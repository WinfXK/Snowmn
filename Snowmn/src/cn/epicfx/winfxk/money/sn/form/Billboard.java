package cn.epicfx.winfxk.money.sn.form;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.epicfx.winfxk.money.sn.MakeForm;
import cn.epicfx.winfxk.money.sn.Money;
import cn.epicfx.winfxk.money.sn.mtp.MyPlayer;
import cn.epicfx.winfxk.money.sn.tool.Format;
import cn.epicfx.winfxk.money.sn.tool.SimpleForm;
import cn.epicfx.winfxk.money.sn.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class Billboard extends BaseForm {
	private Map<String, String> map;

	/**
	 * 排行榜显示页
	 * 
	 * @param player
	 */
	public Billboard(Player player) {
		super(player);
	}

	@Override
	public boolean MakeMain() {
		map = new HashMap<>();
		File file = MyPlayer.getPlayerFile(player.getName()).getParentFile();
		String p, color = Tool.getRandColor(), color2 = Tool.getRandColor();
		Config config;
		for (File f : file.listFiles()) {
			config = new Config(f, Config.YAML);
			p = config.getString("Player");
			map.put(color + p, color2 + Money.getNormMoney(config.getDouble("Money")));
		}
		if (map.size() < 1)
			return MakeForm.Tip(player, msg.getSun("界面", "排行榜", "获取列表失败", player));
		map = Tool.sortByValueDescending(map);
		int bs = kick.config.getInt("排行榜显示数量");
		if (bs > 0) {
			Map<String, String> map2 = new HashMap<>();
			int sb = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				map2.put(entry.getKey(), entry.getValue());
				if (sb > bs)
					break;
			}
			map = map2;
		}
		return MakeSun();
	}

	@Override
	public boolean disMakeMain(FormResponseCustom data) {
		try {
			throw new Exception("无数据的应用窗口");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean MakeSun() {
		form2 = new SimpleForm(FormID2, msg.getSun("界面", "排行榜", "标题", player),
				new Format<>(map).setMaxLength(kick.config.getInt("排行榜占位长度")).getString());
		form2.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMakeSun(FormResponseSimple data) {
		return true;
	}

}
