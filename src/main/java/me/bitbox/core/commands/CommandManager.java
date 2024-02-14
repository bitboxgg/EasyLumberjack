package me.bitbox.core.commands;

import me.bitbox.EasyLumberjack;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommandManager {
    public static void registerCommand(String commandName, Command commandExecutor) {
        PluginCommand command = EasyLumberjack.getInstance().getServer().getPluginCommand(commandName);
        if (command == null) {
            EasyLumberjack.getInstance().getLogger().info("Command not found");
            return;
        }
        command.setExecutor((player, __, ___, args) -> {
            if (!(player instanceof Player)) return false;
            commandExecutor.run((Player) player, args);
            return true;
        });
        command.setTabCompleter(new TabCompleter() {
            @Override
            public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
                return commandExecutor.tabComplete((Player) commandSender, strings);
            }
        });
    }
}
