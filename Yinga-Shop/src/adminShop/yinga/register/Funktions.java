package adminShop.yinga.register;

import org.bukkit.configuration.file.YamlConfiguration;

import adminShop.yinga.config.Configs;

public class Funktions {

	public static Categories main = new Categories(Configs.mainCategorie);
	public static Categories allItems = new Categories(Configs.allItemsCategorie);
	public static String none ;
	public static String lToHB ;
	public static String lToHS ;
	public static String hToLB ;
	public static String hToLS ;
	public static String AToZ ;
	public static String ZToA ;
	
	public static String CollorOut ;
	public static String CollorOn ;
	public static void Load() {
		

		none = Configs.getNone();
		lToHB = Configs.getlToHB();
		lToHS = Configs.getlToHS();
		hToLB = Configs.gethToLB();
		hToLS = Configs.gethToLS();
		
		AToZ = Configs.getAToZ();
		ZToA = Configs.getZToA();
		CollorOut = Configs.getCollorOut();
		CollorOn = Configs.getCollorOn();
		
		for(YamlConfiguration con : Configs.ItemsFiles) {
			new Items(con);
		}
		for(YamlConfiguration con : Configs.categoriesFiles) {
			new Categories(con);
		}
	}
	
}
