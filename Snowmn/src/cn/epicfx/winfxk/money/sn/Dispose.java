package cn.epicfx.winfxk.money.sn;

import cn.epicfx.winfxk.money.sn.form.Billboard;
import cn.epicfx.winfxk.money.sn.form.Demand;
import cn.epicfx.winfxk.money.sn.form.MySw;
import cn.epicfx.winfxk.money.sn.form.Payment;
import cn.epicfx.winfxk.money.sn.form.Setting;
import cn.epicfx.winfxk.money.sn.mtp.FormID;
import cn.epicfx.winfxk.money.sn.mtp.Kick;
import cn.epicfx.winfxk.money.sn.mtp.Message;
import cn.epicfx.winfxk.money.sn.mtp.MyPlayer;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;

/**
 * @author Winfxk
 */
public class Dispose implements Listener {
	private Kick kick;
	private Message msg;

	public Dispose(Kick kick) {
		this.kick = kick;
		msg = kick.Message;
	}

	/**
	 * 处理主页的数据
	 * 
	 * @param player
	 * @param data
	 * @return
	 */
	public boolean Main(Player player, FormResponseSimple data) {
		MyPlayer myPlayer = kick.PlayerDataMap.get(player.getName());
		switch (myPlayer.FormKey.get(data.getClickedButtonId()).toLowerCase()) {
		case "p":
			myPlayer.baseForm = new Payment(player);
			break;
		case "g":
			myPlayer.baseForm = new Demand(player);
			break;
		case "m":
			myPlayer.baseForm = new MySw(player);
			break;
		case "s":
			myPlayer.baseForm = new Setting(player);
			break;
		case "b":
			myPlayer.baseForm = new Billboard(player);
			break;
		default:
			MakeForm.Tip(player, msg.getSon("界面", "处理错误", new String[] { "{Player}", "{Money}", "{Error}" },
					new Object[] { player.getName(), Money.getMoney(player), "找不到对应的按键属性" }));
			return false;
		}
		kick.PlayerDataMap.put(player.getName(), myPlayer);
		return myPlayer.baseForm.MakeMain();
	}

	/**
	 * 表单响应事件
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPlayerForm(PlayerFormRespondedEvent e) {
		FormResponse data = e.getResponse();
		int ID = e.getFormID();
		FormID f = kick.formID;
		Player player = e.getPlayer();
		if (player == null || e.wasClosed() || e.getResponse() == null
				|| (!(e.getResponse() instanceof FormResponseCustom) && !(e.getResponse() instanceof FormResponseSimple)
						&& !(e.getResponse() instanceof FormResponseModal)))
			return;
		try {
			MyPlayer myPlayer = kick.PlayerDataMap.get(player.getName());
			if (ID == f.getID(0))
				Main(player, (FormResponseSimple) data);
			else if (ID == f.getID(1))
				myPlayer.baseForm.disMakeMain((FormResponseCustom) data);
			else if (ID == f.getID(2))
				myPlayer.baseForm.disMakeSun((FormResponseSimple) data);
		} catch (Exception e2) {
			MakeForm.Tip(player, msg.getSon("界面", "处理错误", new String[] { "{Player}", "{Money}", "{Error}" },
					new Object[] { player.getName(), Money.getMoney(player), e2.getMessage() }));
			e2.printStackTrace();
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (kick.PlayerDataMap.containsKey(player.getName()))
			kick.PlayerDataMap.remove(player.getName());
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		kick.PlayerDataMap.put(player.getName(), new MyPlayer(player));
	}
}
