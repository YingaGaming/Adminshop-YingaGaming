package adminShop.yinga.register;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import adminShop.yinga.main.Main;

public class Categories {

	public static ArrayList<String> names = new ArrayList<>();
	public static ArrayList<String> titls = new ArrayList<>();
	public static ArrayList<ItemStack> icons = new ArrayList<>();
	public static ArrayList<Categories> categories = new ArrayList<>();
	
	public String name;
	public String title;
	public Inventory inv;
	public ItemStack icon;
	public ItemStack blankItem;
	
	public Categories(YamlConfiguration con) {
		name = con.getString("name(command)");
		title = con.getString("title");
		icon = getItemFromConfig(con, "item-icon");
		
		ItemStack changePageForward = getItemFromConfig(con, "change-page-forward[pf]", 999);
		
		ItemStack changePageBack = getItemFromConfig(con, "change-page-back[pb]", 998);
		
		ItemStack sellAll = getItemFromConfig(con, "sellAll[sa]", 997);
		
		ItemStack close = getItemFromConfig(con, "close[c]", 996);
		
		 blankItem = getItemFromConfig(con, "blankItem[b]", 995);
		
		ItemStack cat = getItemFromConfig(con, "categories[ca]", 994);
		
		ItemStack sorter = getItemFromConfig(con, "sorter[s]", 993);
		
		
		inv = Bukkit.createInventory(null, 9*6, title);
		int slot = -1;
		for(Object o: con.getList("inventory")) {
			String[] string = o.toString().split(",");
			for(String s : string) {
				slot++;
				if(s.equals("pf")) {
					inv.setItem(slot, changePageForward);
				} else if(s.equals("pb")) {
					inv.setItem(slot, changePageBack);
				}else if(s.equals("sa")) {
					inv.setItem(slot, sellAll);
				}else if(s.equals("c")) {
					inv.setItem(slot, close);
				}else if(s.equals("b")) {
					inv.setItem(slot, blankItem);
				}else if(s.equals("ca")) {
					inv.setItem(slot, cat);
				}else if(s.equals("s")) {
					inv.setItem(slot, sorter);
				}
			}
		}
		names.add(name);
		titls.add(title);
		icons.add(icon);
		categories.add(this);
		

		Main.logger.info("Categorie " + con.getString("title") + " add");
	}
	
	public static ItemStack getItemFromConfig(YamlConfiguration con, String arg, int CustomModelData) {
		ItemStack item = new ItemStack(Material.valueOf(con.getString(arg+".material")));
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
		itemMeta.setCustomModelData(CustomModelData);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	private static ItemStack getItemFromConfig(YamlConfiguration con, String arg) {
		ItemStack item = new ItemStack(Material.valueOf(con.getString(arg+".material")));
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
		item.setItemMeta(itemMeta);
		return item;
	}
	
}
