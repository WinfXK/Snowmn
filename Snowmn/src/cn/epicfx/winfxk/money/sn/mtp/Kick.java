package cn.epicfx.winfxk.money.sn.mtp;

import java.io.File;
import java.util.LinkedHashMap;

import cn.epicfx.winfxk.money.sn.MakeForm;
import cn.epicfx.winfxk.money.sn.Snowmn;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class Kick {
	public static Kick kick;
	/**
	 * 插件主累对象
	 */
	public Snowmn mis;
	/**
	 * 插件猪被子文件 <b>config</b></br>
	 * 表单ID配置文件 <b>formIdConfig</b> </br>
	 */
	public Config config, formIdConfig;
	/**
	 * 系统配置文件的文件名
	 */
	public static final String ConfigName = "Config.yml";
	/**
	 * 每个玩家的余额存储地
	 */
	public static final String SnowmnFile = "SnowmnFile.yml";
	/**
	 * 表单ID存储类
	 */
	public FormID formID;
	/**
	 * 消息文件存储文件名
	 */
	public static final String MsgName = "Message.yml";
	/**
	 * 消息文件类
	 */
	public Message Message;
	/**
	 * 要初始化的表单ID键值
	 */
	public static final String[] FormIDName = { /* 0 */"主页", /* 1 */"玩家交互子页", /* 2 */"玩家交互子页确定页" };
	/**
	 * 表单ID存储位置
	 */
	public static final String FormIDConfigName = "FormID.yml";
	/**
	 * 玩家数据库
	 */
	public LinkedHashMap<String, MyPlayer> PlayerDataMap = new LinkedHashMap<>();
	/**
	 * 要检查默认设置的配置文件
	 */
	public static final String[] LoadFileName = { ConfigName, MsgName };
	/**
	 * 玩家配置文件存储路径
	 */
	public static final String PlayerConfigPath = "Players/";
	/**
	 * 在启动服务器时检查文件夹是否创建，要检查的列表
	 */
	public static final String[] LoadDirList = { PlayerConfigPath };
	/**
	 * 异步线程类
	 */
	public startThread sThread;
	public MakeForm makeForm;

	public Kick(Snowmn bemilk) {
		kick = this;
		if (!bemilk.getDataFolder().exists())
			bemilk.getDataFolder().mkdirs();
		mis = bemilk;
		formIdConfig = new Config(new File(bemilk.getDataFolder(), FormIDConfigName), Config.YAML);
		(new Belle(this)).start();
		config = new Config(new File(bemilk.getDataFolder(), ConfigName), Config.YAML);
		formID = new FormID();
		formID.setConfig(formIdConfig.getAll());
		Message = new Message(this);
		sThread = new startThread(this);
		sThread.start();
		makeForm = new MakeForm(this);
	}

	/**
	 * 判断一个沙雕是不是管理员
	 * 
	 * @param player
	 * @return
	 */
	public static boolean isAdmin(CommandSender player) {
		if (!player.isPlayer())
			return true;
		return isAdmin((Player) player);
	}

	/**
	 * 判断一个沙雕是不是管理员
	 * 
	 * @param player
	 * @return
	 */
	public static boolean isAdmin(Player player) {
		if (player == null)
			return false;
		if (!player.isPlayer())
			return true;
		if (kick.config.getBoolean("管理员白名单")) {
			return kick.config.getList("管理员").contains(player.getName());
		} else
			return player.isOp();
	}
}
