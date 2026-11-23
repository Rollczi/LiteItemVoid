package dev.rollczi.liteitemvoid.deepvoid;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;

public class DeepVoidViewController implements Listener {

    private final DeepVoidView globalView;

    public DeepVoidViewController(DeepVoidView globalView) {
        this.globalView = globalView;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (!(holder instanceof DeepVoidViewHolder) || !(event.getPlayer() instanceof Player)) {
            return;
        }

        this.globalView.markClosed((Player) event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (!(holder instanceof DeepVoidViewHolder) || !(event.getWhoClicked() instanceof Player)) {
            return;
        }

        DeepVoidViewHolder viewHolder = (DeepVoidViewHolder) holder;

        viewHolder.action(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDrag(InventoryDragEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (!(holder instanceof DeepVoidViewHolder)) {
            return;
        }

        event.setCancelled(true);
    }

}
