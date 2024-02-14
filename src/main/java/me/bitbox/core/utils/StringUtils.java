package me.bitbox.core.utils;

import me.bitbox.plugin.utils.LumberJackManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private final static Pattern HEX_PATTERN = Pattern
            .compile("&(#[a-f0-9]{6})", Pattern.CASE_INSENSITIVE);

    @NotNull
    public static String colorize(@NotNull String input) {
        Matcher m = HEX_PATTERN.matcher(input);
        while (m.find()) {
            input = input.replace(m.group(), ChatColor.of(m.group(1)).toString());
        }
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    @NotNull
    public static String replacePlaceholders(final @NotNull String input, final @NotNull Player player) {
        String replaced = input.replace("%current_count%", (LumberJackManager.getPlayerCount(player).toString() + "/" + ConfigSettings.getCount()));
        replaced = replaced.replace("%reward%", ConfigSettings.getReward().toString());
        return replaced;
    }
}
