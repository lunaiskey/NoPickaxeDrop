package me.lunaiskey.pyrex.nopickdrop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class PickaxeDropListener implements Listener {

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
}
