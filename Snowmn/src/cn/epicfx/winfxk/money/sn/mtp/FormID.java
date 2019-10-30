package cn.epicfx.winfxk.money.sn.mtp;

import java.util.HashMap;
import java.util.Map;

import cn.epicfx.winfxk.money.sn.tool.Tool;

/**
 * @author Winfxk
 */
public class FormID {
	private Map<String, Integer> config;

	public FormID() {
		config = new HashMap<>();
	}

	public Map<String, Integer> getMap() {
		return config;
	}

	public int getID(int Key) {
		return getID(Kick.FormIDName[Key]);
	}

	public void setConfig(Map<String, Object> config) {
		for (String key : config.keySet())
			this.config.put(key,
					Tool.isInteger(config.get(key)) ? Float.valueOf(String.valueOf(config.get(key))).intValue()
							: Tool.getRand());
	}

	public int getID(String Key) {
		return config.containsKey(Key) ? config.get(Key) : Tool.getRand();
	}
}
