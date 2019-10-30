package cn.epicfx.winfxk.money.sn;

import java.util.HashMap;

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
public class PCommand extends Command {
	private Kick kick;
	private Message msg;

	public PCommand(Kick kick) {
		super("sw", "雪花货币主命令", "/sw");
		commandParameters = new HashMap<>();
		commandParameters.put("打开雪花货币主页", new CommandParameter[] {});
		commandParameters.put("转账给某个玩家",
				new CommandParameter[] { new CommandParameter("玩家名称", CommandParamType.TARGET, false),
						new CommandParameter("要支付的金额数量", CommandParamType.FLOAT, false) });
		this.kick = kick;
		msg = kick.Message;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] a) {
		if (!sender.isPermissionSet("Snowmn.Command.Main")) {
			sender.sendMessage(msg.getMessage("无权执行命令", kick.PlayerDataMap.get(sender.getName())));
			return true;
		}
		if (!sender.isPlayer()) {
			sender.sendMessage("请在游戏内执行此命令!");
			return true;
		}
		Player player = (Player) sender;
		if (a.length < 1)
			return kick.makeForm.maakeMain(player);
		switch (a[0]) {

		}
		return false;
	}

}
