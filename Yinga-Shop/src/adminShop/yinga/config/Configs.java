package adminShop.yinga.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;

public class Configs {
	
	public static ArrayList<YamlConfiguration> categoriesFiles = new ArrayList<YamlConfiguration>();
	public static ArrayList<YamlConfiguration> ItemsFiles = new ArrayList<YamlConfiguration>();
	public static YamlConfiguration mainCategorie = null;
	public static YamlConfiguration allItemsCategorie = null;
	
	private static File ConfigFile = new File("plugins/YingaAdminshop","config.yml");
	private static YamlConfiguration  ConfigCon = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void createConfigs() {
		if(!ConfigFile.exists()) {
			ConfigCon.set("register-categories", null);
			ConfigCon.set("register-items", null);
			ConfigCon.set("main-categorie", null);
			ConfigCon.set("allItems-categorie", null); 
			
			ConfigCon.set("None", null);
			ConfigCon.set("LowToHightBuy", null);
			ConfigCon.set("HightToLowBuy", null);
			ConfigCon.set("LowToHightSell", null); 
			ConfigCon.set("HightToLowSell", null);
			ConfigCon.set("AToZ", null);
			ConfigCon.set("ZToA", null);
			ConfigCon.set("CollorOut", null); 
			ConfigCon.set("CollorOn", null); 
			try {
				ConfigCon.save(ConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getlToHB() {
		if(ConfigCon.getString("LowToHightBuy") != null) {
			return ConfigCon.getString("LowToHightBuy");
		}else {
			return null;
		}
	}
	
	public static String gethToLB() {
		if(ConfigCon.getString("HightToLowBuy") != null) {
			return ConfigCon.getString("HightToLowBuy");
		}else {
			return null;
		}
	}
	
	public static String getlToHS() {
		if(ConfigCon.getString("LowToHightSell") != null) {
			return ConfigCon.getString("LowToHightSell");
		}else {
			return null;
		}
	}
	
	public static String gethToLS() {
		if(ConfigCon.getString("HightToLowSell") != null) {
			return ConfigCon.getString("HightToLowSell");
		}else {
			return null;
		}
	}
	
	public static String getAToZ() {
		if(ConfigCon.getString("AToZ") != null) {
			return ConfigCon.getString("AToZ");
		}else {
			return null;
		}
	}
	
	public static String getZToA() {
		if(ConfigCon.getString("ZToA") != null) {
			return ConfigCon.getString("ZToA");
		}else {
			return null;
		}
	}
	
	public static String getCollorOut() {
		if(ConfigCon.getString("CollorOut") != null) {
			return ConfigCon.getString("CollorOut");
		}else {
			return null;
		}
	}
	
	public static String getCollorOn() {
		if(ConfigCon.getString("CollorOn") != null) {
			return ConfigCon.getString("CollorOn");
		}else {
			return null;
		}
	}
	
	public static String getNone() {
		if(ConfigCon.getString("None") != null) {
			return ConfigCon.getString("None");
		}else {
			return null;
		}
	}
	
	public static void LoadConfigs() {
		
		if(ConfigCon.getList("register-categories") != null) {
			if(ConfigCon.getString("main-categorie") != null) {
				File categorieFile = new File("plugins/YingaAdminshop/categories", ConfigCon.getString("main-categorie")+".yml");
				if(categorieFile.exists()) {
					mainCategorie = YamlConfiguration.loadConfiguration(categorieFile);
				}
			}
			if(ConfigCon.getString("allItems-categorie") != null) {
				File categorieFile = new File("plugins/YingaAdminshop/categories", ConfigCon.getString("allItems-categorie")+".yml");
				if(categorieFile.exists()) {
					allItemsCategorie = YamlConfiguration.loadConfiguration(categorieFile);
				}
			}
			for(Object o: ConfigCon.getList("register-categories")) {
				String s = o.toString();
				File categorieFile = new File("plugins/YingaAdminshop/categories", s+".yml");
				if(categorieFile.exists()) {
					categoriesFiles.add(YamlConfiguration.loadConfiguration(categorieFile));
				}
			}
		}
		if(ConfigCon.getList("register-items") != null) {
			for(Object o: ConfigCon.getList("register-items")) {
				String s = o.toString();
				File itemsFile = new File("plugins/YingaAdminshop/items", s+".yml");
				if(itemsFile.exists()) {
					ItemsFiles.add(YamlConfiguration.loadConfiguration(itemsFile));
				}
			}
		}

	}
	
}
