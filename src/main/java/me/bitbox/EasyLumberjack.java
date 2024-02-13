package me.bitbox;

import me.bitbox.core.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyLumberjack extends JavaPlugin {
    private static EasyLumberjack INSTANCE;
    public static EasyLumberjack getInstance() {
        return INSTANCE;
    }
    @Override
    public void onLoad() {
        INSTANCE = this;
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        Server.init();
    }
}
