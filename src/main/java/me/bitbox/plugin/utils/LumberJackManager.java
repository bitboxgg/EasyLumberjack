package me.bitbox.plugin.utils;

import me.bitbox.core.Server;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LumberJackManager {
    private static final HashMap<Player, Integer> playerBreakLogCount = new HashMap<>();
    private static final List<Player> editingPlayers = new ArrayList<>();

    public static void addEditingPlayer(Player player) {
        editingPlayers.add(player);
    }

    public static void removeEditingPlayer(Player player) {
        editingPlayers.remove(player);
    }

    public static boolean isPlayerEditing(Player player) {
        return editingPlayers.contains(player);
    }

    public static Integer getPlayerCount(Player player) {
        if (playerBreakLogCount.containsKey(player)) {
            return playerBreakLogCount.get(player);
        }
        return 0;
    }

    public static void setPlayerCount(Player player, Integer count) {
        playerBreakLogCount.put(player, count);
    }

    public static Boolean isItTree(Location location) {
        ConfigurationSection section = Server.data.config.getConfigurationSection("LOCATIONS");
        if (section == null) {
            return false;
        }

        Set<String> keys = section.getKeys(false);
        for (String key : keys) {
            Location currentLocation = section.getLocation(key);
            assert currentLocation != null;
            if (locationEquals(currentLocation, location)) {
                if (currentLocation.getBlock().getType() == Material.OAK_LOG) {
                    return true;
                }
            }
            for (int y = currentLocation.getBlockY() + 1; y < currentLocation.getBlockY() + 5; y++) {
                Location upperLocation = new Location(currentLocation.getWorld(), currentLocation.getBlockX(), y, currentLocation.getBlockZ());
                Block upperBlock = upperLocation.getBlock();

                if (upperBlock.getType() == Material.AIR) {
                    break;
                }

                if (locationEquals(upperBlock.getLocation(), location)) {
                    if (upperBlock.getType() == Material.OAK_LOG) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void destroyTree(Location location) {
        if (isItTree(location)) {
            for (int Y = location.getBlockY(); Y < 8; Y++) {
                Block currentBlock = location.getWorld().getBlockAt(location.getBlockX(), Y, location.getBlockZ());
                if (currentBlock.getType() == Material.OAK_LOG) {
                    currentBlock.getDrops().clear();
                    currentBlock.breakNaturally();
                }
            }
        }
    }

    public static void removeTree(Location location) {
        ConfigurationSection section = Server.data.config.getConfigurationSection("LOCATIONS");
        if (section == null) {
            return;
        }

        Set<String> keys = section.getKeys(false);
        for (String key : keys) {
            Location currentLocation = section.getLocation(key);
            assert currentLocation != null;
            if (locationEquals(location, currentLocation)) {
                section.set(key, null);
                Server.data.save();
            }
        }
    }

    private static Boolean locationEquals(Location location, Location location2) {
        return location.getBlockX() == location2.getBlockX() && location.getBlockY() == location2.getBlockY() && location.getBlockZ() == location2.getBlockZ();
    }
}
