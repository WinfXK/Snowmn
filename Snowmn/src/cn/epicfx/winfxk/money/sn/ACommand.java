package cn.epicfx.winfxk.money.sn;

import java.util.HashMap;

import cn.epicfx.winfxk.money.sn.mtp.Kick;
import cn.epicfx.winfxk.money.sn.mtp.Message;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

/**
 * @author Winfxk
 */
public class ACommand extends Command {
	private Kick kick;
	private Message msg;

	public ACommand(Kick kick) {
		super("aw", "雪花货币管理员命令", "/aw");
		commandParameters = new HashMap<>();
		commandParameters.put("打开雪花货币主页", new CommandParameter[] {});
		this.kick = kick;
		msg = kick.Message;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] a) {
		if (!kick.mis.isEnabled())
			return false;
		if (!sender.hasPermission("Snowmn.Command.Main")) {
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
