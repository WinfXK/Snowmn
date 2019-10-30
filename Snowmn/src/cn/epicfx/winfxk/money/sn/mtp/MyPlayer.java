package cn.epicfx.winfxk.money.sn.mtp;

import java.io.File;
import java.time.Instant;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;

public class MyPlayer {
	/**
	 * 玩家上次成功使用快捷菜单的时间
	 */
	public Instant loadTime;
	public Player player;

	public MyPlayer(Player player) {
		this.player = player;
	}

	public static Config getConfig(String player) {
		File file = new File(new File(Kick.kick.mis.getDataFolder(), Kick.PlayerConfigPath), player + ".yml");
		if (!file.exists())
			try {
				throw new Exception("该玩家（" + player + "）的配置文件不存在！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		return new Config(file, Config.YAML);
	}
}
