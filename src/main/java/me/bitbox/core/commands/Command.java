package me.bitbox.core.commands;

import org.bukkit.entity.Player;

import java.util.List;

public interface Command {
    void run(Player player, String[] args);
    List<String> tabComplete(Player player, String[] args);
}
