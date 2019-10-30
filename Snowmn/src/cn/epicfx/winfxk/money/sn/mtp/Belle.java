package cn.epicfx.winfxk.money.sn.mtp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.epicfx.winfxk.money.sn.tool.Tool;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.Utils;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
 
/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class Belle {
	private Kick kick;

	public Belle(Kick kick) {
		this.kick = kick;
	}

	public void start() {
		File file;
		for (String DirName : Kick.LoadDirList) {
			file = new File(kick.mis.getDataFolder(), DirName);
			if (!file.exists())
				file.mkdirs();
		}
		for (String FileName : Kick.LoadFileName) {
			file = new File(kick.mis.getDataFolder(), FileName);
			if (!file.exists())
				try {
					kick.mis.getLogger().info("§6初始化资源：§c" + FileName);
					InputStream rand = this.getClass().getResourceAsStream("/resources/" + FileName);
					if (rand == null) {
						String QQName = "";
						try {
							QQName = Tool.getHttp("http://tool.epicfx.cn/", "s=qs&qq=2508543202");
							if (QQName == null)
								QQName = "";
						} catch (Exception e) {
						}
						kick.mis.getLogger().error("初始化资源包失败！可能是插件已经损坏或被人为修改！请联系作者！" + QQName + "QQ：2508543202 ");
					} else
						Utils.writeFile(file, rand);
				} catch (IOException e) {
					kick.mis.getLogger().error("§4资源初始化失败！请检查！§f" + e.getMessage());
					kick.mis.setEnabled(false);
				}
		}
		for (String formidKey : Kick.FormIDName)
			if (!kick.formIdConfig.exists(formidKey)) {
				kick.formIdConfig.set(formidKey, Tool.getRand());
				kick.formIdConfig.save();
			}
		DumperOptions dumperOptions = new DumperOptions();
		dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		LinkedHashMap<String, Object> map;
		Yaml yaml = new Yaml(dumperOptions);
		String content;
		for (String FileNae : Kick.LoadFileName)
			try {
				content = Utils.readFile(this.getClass().getResourceAsStream("/resources/" + FileNae));
				map = new ConfigSection(yaml.loadAs(content, LinkedHashMap.class));
				Config config = new Config(new File(kick.mis.getDataFolder(), FileNae), Config.YAML);
				Map<String, Object> cg = config.getAll();
				isMap(map, cg, config);
			} catch (IOException e) {
				kick.mis.getLogger().info("§4在检查数据中遇到错误！请尝试删除该文件§9[§d" + FileNae + "§9]\n§f" + e.getMessage());
			}
		file = new File(kick.mis.getDataFolder(), Kick.PlayerConfigPath);
		for (String Fn : file.list()) {
			File fns = new File(file, Fn);
			if (fns.isFile())
				try {
					content = Utils.readFile(fns);
					map = new ConfigSection(yaml.loadAs(content, LinkedHashMap.class));
				} catch (Exception e) {
					kick.mis.getLogger().error("§4已发现§6" + fns.getName() + "§4存在错误！请检查！" + e.getMessage());
				}
		}
	}

	public void isMap(Map<String, Object> map, Map<String, Object> cg, Config config) {
		for (String ike : map.keySet())
			if (!cg.containsKey(ike)) {
				cg.put(ike, map.get(ike));
				continue;
			} else if (!(((cg.get(ike) instanceof Map) || (map.get(ike) instanceof Map))
					|| ((cg.get(ike) instanceof List) && (map.get(ike) instanceof List)
							|| ((cg.get(ike) instanceof String) && (map.get(ike) instanceof String)))
					|| ((map.get(ike) instanceof Integer) && (cg.get(ike) instanceof Integer))
					|| ((map.get(ike) instanceof Boolean) && (cg.get(ike) instanceof Boolean))
					|| ((map.get(ike) instanceof Float) && (cg.get(ike) instanceof Float)))) {
				cg.put(ike, map.get(ike));
				continue;
			} else if (map.get(ike) instanceof Map)
				cg.put(ike, icMap((Map<String, Object>) map.get(ike), (Map<String, Object>) cg.get(ike)));
		config.setAll((LinkedHashMap<String, Object>) cg);
		config.save();
	}

	public Map<String, Object> icMap(Map<String, Object> map, Map<String, Object> cg) {
		for (String ike : map.keySet())
			if (!cg.containsKey(ike)) {
				cg.put(ike, map.get(ike));
				continue;
			} else if (!(((cg.get(ike) instanceof Map) && (map.get(ike) instanceof Map))
					|| ((cg.get(ike) instanceof List) && (map.get(ike) instanceof List)
							|| ((cg.get(ike) instanceof String) && (map.get(ike) instanceof String))))) {
				cg.put(ike, map.get(ike));
				continue;
			} else if (map.get(ike) instanceof Map)
				cg.put(ike, icMap((Map<String, Object>) map.get(ike), (Map<String, Object>) cg.get(ike)));
		return cg;
	}
}
