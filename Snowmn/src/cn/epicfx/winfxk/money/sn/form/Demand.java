package cn.epicfx.winfxk.money.sn.form;

import cn.epicfx.winfxk.money.sn.MakeForm;
import cn.epicfx.winfxk.money.sn.mtp.MyPlayer;
import cn.epicfx.winfxk.money.sn.tool.CustomForm;
import cn.epicfx.winfxk.money.sn.tool.SimpleForm;
import cn.epicfx.winfxk.money.sn.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;

/**
 * @author Winfxk
 */
public class Demand extends BaseForm {
	private String PlayerName;
	private double Money;
	private Player byPlayer;
	private String[] k = { "{Player}", "{MyMoney}", "{Money}", "{ByPlayer}", "{NormMoney}" };;

	/**
	 * 向玩家索要金币
	 * 
	 * @param player
	 */
	public Demand(Player player) {
		super(player);
	}

	@Override
	public boolean MakeMain() {
		form = new CustomForm(FormID, msg.getSun("界面", "索要", "标题", player));
		form.addInput(msg.getSun("界面", "索要", "请输入玩家名", player));
		form.addDropdown(msg.getSun("界面", "索要", "请选择玩家名", player), getPlayers(player, false));
		form.addInput(msg.getSun("界面", "索要", "请输入金币数", player));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMakeMain(FormResponseCustom data) {
		PlayerName = data.getInputResponse(0);
		if (PlayerName.isEmpty())
			PlayerName = data.getDropdownResponse(1).getElementContent();
		if (PlayerName.isEmpty())
			return MakeForm.Tip(player, msg.getSun("界面", "索要", "请输入玩家名", player));
		String moString = data.getInputResponse(2);
		if (moString.isEmpty())
			return MakeForm.Tip(player, msg.getSun("界面", "索要", "未输入金币数", player));
		try {
			Money = Double.valueOf(moString);
		} catch (Exception e) {
		}
		if (!Tool.isInteger(moString) || Money <= 0)
			return MakeForm.Tip(player, msg.getSun("界面", "索要", "金币数不符合规范", player));
		if (player.getName().equals(PlayerName))
			return MakeForm.Tip(player, msg.getSun("界面", "索要", "不能向自己索要", player));
		if (!MyPlayer.Playerexists(PlayerName))
			return MakeForm.Tip(player, msg.getSun("界面", "索要", "无玩家", player));
		byPlayer = Server.getInstance().getPlayer(PlayerName);
		if (byPlayer == null || !byPlayer.isOnline())
			return MakeForm.Tip(player, msg.getSun("界面", "索要", "不在线", player));
		MyPlayer mmMyPlayer = kick.PlayerDataMap.get(PlayerName);
		mmMyPlayer.baseForm = this;
		kick.PlayerDataMap.put(PlayerName, mmMyPlayer);
		return MakeSun();
	}

	@Override
	public boolean MakeSun() {
		form2 = new SimpleForm(FormID2, msg.getSun("界面", "索要", "支付警告", player),
				msg.getSun("界面", "索要", "索要请求", k,
						new Object[] { byPlayer.getName(), cn.epicfx.winfxk.money.sn.Money.getMoney(byPlayer), Money,
								player.getName(), cn.epicfx.winfxk.money.sn.Money.getNormMoney(Money) }));
		form2.addButton(msg.getSun("界面", "索要", "确定", player));
		form2.addButton(msg.getSun("界面", "索要", "取消", player));
		form2.sendPlayer(byPlayer);
		return true;
	}

	@Override
	public boolean disMakeSun(FormResponseSimple data) {
		if (data.getClickedButtonId() != 0) {
			MakeForm.Tip(byPlayer,
					msg.getSun("界面", "索要", "拒绝支付", k,
							new Object[] { PlayerName, cn.epicfx.winfxk.money.sn.Money.getMoney(PlayerName), Money,
									player.getName(), cn.epicfx.winfxk.money.sn.Money.getNormMoney(Money) }));
			return MakeForm.Tip(player,
					msg.getSun("界面", "索要", "拒绝索要", k,
							new Object[] { player.getName(), cn.epicfx.winfxk.money.sn.Money.getMoney(player), Money,
									PlayerName, cn.epicfx.winfxk.money.sn.Money.getNormMoney(Money) }));
		}
		cn.epicfx.winfxk.money.sn.Money.redMoney(PlayerName, Money);
		cn.epicfx.winfxk.money.sn.Money.addMoney(player, Money);
		MakeForm.Tip(byPlayer,
				msg.getSun("界面", "索要", "支付成功", k,
						new Object[] { PlayerName, cn.epicfx.winfxk.money.sn.Money.getMoney(PlayerName), Money,
								player.getName(), cn.epicfx.winfxk.money.sn.Money.getNormMoney(Money) }));
		return MakeForm.Tip(player,
				msg.getSun("界面", "索要", "收到支付", k,
						new Object[] { player.getName(), cn.epicfx.winfxk.money.sn.Money.getMoney(player), Money,
								PlayerName, cn.epicfx.winfxk.money.sn.Money.getNormMoney(Money) }));
	}

}
