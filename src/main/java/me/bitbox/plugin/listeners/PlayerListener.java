package me.bitbox.plugin.listeners;

import me.bitbox.core.Server;
import me.bitbox.core.utils.DataManager;
import me.bitbox.plugin.utils.LumberJackManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static me.bitbox.core.utils.StringUtils.colorize;
import static me.bitbox.core.utils.StringUtils.replacePlaceholders;

public class PlayerListener implements Listener {
    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.OAK_LOG) {
            if (LumberJackManager.isItTree(event.getBlock().getLocation())) {
                int current = LumberJackManager.getPlayerCount(event.getPlayer());
                if (current == 0) {
                    LumberJackManager.setPlayerCount(event.getPlayer(), 1);
                    event.getPlayer().sendMessage(colorize(replacePlaceholders(DataManager.getCountMessage(), event.getPlayer())));
                } else {
                    if (current >= DataManager.getCount()) {
                        LumberJackManager.setPlayerCount(event.getPlayer(), 0);
                        event.getPlayer().sendMessage(colorize(replacePlaceholders(DataManager.getRewardMessage(), event.getPlayer())));
                        Server.economy.depositPlayer(event.getPlayer(), DataManager.getReward());
                    } else {
                        LumberJackManager.setPlayerCount(event.getPlayer(), LumberJackManager.getPlayerCount(event.getPlayer()) + 1);
                        event.getPlayer().sendMessage(colorize(replacePlaceholders(DataManager.getCountMessage(), event.getPlayer())));
                    }
                }
                event.setCancelled(true);
            }
        }
    }
}
