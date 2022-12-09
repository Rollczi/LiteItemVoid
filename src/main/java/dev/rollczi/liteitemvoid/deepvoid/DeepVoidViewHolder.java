package dev.rollczi.liteitemvoid.deepvoid;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DeepVoidViewHolder implements InventoryHolder {

    private Inventory inventory;
    private final Map<Integer, Consumer<InventoryClickEvent>> actions = new HashMap<>();
    private BiConsumer<InventoryClickEvent, DeepVoidViewHolder> onNoneClick = (inventoryClickEvent, deepVoidViewHolder) -> {};

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void action(int slot, Consumer<InventoryClickEvent> consumer) {
        this.actions.put(slot, consumer);
    }

    public boolean isClaimed(int slot) {
        return this.actions.get(slot) != null;
    }

    public void noneClick(BiConsumer<InventoryClickEvent, DeepVoidViewHolder> onNoneClick) {
        this.onNoneClick = onNoneClick;
    }

    public void action(InventoryClickEvent event) {
        Consumer<InventoryClickEvent> consumer = this.actions.get(event.getSlot());

        if (consumer == null) {
            onNoneClick.accept(event, this);
            return;
        }

        event.setCancelled(true);
        consumer.accept(event);
    }

}
