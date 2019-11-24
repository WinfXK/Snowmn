package cn.epicfx.winfxk.money.sn.mtp;

import java.util.Map;
import java.util.UUID;

import cn.epicfx.winfxk.money.sn.Money;
import cn.nukkit.Player;
import cn.nukkit.Server;

/**
 * @author Winfxk
 */
public class startThread extends Thread {
	private Kick kick;
	private int WhileTime = 0;

	public startThread(Kick kick) {
		this.kick = kick;
		WhileTime = kick.config.getInt("循环间隔");
	}

	@Override
	public void run() {
		while (true)
			try {
				sleep(1000);
				if (WhileTime < 0) {
					WhileTime = kick.config.getInt("循环间隔");
					Map<UUID, Player> Players = Server.getInstance().getOnlinePlayers();
					for (Player player : Players.values())
						if (!player.isOnline())
							continue;
						else
							Money.addMoney(player, kick.config.getDouble("每次增加"));
				} else
					WhileTime--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
