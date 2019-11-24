package cn.epicfx.winfxk.money.sn.form;

import cn.epicfx.winfxk.money.sn.Money;
import cn.epicfx.winfxk.money.sn.tool.SimpleForm;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;

/**
 * @author Winfxk
 */
public class MySw extends BaseForm {
	private String[] k;
	private Object[] d;

	public MySw(Player player) {
		super(player);
		k = new String[] { "{Player}", "{Money}", "{NormMoney}" };
		d = new Object[] { player.getName(), Money.getMoney(player), Money.getNormMoney(player) };
	}

	@Override
	public boolean MakeMain() {
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
		form2 = new SimpleForm(FormID2, msg.getSun("界面", "个人主页", "标题", k, d), msg.getSun("界面", "个人主页", "内容", k, d));
		form2.addButton(msg.getSun("界面", "个人主页", "广播", k, d));
		form2.addButton(msg.getSun("界面", "个人主页", "取消", k, d));
		form2.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMakeSun(FormResponseSimple data) {
		if (data.getClickedButtonId() != 0)
			return true;
		Server.getInstance().broadcastMessage(msg.getMessage("广播内容", k, d));
		return true;
	}

}
