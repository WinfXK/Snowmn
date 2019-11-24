package cn.epicfx.winfxk.money.sn.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.epicfx.winfxk.money.sn.mtp.Kick;
import cn.epicfx.winfxk.money.sn.mtp.Message;
import cn.epicfx.winfxk.money.sn.mtp.MyPlayer;
import cn.epicfx.winfxk.money.sn.tool.CustomForm;
import cn.epicfx.winfxk.money.sn.tool.SimpleForm;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public abstract class BaseForm {
	public Player player;
	public int FormID, FormID2;
	public Kick kick;
	public Message msg;
	public MyPlayer myPlayer;
	public CustomForm form;
	public SimpleForm form2;

	public BaseForm(Player player) {
		this.player = player;
		kick = Kick.kick;
		FormID = kick.formID.getID(1);
		FormID2 = kick.formID.getID(2);
		msg = kick.Message;
		myPlayer = kick.PlayerDataMap.get(player.getName());
	}

	/**
	 * 构建主页
	 * 
	 * @return
	 */
	public abstract boolean MakeMain();

	/**
	 * 处理主页的数据
	 * 
	 * @param data
	 * @return
	 */
	public abstract boolean disMakeMain(FormResponseCustom data);

	/**
	 * 构建确定页
	 * 
	 * @return
	 */
	public abstract boolean MakeSun();

	/**
	 * 处理确定页的东西
	 * 
	 * @param data
	 * @return
	 */
	public abstract boolean disMakeSun(FormResponseSimple data);

	/**
	 * 获取文本列表的在线玩家列表
	 * 
	 * @return
	 */
	public List<String> getPlayers() {
		return getPlayers(null, true);
	}

	/**
	 * 获取文本列表的在线玩家列表
	 * 
	 * @return
	 */
	public List<String> getPlayers(Player p, boolean isMy) {
		List<String> list = new ArrayList<>();
		Map<UUID, Player> PPls = Server.getInstance().getOnlinePlayers();
		for (Player player : PPls.values())
			if (!isMy || (p != null && !p.getName().equals(player.getName())))
				list.add(player.getName());
		return list;
	}

	/**
	 * 发送消息给玩家
	 * 
	 * @param player
	 * @param string
	 * @param is
	 * @return
	 */
	public boolean sendMessage(String player, String string, boolean is) {
		Player p = Server.getInstance().getPlayer(player);
		if (p == null || !p.isOnline()) {
			Config config = MyPlayer.getConfig(player);
			List<String> list = config.getList("Msg");
			list.add(string);
			config.set("Msg", list);
			config.save();
		} else
			p.sendMessage(string);
		return is;
	}

	/**
	 * 判断玩家是否在线
	 * 
	 * @param player
	 * @return
	 */
	public boolean isOnline(String player) {
		Player p = Server.getInstance().getPlayer(player);
		return (p != null && p.isOnline());
	}
}
