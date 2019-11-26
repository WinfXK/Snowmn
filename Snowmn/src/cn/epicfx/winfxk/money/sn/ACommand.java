package cn.epicfx.winfxk.money.sn;

import cn.epicfx.winfxk.money.sn.mtp.Kick;
import cn.epicfx.winfxk.money.sn.mtp.MyPlayer;
import cn.epicfx.winfxk.money.sn.tool.Tool;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;

/**
 * @author Winfxk
 */
public class ACommand extends Command {
	private Kick kick;
	private final String Permission = "Snowmn.Command.Admin";

	public ACommand(Kick kick) {
		super("aw", Snowmn.getMoneyName() + "管理员命令", "/aw help");
		this.setPermission(Permission);
		this.commandParameters.clear();
		commandParameters.put("设置玩家" + Snowmn.getMoneyName(),
				new CommandParameter[] {
						new CommandParameter("设置玩家" + Snowmn.getMoneyName() + "数", false, new String[] { "set", "设置" }),
						new CommandParameter("玩家名称", CommandParamType.TARGET, false),
						new CommandParameter(Snowmn.getMoneyName() + "数", CommandParamType.FLOAT, false) });
		commandParameters.put("增加玩家" + Snowmn.getMoneyName(),
				new CommandParameter[] {
						new CommandParameter("增加玩家" + Snowmn.getMoneyName() + "数", false, new String[] { "add", "增加" }),
						new CommandParameter("玩家名称", CommandParamType.TARGET, false),
						new CommandParameter(Snowmn.getMoneyName() + "数", CommandParamType.FLOAT, false) });
		commandParameters.put("扣除玩家" + Snowmn.getMoneyName(),
				new CommandParameter[] {
						new CommandParameter("扣除玩家" + Snowmn.getMoneyName() + "数", false, new String[] { "rec", "扣除" }),
						new CommandParameter("玩家名称", CommandParamType.TARGET, false),
						new CommandParameter(Snowmn.getMoneyName() + "数", CommandParamType.FLOAT, false) });
		commandParameters.put("查看玩家" + Snowmn.getMoneyName(),
				new CommandParameter[] {
						new CommandParameter("查看玩家" + Snowmn.getMoneyName() + "数", false, new String[] { "get", "查看" }),
						new CommandParameter("玩家名称", CommandParamType.TARGET, false) });
		commandParameters.put("广播玩家" + Snowmn.getMoneyName(),
				new CommandParameter[] {
						new CommandParameter("广播玩家" + Snowmn.getMoneyName() + "数", false, new String[] { "rad", "广播" }),
						new CommandParameter("玩家名称", CommandParamType.TARGET, false) });
		commandParameters.put("重置玩家" + Snowmn.getMoneyName(),
				new CommandParameter[] {
						new CommandParameter("重置玩家" + Snowmn.getMoneyName() + "数", false, new String[] { "rel", "重置" }),
						new CommandParameter("玩家名称", CommandParamType.TARGET, false) });
		commandParameters.put("命令帮助",
				new CommandParameter[] { new CommandParameter("命令帮助", false, new String[] { "help", "帮助" }) });
		this.kick = kick;
	}

	@Override
	public boolean execute(CommandSender player, String commandLabel, String[] a) {
		if (!kick.mis.isEnabled())
			return false;
		if (!player.hasPermission(Permission) || !Kick.isAdmin(player)) {
			player.sendMessage(kick.Message.getMessage("无权执行命令", kick.PlayerDataMap.get(player.getName())));
			return true;
		}
		if (a.length < 1) {
			player.sendMessage(Tool.getCommandHelp(this));
			return true;
		}
		String name;
		double Money;
		switch (a[0].toLowerCase()) {
		case "rec":
		case "扣除":
			if (a.length < 3) {
				player.sendMessage(Tool.getCommandHelp(this, commandParameters.get("扣除玩家" + Snowmn.getMoneyName())));
				return true;
			}
			name = a[1];
			if (!MyPlayer.Playerexists(name)) {
				player.sendMessage("§4该玩家不存在或从未加入过游戏！");
				return true;
			}
			Money = Tool.ObjectToDouble(a[2]);
			if (!Tool.isInteger(a[2]) || Money <= 0) {
				player.sendMessage(Snowmn.getMoneyName() + "数仅支持大于零纯数字！");
				return true;
			}
			Money = cn.epicfx.winfxk.money.sn.Money.redMoney(name, Money);
			player.sendMessage("§9" + name + "§6的" + Snowmn.getMoneyName() + "数已被您设置为" + Money
					+ (Money > 1000 ? "§f(§8" + cn.epicfx.winfxk.money.sn.Money.getNormMoney(Money) + "§f)" : ""));
			return true;
		case "add":
		case "增加":
			if (a.length < 3) {
				player.sendMessage(Tool.getCommandHelp(this, commandParameters.get("增加玩家" + Snowmn.getMoneyName())));
				return true;
			}
			name = a[1];
			if (!MyPlayer.Playerexists(name)) {
				player.sendMessage("§4该玩家不存在或从未加入过游戏！");
				return true;
			}
			Money = Tool.ObjectToDouble(a[2]);
			if (!Tool.isInteger(a[2]) || Money <= 0) {
				player.sendMessage(Snowmn.getMoneyName() + "数仅支持大于零纯数字！");
				return true;
			}
			Money = cn.epicfx.winfxk.money.sn.Money.addMoney(name, Money);
			player.sendMessage("§9" + name + "§6的" + Snowmn.getMoneyName() + "数已被您设置为" + Money
					+ (Money > 1000 ? "§f(§8" + cn.epicfx.winfxk.money.sn.Money.getNormMoney(Money) + "§f)" : ""));
			return true;
		case "rel":
		case "重置":
			if (a.length < 2) {
				player.sendMessage(Tool.getCommandHelp(this, commandParameters.get("重置玩家" + Snowmn.getMoneyName())));
				return true;
			}
			name = a[1];
			if (!MyPlayer.Playerexists(name)) {
				player.sendMessage("§4该玩家不存在或从未加入过游戏！");
				return true;
			}
			player.sendMessage("§6重置" + (MyPlayer.reloadConfig(name) ? "成功" : "§4失败！"));
			return true;
		case "rad":
		case "广播":
			if (a.length < 2) {
				player.sendMessage(Tool.getCommandHelp(this, commandParameters.get("广播玩家" + Snowmn.getMoneyName())));
				return true;
			}
			name = a[1];
			if (!MyPlayer.Playerexists(name)) {
				player.sendMessage("§4该玩家不存在或从未加入过游戏！");
				return true;
			}
			Money = cn.epicfx.winfxk.money.sn.Money.getMoney(name);
			Server.getInstance().broadcastMessage(kick.Message.getMessage("广播内容",
					new String[] { "{Player}", "{Money}", "{NormMoney}" },
					new Object[] { player.getName(), Money, cn.epicfx.winfxk.money.sn.Money.getNormMoney(Money) }));
			return true;
		case "get":
		case "查看":
			if (a.length < 2) {
				player.sendMessage(Tool.getCommandHelp(this, commandParameters.get("查看玩家" + Snowmn.getMoneyName())));
				return true;
			}
			name = a[1];
			if (!MyPlayer.Playerexists(name)) {
				player.sendMessage("§4该玩家不存在或从未加入过游戏！");
				return true;
			}
			Money = cn.epicfx.winfxk.money.sn.Money.getMoney(name);
			player.sendMessage("§6该玩家的碎月数为：§b" + Money
					+ (Money > 1000 ? "§f(§8" + cn.epicfx.winfxk.money.sn.Money.getNormMoney(Money) + "§f)" : ""));
			return true;
		case "set":
		case "设置":
			if (a.length < 3) {
				player.sendMessage(Tool.getCommandHelp(this, commandParameters.get("设置玩家" + Snowmn.getMoneyName())));
				return true;
			}
			name = a[1];
			if (!MyPlayer.Playerexists(name)) {
				player.sendMessage("§4该玩家不存在或从未加入过游戏！");
				return true;
			}
			if (!Tool.isInteger(a[2])) {
				player.sendMessage(Snowmn.getMoneyName() + "数仅支持纯数字！");
				return true;
			}
			Money = Tool.ObjectToDouble(a[2]);
			cn.epicfx.winfxk.money.sn.Money.setMoney(name, Money);
			player.sendMessage("§9" + name + "§6的" + Snowmn.getMoneyName() + "数已被您设置为" + Money
					+ (Money > 1000 ? "§f(§8" + cn.epicfx.winfxk.money.sn.Money.getNormMoney(Money) + "§f)" : ""));
			return true;
		case "help":
		case "帮助":
		default:
			break;
		}
		player.sendMessage(Tool.getCommandHelp(this));
		return true;
	}

}
