package adminShop.yinga.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import adminShop.yinga.register.Categories;
import adminShop.yinga.register.Funktions;
import adminShop.yinga.register.Items;


public class Gui {
	public static ArrayList<ItemSorting> sortPlayer = new ArrayList<>();
	public static ArrayList<Integer> pages = new ArrayList<>();
	public static ArrayList<Player> player = new ArrayList<>();


	public static Inventory create(String name, int page, ItemSorting Sorting) {

		Inventory inv = Bukkit.createInventory(null, Categories.categories.get(Categories.names.indexOf(name)).inv.getSize(), Categories.categories.get(Categories.names.indexOf(name)).title);
		inv.setContents(Categories.categories.get(Categories.names.indexOf(name)).inv.getContents());

		int freeplace = 0;

		ItemSorting sorting = Sorting;

		for(ItemStack item : inv.getContents()) {
			if(item != null) {
				freeplace++;
			}
		}


		ArrayList<ItemStack> items = new ArrayList<>();
		if(!name.equals(Funktions.main.name) && ! name.equals(Funktions.allItems.name) && Categories.names.contains(name)) {
			for(ItemStack item : Items.itemsIcon) {
				if(Items.categories.get(Items.itemsIcon.indexOf(item)).equals(name)) {
					items.add(item);
				}
			}
		}else if(name.equals(Funktions.allItems.name)) {
			for(ItemStack item : Items.itemsIcon) {
					items.add(item);
			}
		}else{
			for(Categories ca : Categories.categories) {
				if(!ca.name.equals(Funktions.main.name) ) {
					items.add(ca.icon.clone());
				}
			}
		}

		ArrayList<ItemStack> itemsSorted = new ArrayList<>();
		if(!name.equals(Funktions.main.name)){
		if(sorting == ItemSorting.HighestToLowestBuy) {
			while (items.size() > 0) {
				double max = 0.0;
				ItemStack itemMax = null;
				for(ItemStack item : items) {
						if(Items.itemsYS.get(Items.itemsIcon.indexOf(item)).preisBuy > max) {
							max = Items.itemsYS.get(Items.itemsIcon.indexOf(item)).preisBuy;
							itemMax= item;
						}
				}
				itemsSorted.add(itemMax);
				items.remove(itemMax);
			}

		}else if(sorting == ItemSorting.LowestToHighestBuy){
			while (items.size() > 0) {
				double min = 99999999999999999999999999999999999999999999999999999999.0;
				ItemStack itemMin = null;
				for(ItemStack item : items) {
					if(Items.itemsYS.get(Items.itemsIcon.indexOf(item)).preisBuy < min) {
						min = Items.itemsYS.get(Items.itemsIcon.indexOf(item)).preisBuy;
						itemMin= item;
					}
				}
				itemsSorted.add(itemMin);
				items.remove(itemMin);
			}

		}else if(sorting == ItemSorting.HighestToLowestSell){
			while (items.size() > 0) {
				double max = 0.0;
				ItemStack itemMax = null;
				for(ItemStack item : items) {
					if(Items.itemsYS.get(Items.itemsIcon.indexOf(item)).preisSell > max) {
						max = Items.itemsYS.get(Items.itemsIcon.indexOf(item)).preisBuy;
						itemMax= item;
					}
				}
				itemsSorted.add(itemMax);
				items.remove(itemMax);
			}

		}else if(sorting == ItemSorting.LowestToHighestSell){
			while (items.size() > 0) {
				double min = 99999999999999999999999999999999999999999999999999999999.0;
				ItemStack itemMin = null;
				for(ItemStack item : items) {
					if(Items.itemsYS.get(Items.itemsIcon.indexOf(item)).preisSell < min) {
						min = Items.itemsYS.get(Items.itemsIcon.indexOf(item)).preisBuy;
						itemMin= item;
					}
				}
				itemsSorted.add(itemMin);
				items.remove(itemMin);
			}

		}else if(sorting == ItemSorting.AtoZ){
			while (items.size() > 0) {
				double charMin = 'Z';
				ItemStack itemMin = null;
				for(ItemStack item : items) {
					char[] c;
					if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()){
						c= item.getItemMeta().getDisplayName().toCharArray();
					}else{
						 c = item.getType().toString().toCharArray();
					}
					if(c[0] <= charMin) {
						charMin = c[0];
						itemMin= item;
					}
				}
				itemsSorted.add(itemMin);
				items.remove(itemMin);
			}

		}else if(sorting == ItemSorting.ZtoA){
			while (items.size() > 0) {
				double charMin = 'A';
				ItemStack itemMin = null;
				for(ItemStack item : items) {
					char[] c = item.getType().toString().toCharArray();
					if(c[0] >= charMin) {
						charMin = c[0];
						itemMin= item;
					}
				}
				itemsSorted.add(itemMin);
				items.remove(itemMin);
			}

		}else {
			for(ItemStack item : items) {
				itemsSorted.add(item);
			}
			items.clear();
		}

		}else {
			for(ItemStack item : items) {
				itemsSorted.add(item);
			}
			items.clear();

		}

		ArrayList<ArrayList<ItemStack>> pages = new ArrayList<>();
		int a = 0;
		while(itemsSorted.size() > 0 || a == 0) {
			int NumerInPage = 0;
			a= 1;
			Boolean isFinish = false;
			ArrayList<ItemStack> pageAdd = new ArrayList<ItemStack>();
			for(ItemStack item : itemsSorted) {
				NumerInPage++;
				pageAdd.add(item);
				if(NumerInPage == freeplace) {
					isFinish = true;
					pages.add(pageAdd);
					break;
				}
			}
			for(ItemStack item : pageAdd) {
				itemsSorted.remove(item);
			}
			if(!isFinish) {
				pages.add(pageAdd);
			}
		}


		if(page <=1) {
			int place = 0;
			for(ItemStack i : inv.getContents()) {
				if(i != null) {
					if(i.hasItemMeta() && i.getItemMeta().hasCustomModelData() && i.getItemMeta().getCustomModelData() == 998) {
					inv.setItem(place, Categories.categories.get(Categories.names.indexOf(name)).blankItem);
					}
				}
				place++;
			}
		}
		if(pages.size()-page <=0) {
			int place = 0;
			for(ItemStack i : inv.getContents()) {
				if(i != null) {
					if(i.hasItemMeta() && i.getItemMeta().hasCustomModelData() && i.getItemMeta().getCustomModelData() == 999) {
					inv.setItem(place, Categories.categories.get(Categories.names.indexOf(name)).blankItem);
					}
				}
				place++;
			}
		}
		{
			int place = 0;
			for(ItemStack i : inv.getContents()) {
				if(i != null) {
					if(i.hasItemMeta() && i.getItemMeta().hasCustomModelData() && i.getItemMeta().getCustomModelData() == 993) {
						ItemStack item = i.clone();
						ArrayList<String> lore = new ArrayList<>();
						if(sorting == ItemSorting.None) {
							lore.add(Funktions.CollorOn + Funktions.none);
						}else {
							lore.add(Funktions.CollorOut + Funktions.none);
						}
						if(sorting == ItemSorting.HighestToLowestBuy) {
							lore.add(Funktions.CollorOn + Funktions.hToLB);
						}else {
							lore.add(Funktions.CollorOut + Funktions.hToLB);
						}
						if(sorting == ItemSorting.LowestToHighestBuy) {
							lore.add(Funktions.CollorOn + Funktions.lToHB);
						}else {
							lore.add(Funktions.CollorOut + Funktions.lToHB);
						}
						if(sorting == ItemSorting.HighestToLowestSell) {
							lore.add(Funktions.CollorOn + Funktions.hToLS);
						}else {
							lore.add(Funktions.CollorOut + Funktions.hToLS);
						}
						if(sorting == ItemSorting.LowestToHighestSell) {
							lore.add(Funktions.CollorOn + Funktions.lToHS);
						}else {
							lore.add(Funktions.CollorOut + Funktions.lToHS);
						}
						if(sorting == ItemSorting.AtoZ) {
							lore.add(Funktions.CollorOn + Funktions.AToZ);
						}else {
							lore.add(Funktions.CollorOut + Funktions.AToZ);
						}
						if(sorting == ItemSorting.ZtoA) {
							lore.add(Funktions.CollorOn + Funktions.ZToA);
						}else {
							lore.add(Funktions.CollorOut + Funktions.ZToA);
						}
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setLore(lore);
						item.setItemMeta(itemMeta);
						inv.setItem(place, item);
					}
				}
				place++;
			}
		}
		ArrayList<ItemStack> pageItems = pages.get(page-1);
		int place = 0;
		for(ItemStack i : inv.getContents()) {

			if(pageItems.size() <= 0) {
				break;
			}
			if(i == null) {
				inv.setItem(place, pageItems.get(0).clone());
				pageItems.remove(pageItems.get(0));
			}
			place++;
		}



		return inv;
	}

}
