package adminShop.yinga.main;


import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
//import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import adminShop.yinga.command.Command;
import adminShop.yinga.config.Configs;
import adminShop.yinga.eventHandler.Listener;
import adminShop.yinga.gui.Gui;
import adminShop.yinga.gui.ItemSorting;
import adminShop.yinga.register.Funktions;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin{
	
	public static Economy eco;
	public static Logger logger;
	
	@Override
	public void onEnable() {
		if(!setupEconomy()) {
			getLogger().warning("Vault isn't installing");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			Gui.player.add(p);
			Gui.sortPlayer.add(ItemSorting.None);
			Gui.pages.add(1);
		}
		getServer().getPluginManager().registerEvents(new Listener(), this);
		logger = getLogger();
		Configs.createConfigs();
		Configs.LoadConfigs();
		Funktions.Load();
		getCommand("shop").setExecutor(new Command());
		getLogger().info("enable");
	}

	@Override
	public void onDisable() {
		getLogger().info("disable");
	}

	  private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }


}
