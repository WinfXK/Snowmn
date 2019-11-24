package cn.epicfx.winfxk.money.sn.form;

import cn.epicfx.winfxk.money.sn.MakeForm;
import cn.epicfx.winfxk.money.sn.tool.CustomForm;
import cn.epicfx.winfxk.money.sn.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class Setting extends BaseForm {
	private Config config;

	/**
	 * 插件属性设置
	 * 
	 * @param player
	 */
	public Setting(Player player) {
		super(player);
		config = kick.config;
	}

	@Override
	public boolean MakeMain() {
		form = new CustomForm(FormID, Tool.getColorFont(kick.mis.getName()) + Tool.getRandColor() + "设置");
		form.addToggle("检测更新", config.getBoolean("检测更新"));
		form.addInput("更新监测间隔", config.get("检测更新间隔"), 21600);
		form.addInput("玩家货币增加间隔", config.get("循环间隔"), 10);
		form.addInput("每次循环玩家增加的货币数量", config.get("每次增加"), 0.3);
		form.addInput("基准货币单位", config.get("货币单位"), "碎月");
		form.addInput("二阶货币单位", config.get("二阶货币单位"), "霜月");
		form.addInput("三阶货币单位", config.get("三阶货币单位"), "雪月");
		form.addInput("四阶货币单位", config.get("四阶货币单位"), "寒月");
		form.addInput("玩家初始金币", config.get("玩家初始金币"), 1);
		form.addToggle("后备隐藏能源", config.getBoolean("后备隐藏能源"));
		form.addInput("排行榜显示数量(0为不限制)", config.get("排行榜显示数量"), 0);
		form.addInput("排行榜占位长度", config.get("排行榜占位长度"), 55);
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMakeMain(FormResponseCustom data) {
		config.set("检测更新", data.getToggleResponse(0));
		config.set("检测更新间隔", Tool.ObjectToInt(data.getInputResponse(1)));
		config.set("循环间隔", Tool.ObjectToInt(data.getInputResponse(2)));
		config.set("每次增加", Tool.ObjectToDouble(data.getInputResponse(3)));
		config.set("货币单位", Tool.objToString(data.getInputResponse(4), "碎月"));
		config.set("二阶货币单位", Tool.objToString(data.getInputResponse(5), "霜月"));
		config.set("三阶货币单位", Tool.objToString(data.getInputResponse(6), "雪月"));
		config.set("四阶货币单位", Tool.objToString(data.getInputResponse(7), "寒月"));
		config.set("玩家初始金币", Tool.ObjectToInt(data.getInputResponse(8), 1));
		config.set("后备隐藏能源", data.getToggleResponse(9));
		config.set("排行榜显示数量", Tool.ObjectToInt(data.getInputResponse(10), 0));
		config.set("排行榜占位长度", Tool.ObjectToInt(data.getInputResponse(11), 55));
		kick.config = config;
		boolean isok = kick.config.save();
		return MakeForm.Tip(player, "§6保存" + (isok ? "成功！" : "§4失败！"), isok);
	}

	@Override
	public boolean MakeSun() {
		return true;
	}

	@Override
	public boolean disMakeSun(FormResponseSimple data) {
		return true;
	}

}
