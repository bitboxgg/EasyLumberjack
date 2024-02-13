package me.bitbox.plugin.commands;

import me.bitbox.core.Server;
import me.bitbox.core.utils.StringUtils;
import me.bitbox.plugin.utils.LumberJackManager;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;


public class LumberJackListener implements Listener {
    @EventHandler
    public void interactEvent(PlayerInteractEvent event) {
        if (LumberJackManager.isPlayerEditing(event.getPlayer())) {
            if (event.getHand() != EquipmentSlot.HAND) return;
            Block block = event.getClickedBlock();
            if (block == null) return;

            FileConfiguration data = Server.data.config;
            switch (event.getAction()) {
                case RIGHT_CLICK_BLOCK: {
                    Location treeLocation = block.getLocation().add(0, 1, 0);
                    if (treeLocation.getWorld().generateTree(treeLocation, TreeType.TREE)) {
                        data.set("LOCATIONS." + UUID.randomUUID(), treeLocation);
                        Server.data.save();
                        event.getPlayer().sendMessage(StringUtils.colorize("&aPlaced tree."));
                    } else {
                        event.getPlayer().sendMessage(StringUtils.colorize("&cCannot place tree here."));
                    }
                    break;
                }
                case LEFT_CLICK_BLOCK: {
                    if(event.getPlayer().isSneaking()) {
                        return;
                    }
                    if (LumberJackManager.isItTree(block.getLocation())) {
                        LumberJackManager.removeTree(block.getLocation());
                        LumberJackManager.destroyTree(block.getLocation());
                        event.getPlayer().sendMessage(StringUtils.colorize("&cYou are removed that tree"));
                    } else {
                        event.getPlayer().sendMessage(StringUtils.colorize("&cTo remove tree you need click on bottom of the tree"));
                    }
                    break;
                }
            }
            event.setCancelled(true);
        }
    }
}
