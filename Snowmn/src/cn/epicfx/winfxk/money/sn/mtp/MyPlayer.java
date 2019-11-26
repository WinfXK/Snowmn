package cn.epicfx.winfxk.money.sn.mtp;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
		if (!file.exists())
			reloadConfig(player.getName());
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

	public static boolean reloadConfig(String name) {
		Config config = new Config(getPlayerFile(name), Config.YAML);
		config.setAll(DefaultConfig());
		config.set("Player", name);
		return config.save();
	}

	public static LinkedHashMap<String, Object> DefaultConfig() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("Player", null);
		map.put("Money", Kick.kick.config.get("玩家初始金币"));
		map.put("Msg", new ArrayList<String>());
		return map;
	}
}
