package cn.epicfx.winfxk.money.sn.form;

import cn.epicfx.winfxk.money.sn.MakeForm;
import cn.epicfx.winfxk.money.sn.mtp.MyPlayer;
import cn.epicfx.winfxk.money.sn.tool.CustomForm;
import cn.epicfx.winfxk.money.sn.tool.SimpleForm;
import cn.epicfx.winfxk.money.sn.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;

/**
 * @author Winfxk
 */
public class Payment extends BaseForm {
	private String PlayerName;
	private double Money;

	/**
	 * 支付金币给某玩家
	 * 
	 * @param player
	 */
	public Payment(Player player) {
		super(player);
	}

	@Override
	public boolean MakeMain() {
		form = new CustomForm(FormID, msg.getSun("界面", "支付", "标题", player));
		form.addInput(msg.getSun("界面", "支付", "请输入玩家名", player));
		form.addDropdown(msg.getSun("界面", "支付", "请选择玩家名", player), getPlayers(player, false));
		form.addInput(msg.getSun("界面", "支付", "请输入金币数", player));
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMakeMain(FormResponseCustom data) {
		PlayerName = data.getInputResponse(0);
		if (PlayerName.isEmpty())
			PlayerName = data.getDropdownResponse(1).getElementContent();
		if (PlayerName.isEmpty())
			return MakeForm.Tip(player, msg.getSun("界面", "支付", "请输入玩家名", player));
		String moString = data.getInputResponse(2);
		if (moString.isEmpty())
			return MakeForm.Tip(player, msg.getSun("界面", "支付", "未输入金币数", player));
		try {
			Money = Double.valueOf(moString);
		} catch (Exception e) {
		}
		if (!Tool.isInteger(moString) || Money <= 0)
			return MakeForm.Tip(player, msg.getSun("界面", "支付", "金币数不符合规范", player));
		if (player.getName().equals(PlayerName))
			return MakeForm.Tip(player, msg.getSun("界面", "支付", "不能支付给自己", player));
		if (!MyPlayer.Playerexists(PlayerName))
			return MakeForm.Tip(player, msg.getSun("界面", "支付", "无玩家", player));
		return MakeSun();
	}

	@Override
	public boolean MakeSun() {
		form2 = new SimpleForm(FormID2, msg.getSun("界面", "支付", "支付警告", player),
				msg.getSun("界面", "支付", "支付警告内容",
						new String[] { "{Player}", "{MyMoney}", "{Money}", "{ByPlayer}", "{isOnline}" },
						new Object[] { player.getName(), cn.epicfx.winfxk.money.sn.Money.getMoney(player), Money,
								PlayerName, isOnline(PlayerName) ? "在线" : "离线" }));
		form2.addButton(msg.getSun("界面", "支付", "确定", player));
		form2.addButton(msg.getSun("界面", "支付", "取消", player));
		form2.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMakeSun(FormResponseSimple data) {
		if (data.getClickedButtonId() != 0)
			return false;
		cn.epicfx.winfxk.money.sn.Money.addMoney(PlayerName, Money);
		cn.epicfx.winfxk.money.sn.Money.redMoney(player, Money);
		player.sendMessage(msg.getSun("界面", "支付", "支付成功",
				new String[] { "{Player}", "{MyMoney}", "{Money}", "{ByPlayer}" }, new Object[] { player.getName(),
						cn.epicfx.winfxk.money.sn.Money.getMoney(player), Money, PlayerName }));
		return sendMessage(PlayerName, msg.getSun("界面", "支付", "收到支付",
				new String[] { "{Player}", "{MyMoney}", "{Money}", "{ByPlayer}" },
				new Object[] { player.getName(), cn.epicfx.winfxk.money.sn.Money.getMoney(player), Money, PlayerName }),
				true);
	}

}
