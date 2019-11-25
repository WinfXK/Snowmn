package cn.epicfx.winfxk.money.sn;

import cn.epicfx.winfxk.money.sn.mtp.Kick;
import cn.epicfx.winfxk.money.sn.mtp.Message;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;

/**
 * @author Winfxk
 */
public class ACommand extends Command {
	private Kick kick;
	private Message msg;
	private final String Permission = "Snowmn.Command.Admin";

	public ACommand(Kick kick) {
		super("aw", "雪花货币管理员命令", "/aw");
		this.setPermission(Permission);
		this.commandParameters.clear();
		commandParameters.put("设置玩家" + Snowmn.getMoneyName(),
				new CommandParameter[] {
						new CommandParameter("设置玩家" + Snowmn.getMoneyName() + "数", false, new String[] { "设置", "set" }),
						new CommandParameter("玩家名称", CommandParamType.TARGET, false),
						new CommandParameter(Snowmn.getMoneyName() + "数", CommandParamType.FLOAT, false) });
		commandParameters.put("增加玩家" + Snowmn.getMoneyName() + "数量",
				new CommandParameter[] {
						new CommandParameter("增加玩家" + Snowmn.getMoneyName() + "数", false, new String[] { "add", "增加" }),
						new CommandParameter("玩家名称", CommandParamType.TARGET, false),
						new CommandParameter(Snowmn.getMoneyName() + "数", CommandParamType.FLOAT, false) });
		commandParameters.put("扣除玩家" + Snowmn.getMoneyName() + "数量",
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
		this.kick = kick;
		msg = kick.Message;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] a) {
		if (!kick.mis.isEnabled())
			return false;
		if (!sender.hasPermission(Permission)) {
			sender.sendMessage(msg.getMessage("无权执行命令", kick.PlayerDataMap.get(sender.getName())));
			return true;
		}
		if (!sender.isPlayer()) {
			sender.sendMessage("请在游戏内执行此命令!");
			return true;
		}
		return kick.makeForm.makeMain((Player) sender);
	}

}
