package me.bitbox.core;

import me.bitbox.EasyLumberjack;
import me.bitbox.core.commands.CommandManager;
import me.bitbox.core.utils.Config;
import me.bitbox.plugin.commands.LumberJackCommand;
import me.bitbox.plugin.commands.LumberJackListener;
import me.bitbox.plugin.listeners.PlayerListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class Server {
    public static Economy economy = null;
    public static Config data = new Config("data.yml");

    public static void init() {
        if (setupEconomy()) {
            CommandManager.registerCommand("lumberjack", new LumberJackCommand());
            EasyLumberjack.getInstance().getServer().getPluginManager().registerEvents(new LumberJackListener(), EasyLumberjack.getInstance());
            EasyLumberjack.getInstance().getServer().getPluginManager().registerEvents(new PlayerListener(), EasyLumberjack.getInstance());
        } else {
            getLogger().severe("EasyLumberJack needs vault api.");
        }
    }

    private static boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> registeredServiceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (registeredServiceProvider == null) {
            return false;
        }
        economy = registeredServiceProvider.getProvider();
        return true;
    }
}
