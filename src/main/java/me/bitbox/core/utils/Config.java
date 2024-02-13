package me.bitbox.core.utils;

import me.bitbox.EasyLumberjack;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {
    public final FileConfiguration config;
    public final File configFile;

    public Config(String fileName) {
        configFile = new File(EasyLumberjack.getInstance().getDataFolder(), fileName);
        if (!configFile.exists()) {
            EasyLumberjack.getInstance().saveResource(fileName, false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (Exception ignored) {}
    }
}