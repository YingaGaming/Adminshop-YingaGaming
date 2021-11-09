package adminShop.yinga.register;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;


public class Items {

	public static ArrayList<String> categories = new ArrayList<>();
	public static ArrayList<Items> itemsYS = new ArrayList<>();
	public static ArrayList<ItemStack> itemsIcon = new ArrayList<>();
	public static ArrayList<ItemStack> itemsGet = new ArrayList<>();
	
	public String categorie;
	public ItemStack itemIcon;
	public ItemStack itemGet;
	public Double preisBuy;
	public Double preisSell;
	
	public Items(YamlConfiguration con) {
		
		categorie = con.getString("categorie");
		categories.add(categorie);
		
		itemIcon = getItemFromConfig(con, "item-icon");
		itemsIcon.add(itemIcon);
		
		itemGet = getItemFromConfig(con, "item-get");
		itemsGet.add(itemGet);
		
		preisBuy = con.getDouble("buy");
		preisSell = con.getDouble("sell");
		
		itemsYS.add(this);
	}
	
	private static ItemStack getItemFromConfig(YamlConfiguration con, String arg) {
		ItemStack item = new ItemStack(Material.valueOf(con.getString(arg+".material")));
		if(con.getString(arg+".displayname") != null || con.getList(arg+".enchantments") != null || con.getList(arg+".lore") != null || con.getString(arg+".PotionType") != null) {

			ItemMeta itemMeta = item.getItemMeta();
			if(con.getString(arg+".displayname") != null) {
				itemMeta.setDisplayName(con.getString(arg+".displayname"));
			}
			if(con.getList(arg+".enchantments") != null) {
				for(Object o : con.getList(arg+".enchantments")) {
					String[] s = o.toString().split(",");
					itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(s[0])), Integer.valueOf(s[1]), true);
				}
			}
			if(con.getList(arg+".lore") != null) {
				ArrayList<String> lore = new ArrayList<>();
				for(Object o : con.getList(arg+".lore")) {
					lore.add(o.toString());
				}
				itemMeta.setLore(lore);
			}
			if(con.getString(arg+".PotionType") != null) {
				PotionMeta pm = (PotionMeta) itemMeta;
				pm.setBasePotionData(new PotionData(PotionType.valueOf(con.getString(arg+".PotionType"))));
			}
			item.setItemMeta(itemMeta);
		}
		if(con.getInt(arg+".amount") != 0) {
			item.setAmount(con.getInt(arg+".amount"));
		}
		return item;
	}
	
}
