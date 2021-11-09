package adminShop.yinga.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import adminShop.yinga.gui.Gui;
import adminShop.yinga.gui.ItemSorting;
import adminShop.yinga.register.Categories;
import adminShop.yinga.register.Funktions;

public class Command implements CommandExecutor { 

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String string, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 0) {
				p.openInventory(Gui.create(Funktions.main.name, 1, ItemSorting.None));
				Gui.sortPlayer.set(Gui.player.indexOf(p), ItemSorting.None);
				Gui.pages.set(Gui.player.indexOf(p), 1);
			}else if(args.length == 1) {
				if(Categories.names.contains(args[0])) {
					p.openInventory(Gui.create(args[0], 1, Gui.sortPlayer.get(Gui.player.indexOf(p))));
					Gui.pages.set(Gui.player.indexOf(p), 1);
				}else {
					p.sendMessage("§4Die Kategorie " + args[0] + "existiert nicht!");
				}
			}else {
				p.sendMessage("§4Bitte benutze /shop <Kategorie>!");
			}
		}
		
		return false;
	}

}
