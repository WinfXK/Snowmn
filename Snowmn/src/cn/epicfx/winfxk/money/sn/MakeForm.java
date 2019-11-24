package cn.epicfx.winfxk.money.sn;

import java.util.ArrayList;

import cn.epicfx.winfxk.money.sn.mtp.FormID;
import cn.epicfx.winfxk.money.sn.mtp.Kick;
import cn.epicfx.winfxk.money.sn.mtp.Message;
import cn.epicfx.winfxk.money.sn.mtp.MyPlayer;
import cn.epicfx.winfxk.money.sn.tool.ModalForm;
import cn.epicfx.winfxk.money.sn.tool.SimpleForm;
import cn.epicfx.winfxk.money.sn.tool.Tool;
import cn.nukkit.Player;

/**
 * @author Winfxk
 */
public class MakeForm {
	private Kick kick;
	private FormID formID;
	private Message msg = Kick.kick.Message;

	public MakeForm(Kick kick) {
		this.kick = kick;
		formID = kick.formID;
	}

	/**
	 * 构建主页
	 * 
	 * @param player
	 * @return
	 */
	public boolean makeMain(Player player) {
		SimpleForm form = new SimpleForm(formID.getID(0), msg.getSun("界面", "主页", "标题", player),
				msg.getSun("界面", "主页", "内容", player));
		MyPlayer myPlayer = kick.PlayerDataMap.get(player.getName());
		myPlayer.FormKey = new ArrayList<>();
		form.addButton(msg.getSun("界面", "主页", "支付", player));
		myPlayer.FormKey.add("p");
		form.addButton(msg.getSun("界面", "主页", "索要", player));
		myPlayer.FormKey.add("g");
		form.addButton(msg.getSun("界面", "主页", "排行榜", player));
		myPlayer.FormKey.add("b");
		form.addButton(msg.getSun("界面", "主页", "我的", player));
		myPlayer.FormKey.add("m");
		if (Kick.isAdmin(player)) {
			form.addButton(Tool.getRandColor() + "管理设置");
			myPlayer.FormKey.add("s");
		}
		kick.PlayerDataMap.put(player.getName(), myPlayer);
		form.sendPlayer(player);
		return true;
	}

	/**
	 * 显示一个弹窗
	 * 
	 * @param player  要显示弹窗的玩家对象
	 * @param Content 弹窗的内容
	 * @return <b>back</b>
	 */
	public static boolean Tip(Player player, String Content) {
		return Tip(player, Tool.getRandColor() + Kick.kick.mis.getName(), Content, false);
	}

	/**
	 * 显示一个弹窗
	 * 
	 * @param player  要显示弹窗的玩家对象
	 * @param Content 弹窗的内容
	 * @param back    返回的布尔值
	 * @return <b>back</b>
	 */
	public static boolean Tip(Player player, String Content, boolean back) {
		return Tip(player, Tool.getRandColor() + Kick.kick.mis.getName(), Content, back);
	}

	/**
	 * 显示一个弹窗
	 * 
	 * @param player  要显示弹窗的玩家对象
	 * @param Title   弹窗的标题
	 * @param Content 弹窗的内容
	 * @param back    返回的布尔值
	 * @return <b>back</b>
	 */
	public static boolean Tip(Player player, String Title, String Content, boolean back) {
		return Tip(player, Title, Content, back, true);
	}

	/**
	 * 显示一个弹窗
	 * 
	 * @param player  要显示弹窗的玩家对象
	 * @param Title   弹窗的标题
	 * @param Content 弹窗的内容
	 * @param back    返回的布尔值
	 * @param Modal   是否是Modal型弹窗
	 * @return <b>back</b>
	 */
	public static boolean Tip(Player player, String Title, String Content, boolean back, boolean Modal) {
		return Tip(player, Title, Content, "确定", "取消", back, Modal);
	}

	/**
	 * 显示一个弹窗
	 * 
	 * @param player  要显示弹窗的玩家对象
	 * @param Title   弹窗的标题
	 * @param Content 弹窗的内容
	 * @param Button1 弹窗的第一个按钮文本内容
	 * @param Button2 弹窗的第二个按钮文本内容
	 * @param back    返回的布尔值
	 * @param Modal   是否是Modal型弹窗
	 * @return <b>back</b>
	 */
	public static boolean Tip(Player player, String Title, String Content, String Button1, String Button2, boolean back,
			boolean Modal) {
		if (Modal) {
			ModalForm form = new ModalForm(Tool.getRand(), Title, Button1, Button2);
			form.setContent(Content);
			form.sendPlayer(player);
		} else {
			SimpleForm form = new SimpleForm(Tool.getRand(), Title, Content);
			form.addButton(Button1).addButton(Button2);
			form.sendPlayer(player);
		}
		return back;
	}
}
