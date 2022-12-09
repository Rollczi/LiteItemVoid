/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.deepvoid;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.LongFunction;

public class DeepVoid {

    private final List<ItemStack> voidItems = new ArrayList<>();
    private boolean closed = true;
    private boolean enabled = true;
    private long timeToOpen = 0;

    public boolean isClosed() {
        return closed;
    }

    public boolean isOpen() {
        return closed;
    }

    public void open() {
        this.closed = false;
    }

    public void close() {
        this.closed = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDisabled() {
        return !enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public long getTimeToOpen() {
        return timeToOpen;
    }

    public void updateTimeToOpen(LongFunction<Long> update) {
        this.timeToOpen = update.apply(timeToOpen);
    }

    public void clearItems() {
        this.voidItems.clear();
    }

    public void addVoidItems(List<ItemStack> itemsFromVoid) {
        this.voidItems.addAll(itemsFromVoid);
    }

    public List<ItemStack> getVoidItems() {
        return Collections.unmodifiableList(voidItems);
    }

}
