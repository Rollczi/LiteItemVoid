package dev.rollczi.liteitemvoid.shared;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemStackList extends ArrayList<ItemStack> {

    private final boolean stacking;

    public ItemStackList(boolean stacking) {
        this.stacking = stacking;
    }

    @Override
    public boolean add(ItemStack original) {
        ItemStack itemStack = original.clone();

        if (!stacking) {
            return super.add(itemStack);
        }

        for (ItemStack current : this) {
            if (!current.isSimilar(itemStack)) {
                continue;
            }

            if (current.getAmount() >= current.getMaxStackSize()) {
                continue;
            }

            int difference = Math.min(current.getMaxStackSize() - current.getAmount(), itemStack.getAmount());

            current.setAmount(current.getAmount() + difference);

            if (itemStack.getAmount() <= difference) {
                return true;
            }

            itemStack.setAmount(itemStack.getAmount() - difference);
        }

        return super.add(itemStack);
    }

}
