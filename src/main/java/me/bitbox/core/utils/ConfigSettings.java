package me.bitbox.core.utils;

import me.bitbox.EasyLumberjack;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigSettings {
    private static final FileConfiguration config = EasyLumberjack.getInstance().getConfig();

    public static Integer getReward() {
        if (config.isInt("lumberjack.reward")) {
            return config.getInt("lumberjack.reward");
        }
        return 0;
    }

    public static Integer getCount() {
        if (config.isInt("lumberjack.count")) {
            return config.getInt("lumberjack.count");
        }
        return 0;
    }

    public static String getCountMessage() {
        if (config.isString("messages.break-log-count")) {
            return config.getString("messages.break-log-count");
        }
        return "";
    }

    public static String getRewardMessage() {
        if (config.isString("messages.break-tree-reward")) {
            return config.getString("messages.break-tree-reward");
        }
        return "";
    }
}
