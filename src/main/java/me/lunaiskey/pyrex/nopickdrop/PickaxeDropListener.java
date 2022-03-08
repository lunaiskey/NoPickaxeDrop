package me.lunaiskey.pyrex.nopickdrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PickaxeDropListener implements Listener {

    private NoPickDrop plugin;

    public PickaxeDropListener(NoPickDrop plugin) {
        this.plugin = plugin;
    }

    private Map<UUID,List<ItemStack>> respawnAddBack = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void onPickDrop(PlayerDropItemEvent e) {
        ItemStack item = e.getItemDrop().getItemStack();
        if (item.getType() == Material.DIAMOND_PICKAXE) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void onPickInventory(InventoryClickEvent e) {
        InventoryView view = e.getView();
        Inventory inv = e.getInventory();
        Inventory clickedInv = e.getClickedInventory();
        Player player = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null) {return;}
        //Debug messages please ignore.
        //player.sendMessage("View: "+ view.getType());
        //player.sendMessage("Clicked: "+clickedInv.getType());
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {return;}
        if (e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getKeepInventory()) {return;}
        List<ItemStack> addBack = new ArrayList<>();
        for (ItemStack item : e.getDrops()) {
            if (item.getType() == Material.DIAMOND_PICKAXE) {
                addBack.add(item.clone());
                item.setType(Material.AIR);

            }
        }
        respawnAddBack.put(e.getEntity().getUniqueId(),addBack);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        Bukkit.getScheduler().runTask(plugin,()->{
            if (respawnAddBack.containsKey(player.getUniqueId())) {
                List<ItemStack> items = respawnAddBack.get(player.getUniqueId());
                if (items.isEmpty()) {return;}
                for (ItemStack item : items) {
                    player.getInventory().addItem(item);
                }
                respawnAddBack.remove(player.getUniqueId());
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void onEntityClick(PlayerInteractEntityEvent e) {
        Entity clicked = e.getRightClicked();
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if (clicked instanceof ItemFrame || clicked instanceof ArmorStand) {
            if (item.getType() == Material.DIAMOND_PICKAXE) {e.setCancelled(true);}
        }
    }
}
