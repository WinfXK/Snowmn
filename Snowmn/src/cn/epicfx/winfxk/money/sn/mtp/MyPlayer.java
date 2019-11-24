package cn.epicfx.winfxk.money.sn.mtp;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import cn.epicfx.winfxk.money.sn.form.BaseForm;
import cn.nukkit.Player;
import cn.nukkit.utils.Config;

public class MyPlayer {
	/**
	 * 玩家上次成功使用快捷菜单的时间
	 */
	public Instant loadTime;
	public Player player;
	/**
	 * 在构建界面是，用于记录每个按钮的Key
	 */
	public List<String> FormKey;
	public BaseForm baseForm;

	public MyPlayer(Player player) {
		this.player = player;
		File file = getPlayerFile(player.getName());
		if (!file.exists()) {
			Config config = new Config(file, Config.YAML);
			config.set("Player", player.getName());
			config.set("Money", Kick.kick.config.get("玩家初始金币"));
			config.set("Msg", new ArrayList<String>());
			config.save();
		}
	}

	public static Config getConfig(String player) {
		File file = getPlayerFile(player);
		if (!file.exists())
			try {
				throw new Exception("该玩家（" + player + "）的配置文件不存在！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		return new Config(file, Config.YAML);
	}

	public static File getPlayerFile(String player) {
		return new File(new File(Kick.kick.mis.getDataFolder(), Kick.PlayerConfigPath), player + ".yml");
	}

	public static boolean Playerexists(String player) {
		File file = getPlayerFile(player);
		return file.exists();
	}
}
