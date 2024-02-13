package me.bitbox.plugin.commands;

import me.bitbox.EasyLumberjack;
import me.bitbox.core.commands.Command;
import me.bitbox.plugin.utils.LumberJackManager;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.bitbox.core.utils.StringUtils.colorize;
import static net.kyori.adventure.text.Component.text;

public class LumberJackCommand implements Command {
    @Override
    public void run(Player player, String[] args) {
        if (args != null && args.length > 0) {
            switch (args[0]) {
                case "setup": {
                    if (player.hasPermission("easylumberjack.setup")) {
                        if (LumberJackManager.isPlayerEditing(player)) {
                            player.sendMessage(colorize("&cYou're arleady in setup mode"));
                            player.sendMessage(text(colorize("&7 ⋇ &aClick&7 to stop setup mode")).clickEvent(ClickEvent.suggestCommand("elj stop")));
                            return;
                        }
                        player.sendMessage(colorize("&aYou've entered setup mode."));
                        player.sendMessage(colorize("&7 ⋇ Click on block that you want to place a tree."));
                        player.sendMessage(colorize("&7 ⋇ Break to remove tree."));
                        player.sendMessage(text(colorize("&7 ⋇ &aClick&7 or write '/elj stop' to stop setup mode")).clickEvent(ClickEvent.suggestCommand("elj stop")));

                        LumberJackManager.addEditingPlayer(player);
                    } else {
                        player.sendMessage(colorize("&cYou don't have permission"));
                    }
                    break;
                }
                case "stop": {
                    if (player.hasPermission("easylumberjack.setup")) {
                        player.sendMessage(colorize("&cYou're stopped editing"));
                        LumberJackManager.removeEditingPlayer(player);
                    } else {
                        player.sendMessage(colorize("&cYou don't have permission"));
                    }
                    break;
                }
                case "reload": {
                    if (player.hasPermission("easylumberjack.reload")) {
                        player.sendMessage(colorize("&aPlugin config reloading"));
                        EasyLumberjack.getInstance().reloadConfig();
                    } else {
                        player.sendMessage(colorize("&cYou don't have permission"));
                    }
                    break;
                }
                default: {
                    player.sendMessage(colorize("&cIncorrect syntax"));
                    break;
                }
            }
        }
    }

    @Override
    public List<String> tabComplete(Player player, String[] args) {
        if (args != null && args.length > 0) {
            List<String> suggestions = new ArrayList<>();
            boolean hasAnyPermission = false;
            if (player.hasPermission("easylumberjack.reload")) {
                suggestions.add("reload");
                hasAnyPermission = true;
            }
            if (player.hasPermission("easylumberjack.setup")) {
                suggestions.add("setup");
                suggestions.add("stop");
                hasAnyPermission = true;
            }
            if (!hasAnyPermission) {
                suggestions.add(colorize("&cYou don't have permission"));
            }
            return suggestions;
        }
        return null;
    }
}
