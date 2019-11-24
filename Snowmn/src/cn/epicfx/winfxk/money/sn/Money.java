package cn.epicfx.winfxk.money.sn;

import cn.epicfx.winfxk.money.sn.mtp.Kick;
import cn.epicfx.winfxk.money.sn.mtp.Message;
import cn.epicfx.winfxk.money.sn.mtp.MyPlayer;
import cn.epicfx.winfxk.money.sn.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class Money {
	/**
	 * 增加一个玩家的碎月
	 * 
	 * @param player
	 * @param Money
	 * @return
	 */
	public static double addMoney(CommandSender player, double Money) {
		return addMoney(player.getName(), Money);
	}

	/**
	 * 增加一个玩家的碎月
	 * 
	 * @param player
	 * @param Money
	 * @return
	 */
	public static double addMoney(Player player, double Money) {
		return addMoney(player.getName(), Money);
	}

	/**
	 * 增加一个玩家的碎月
	 * 
	 * @param player
	 * @param Money
	 * @return
	 */
	public static double addMoney(String player, double Money) {
		Config config = MyPlayer.getConfig(player);
		double sb = 0;
		config.set("Money", sb = (config.getDouble("Money") + Money));
		config.save();
		return sb;
	}

	/**
	 * 减少一个玩家的碎月
	 * 
	 * @param player
	 * @param Money
	 * @return
	 */
	public static double redMoney(CommandSender player, double Money) {
		return redMoney(player.getName(), Money);
	}

	/**
	 * 减少一个玩家的碎月
	 * 
	 * @param player
	 * @param Money
	 * @return
	 */
	public static double redMoney(Player player, double Money) {
		return redMoney(player.getName(), Money);
	}

	/**
	 * 减少一个玩家的碎月
	 * 
	 * @param player
	 * @param Money
	 * @return
	 */
	public static double redMoney(String player, double Money) {
		Config config = MyPlayer.getConfig(player);
		double sb = 0;
		config.set("Money", sb = (config.getDouble("Money") - Money));
		config.save();
		return sb;
	}

	/**
	 * 设置一个玩家的碎月
	 * 
	 * @param player
	 * @param Money
	 * @return
	 */
	public static double setMoney(CommandSender player, double Money) {
		return setMoney(player.getName(), Money);
	}

	/**
	 * 设置一个玩家的碎月
	 * 
	 * @param player
	 * @param Money
	 * @return
	 */
	public static double setMoney(Player player, double Money) {
		return setMoney(player.getName(), Money);
	}

	/**
	 * 设置一个玩家的碎月
	 * 
	 * @param player
	 * @param Money
	 * @return
	 */
	public static double setMoney(String player, double Money) {
		Config config = MyPlayer.getConfig(player);
		config.set("Money", Money);
		config.save();
		return Money;
	}

	/**
	 * 取得一个玩家的碎月数量
	 * 
	 * @param player
	 * @return
	 */
	public static double getMoney(Player player) {
		return getMoney(player.getName());
	}

	/**
	 * 取得一个玩家的碎月数量
	 * 
	 * @param player
	 * @return
	 */
	public static double getMoney(CommandSender player) {
		return getMoney(player.getName());
	}

	/**
	 * 获取规范化玩家金币数
	 * 
	 * @param player
	 * @return
	 */
	public static String getNormMoney(CommandSender player) {
		return getNormMoney(player.getName());
	}

	/**
	 * 获取规范化玩家金币数
	 * 
	 * @param player
	 * @return
	 */
	public static String getNormMoney(Player player) {
		return getNormMoney(player.getName());
	}

	/**
	 * 获取规范化玩家金币数
	 * 
	 * @param player
	 * @return
	 */
	public static String getNormMoney(String player) {
		double money = getMoney(player);
		int k = (int) (money / 1000000000);
		money = money % 1000000000;
		int sm = (int) (money / 1000000);
		money = money % 1000000;
		int st = (int) (money / 1000);
		money = Tool.Double2(money % 1000);
		Message msg = Kick.kick.Message;
		String ms = msg.getSon("货币", "碎月", new String[] { "{Player}", "{Money}" }, new Object[] { player, money });
		String sts = msg.getSon("货币", "霜月", new String[] { "{Player}", "{Money}" }, new Object[] { player, st });
		String sms = msg.getSon("货币", "雪月", new String[] { "{Player}", "{Money}" }, new Object[] { player, sm });
		String ks = msg.getSon("货币", "寒月", new String[] { "{Player}", "{Money}" }, new Object[] { player, k });
		String string = (k > 0 ? ks : "") + (st > 0 ? sts : "") + (sm > 0 ? sms : "") + (money > 0 ? ms : "");
		return string.isEmpty()
				? msg.getSon("货币", "无货币", new String[] { "{Player}", "{Money}" }, new Object[] { player, k })
				: string;
	}

	/**
	 * 获取规范化玩家金币数
	 * 
	 * @param player
	 * @return
	 */
	public static String getNormMoney(int money) {
		return getNormMoney(money);
	}

	/**
	 * 获取规范化玩家金币数
	 * 
	 * @param player
	 * @return
	 */
	public static String getNormMoney(double money) {
		int k = (int) (money / 1000000000);
		money = money % 1000000000;
		int sm = (int) (money / 1000000);
		money = money % 1000000;
		int st = (int) (money / 1000);
		money = Tool.Double2(money % 1000);
		Message msg = Kick.kick.Message;
		String ms = msg.getSon("货币", "碎月", new String[] { "{Money}" }, new Object[] { money });
		String sts = msg.getSon("货币", "霜月", new String[] { "{Money}" }, new Object[] { st });
		String sms = msg.getSon("货币", "雪月", new String[] { "{Money}" }, new Object[] { sm });
		String ks = msg.getSon("货币", "寒月", new String[] { "{Money}" }, new Object[] { k });
		String string = (k > 0 ? ks : "") + (st > 0 ? sts : "") + (sm > 0 ? sms : "") + (money > 0 ? ms : "");
		return string.isEmpty() ? ms : string;
	}

	/**
	 * 取得一个玩家的碎月数量
	 * 
	 * @param player
	 * @return
	 */
	public static double getMoney(String player) {
		return MyPlayer.getConfig(player).getDouble("Money");
	}
}
