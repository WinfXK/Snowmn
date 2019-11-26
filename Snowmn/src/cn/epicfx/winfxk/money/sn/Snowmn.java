package cn.epicfx.winfxk.money.sn;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Map;
import java.util.UUID;

import cn.epicfx.winfxk.money.sn.mtp.Kick;
import cn.epicfx.winfxk.money.sn.mtp.MyPlayer;
import cn.epicfx.winfxk.money.sn.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

/**
 * @author Winfxk
 */
public class Snowmn extends PluginBase {
	private Temporal loadTime = Instant.now();
	private static Kick kick;

	@Override
	public void onEnable() {
		Instant EnableTime = Instant.now();
		float entime = ((Duration.between(loadTime, Instant.now()).toMillis()));
		this.getServer().getCommandMap().register(getName() + "PlayersCommand", new PCommand(kick));
		this.getServer().getCommandMap().register(getName() + "AdministratorCommand", new ACommand(kick));
		Map<UUID, Player> OnlinePlayers = getServer().getOnlinePlayers();
		for (UUID id : OnlinePlayers.keySet()) {
			Player player = OnlinePlayers.get(id);
			if (player.isOnline() && !kick.PlayerDataMap.containsKey(player.getName()))
				kick.PlayerDataMap.put(player.getName(), new MyPlayer(player));
		}
		this.getServer().getPluginManager().registerEvents(new Dispose(kick), this);
		String onEnableString = (entime > 1000 ? ((entime / 1000) + "§6s!(碉堡了) ") : entime + "§6ms");
		this.getServer().getLogger().info(Tool.getColorFont(this.getName() + "启动！") + "§6总耗时:§9" + onEnableString
				+ " 启动耗时:§9" + ((float) (Duration.between(EnableTime, Instant.now()).toMillis())) + "§6ms");
		if (Tool.getRand(1, 5) == 1)
			getLogger().info(Tool.getColorFont("本插件完全免费，如果你是给钱了的，那你就可能被坑啦~"));
	}

	/**
	 * 返回货币的名称，如“金币”
	 * 
	 * @return
	 */
	public static String getMoneyName() {
		return kick.config.getString("货币单位");
	}

	/**
	 * ????这都看不懂？？这是插件关闭事件
	 */
	@Override
	public void onDisable() {
		this.getServer().getLogger()
				.info(Tool.getColorFont(this.getName() + "关闭！") + TextFormat.GREEN + "本次运行时长" + TextFormat.BLUE
						+ Tool.getTimeBy(((float) (Duration.between(loadTime, Instant.now()).toMillis()) / 1000)));
		super.onDisable();
	}

	/**
	 * PY已准备好！插件加载事件
	 */
	@Override
	public void onLoad() {
		this.getServer().getLogger().info(Tool.getColorFont(this.getName() + "正在加载..."));
		kick = new Kick(this);
	}
}
