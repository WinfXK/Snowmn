package cn.epicfx.winfxk.money.sn;

import cn.epicfx.winfxk.money.sn.mtp.MyPlayer;
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
	 * 取得一个玩家的碎月数量
	 * 
	 * @param player
	 * @return
	 */
	public static double getMoney(String player) {
		return MyPlayer.getConfig(player).getDouble("Money");
	}
}
