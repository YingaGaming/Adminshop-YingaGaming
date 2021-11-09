package adminShop.yinga.eventHandler;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import adminShop.yinga.gui.Gui;
import adminShop.yinga.gui.ItemSorting;
import adminShop.yinga.main.Main;
import adminShop.yinga.register.Categories;
import adminShop.yinga.register.Funktions;
import adminShop.yinga.register.Items;
import net.milkbowl.vault.economy.Economy;

public class Listener implements org.bukkit.event.Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Gui.player.add(e.getPlayer());
		Gui.sortPlayer.add(ItemSorting.None);
		Gui.pages.add(1);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Gui.sortPlayer.remove(Gui.player.indexOf(e.getPlayer()));
		Gui.pages.remove(Gui.player.indexOf(e.getPlayer()));
		Gui.player.remove(e.getPlayer());
	}
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Economy economy = Main.eco;
		Player p = (Player) e.getWhoClicked();
		if(Categories.titls.contains(e.getView().getTitle())) {
			if(e.getRawSlot() <= 53) {
				if(e.getCurrentItem() != null) {

					ItemSorting itemSorting = Gui.sortPlayer.get(Gui.player.indexOf(p));

					if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasCustomModelData() && e.getCurrentItem().getItemMeta().getCustomModelData() == 994) {
						p.openInventory(Gui.create(Funktions.main.name, 1, itemSorting));
						Gui.pages.set(Gui.player.indexOf(p), 1);
					}else if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasCustomModelData() && e.getCurrentItem().getItemMeta().getCustomModelData() == 999) {
						Gui.pages.set(Gui.player.indexOf(p), Gui.pages.get(Gui.player.indexOf(p))+1);
						p.openInventory(Gui.create(Categories.names.get(Categories.titls.indexOf(e.getView().getTitle())), Gui.pages.get(Gui.player.indexOf(p)), itemSorting));
					}else if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasCustomModelData() && e.getCurrentItem().getItemMeta().getCustomModelData() == 998) {
						Gui.pages.set(Gui.player.indexOf(p), Gui.pages.get(Gui.player.indexOf(p))-1);
						p.openInventory(Gui.create(Categories.names.get(Categories.titls.indexOf(e.getView().getTitle())), Gui.pages.get(Gui.player.indexOf(p)), itemSorting));
					}else if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasCustomModelData() && e.getCurrentItem().getItemMeta().getCustomModelData() == 993) {
						if(itemSorting == ItemSorting.None) {
							itemSorting = ItemSorting.HighestToLowestBuy;
						} else if(itemSorting == ItemSorting.HighestToLowestBuy) {
							itemSorting = ItemSorting.LowestToHighestBuy;
						}else if(itemSorting == ItemSorting.LowestToHighestBuy) {
							itemSorting = ItemSorting.HighestToLowestSell;
						}else if(itemSorting == ItemSorting.HighestToLowestSell) {
							itemSorting = ItemSorting.LowestToHighestSell;
						}else if(itemSorting == ItemSorting.LowestToHighestSell) {
							itemSorting = ItemSorting.AtoZ;
						}else if(itemSorting == ItemSorting.AtoZ) {
							itemSorting = ItemSorting.ZtoA;
						}else if(itemSorting == ItemSorting.ZtoA) {
							itemSorting = ItemSorting.None;
						}
						p.openInventory(Gui.create(Categories.names.get(Categories.titls.indexOf(e.getView().getTitle())), Gui.pages.get(Gui.player.indexOf(p)), itemSorting));
						Gui.sortPlayer.set(Gui.player.indexOf(p), itemSorting);
					}else if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasCustomModelData() && e.getCurrentItem().getItemMeta().getCustomModelData() == 997) {
						int items = 0;
						Double getCoins = 0.0;
						ArrayList<ItemStack> mat = new ArrayList<>();
						for(ItemStack item : p.getInventory().getContents()) {
							if(item != null) {
								int Amount = item.getAmount();
								item.setAmount(1);
								if(Items.itemsGet.contains(item)) {
									items += Amount;
									getCoins += Items.itemsYS.get(Items.itemsGet.indexOf(item)).preisSell*Amount;
									item.setAmount(0);
									mat.add(item);
								}
							}
						}
						
						if(items > 0) {
							for(ItemStack mate :mat) {
								p.getInventory().remove(mate);;
							}
							economy.depositPlayer(p, getCoins);
							p.sendMessage(ChatColor.GREEN + "Du hast §6" + items + ChatColor.GREEN + " verkauft §6+" + getCoins + "$");
						}else {
							p.sendMessage("§4Du hast keine Items zu Verkaufen!");
						}

					}else if(Categories.icons.contains(e.getCurrentItem())) {
						p.openInventory(Gui.create(Categories.names.get(Categories.icons.indexOf(e.getCurrentItem())), 1, itemSorting));
						Gui.pages.set(Gui.player.indexOf(p), 1);
					}else if(Items.itemsIcon.contains(e.getCurrentItem())) {
						if(e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT ||e.getClick() == ClickType.MIDDLE) {
							int items = 0;
							if(e.getClick() == ClickType.LEFT) {
								items = 1;
							}else if(e.getClick() == ClickType.SHIFT_LEFT) {
								items = 64;
							}else if(e.getClick() == ClickType.MIDDLE) {
								for(ItemStack item : e.getInventory().getContents()) {
									if(item == null) {
										items += 64;
									}
								}
							}

							while (Items.itemsYS.get(Items.itemsIcon.indexOf(e.getCurrentItem())).preisBuy*items > economy.getBalance(p)) {
								items--;
							}
							if(items > 0) {
								economy.withdrawPlayer(p, Items.itemsYS.get(Items.itemsIcon.indexOf(e.getCurrentItem())).preisBuy*items);
								ItemStack item = Items.itemsGet.get(Items.itemsIcon.indexOf(e.getCurrentItem())).clone();
								item.setAmount(items);
								p.getInventory().addItem(item);
								p.sendMessage(ChatColor.GREEN + "Du hast §6" + items + ChatColor.GREEN + " gekauft. §6-" + Items.itemsYS.get(Items.itemsIcon.indexOf(e.getCurrentItem())).preisBuy*items + "$");

							}else {
								p.sendMessage("§4Du hast zu wenig Geld dafür!");
							}
						}else if(e.getClick() == ClickType.RIGHT  || e.getClick() == ClickType.SHIFT_RIGHT) {
							 int items = 0;
							 for(ItemStack item : p.getInventory().getContents()) {
								if(item != null) {
									 ItemStack i = Items.itemsGet.get(Items.itemsIcon.indexOf(e.getCurrentItem())).clone();
									 i.setAmount(item.getAmount());
									 if(i.equals(item)) {
										 items += item.getAmount();
									 }
								}
							 }

							 if(items > 0 ) {
								 int itemsSell = 0;
								 if(e.getClick() == ClickType.RIGHT) {
									 items--;
									 itemsSell++;
								 }else if(e.getClick() == ClickType.SHIFT_RIGHT) {
									 if(items -64 >= 0) {
										 items-= 64;
										 itemsSell = 64;
									 }else {
										 itemsSell = items;
										 items = 0;
									 }
								 }
								 ItemStack i = Items.itemsGet.get(Items.itemsIcon.indexOf(e.getCurrentItem())).clone();
								 i.setAmount(itemsSell);
								 p.getInventory().remove(i.getType());

								 if(items > 0) {
									 i.setAmount(items);
									 p.getInventory().addItem(i);
								 }
								 economy.depositPlayer( p, Items.itemsYS.get(Items.itemsIcon.indexOf(e.getCurrentItem())).preisSell*itemsSell);
								 p.sendMessage(ChatColor.GREEN + "Du hast §6" + itemsSell + ChatColor.GREEN + " verkauft. §6+" + Items.itemsYS.get(Items.itemsIcon.indexOf(e.getCurrentItem())).preisSell*itemsSell + "$");
							 }else {
								 p.sendMessage("§4Du hast keine Items zu Verkaufen");
							 }

						}
					}



				}
				e.setCancelled(true);
			}
		}
	}
}
