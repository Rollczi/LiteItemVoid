package dev.rollczi.liteitemvoid.deepvoid;

import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public class DeepVoidViewPage {

    private final static Consumer<InventoryClickEvent> NONE_ACTION = event -> {};

    private final int page;
    private final Inventory inventory;
    private final DeepVoidViewHolder holder;
    private final DeepVoidViewSettings settings;

    private DeepVoidViewPage(int page, Inventory inventory, DeepVoidViewHolder holder, DeepVoidViewSettings settings) {
        this.page = page;
        this.inventory = inventory;
        this.holder = holder;
        this.settings = settings;
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    public int getPage() {
        return page;
    }

    public void applyNextPage(DeepVoidViewPage next, Consumer<UUID> onClick) {
        this.item(6, 7, this.settings.nextPage(), event -> {
            HumanEntity whoClicked = event.getWhoClicked();

            if (!(whoClicked instanceof Player)) {
                return;
            }

            Player player = (Player) whoClicked;

            next.open(player);
            onClick.accept(player.getUniqueId());
        });
    }

    public void applyPreviousPage(DeepVoidViewPage previous, Consumer<UUID> onClick) {
        this.item(6, 3, this.settings.previousPage(), event -> {
            HumanEntity whoClicked = event.getWhoClicked();

            if (!(whoClicked instanceof Player)) {
                return;
            }

            Player player = (Player) whoClicked;

            previous.open(player);
            onClick.accept(player.getUniqueId());
        });
    }

    public boolean next(ItemStack itemStack) {
        return this.next(itemStack, null);
    }

    public boolean nextNoneAction(ItemStack itemStack) {
        return this.next(itemStack, event -> {});
    }

    public boolean next(ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        ItemStack[] contents = this.inventory.getContents();

        for (int slot = 0; slot < contents.length; slot++) {
            ItemStack content = contents[slot];

            if (content != null && !content.getType().isAir()) {
                continue;
            }

            if (holder.isClaimed(slot)) {
                continue;
            }

            this.item(slot, itemStack, consumer);
            return true;
        }

        return false;
    }

    public void item(int row, int col, ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        this.item(this.slot(row, col), itemStack, consumer);
    }

    public void item(int row, int col, ItemStack itemStack) {
        this.item(this.slot(row, col), itemStack, null);
    }

    public void item(int slot, ItemStack itemStack) {
        this.item(slot, itemStack, null);
    }

    public void item(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        this.inventory.setItem(slot, itemStack);

        if (consumer != null) {
            this.holder.action(slot, consumer);
        }
    }

    private int slot(int row, int col) {
        return (col + (row - 1) * 9) - 1;
    }

    public static DeepVoidViewPage create(int page, Server server, DeepVoidViewSettings settings) {
        DeepVoidViewHolder holder = new DeepVoidViewHolder();
        Inventory inventory = server.createInventory(holder, 9 * 6, settings.getTitle());
        holder.setInventory(inventory);

        DeepVoidViewPage deepVoidPage = new DeepVoidViewPage(page, inventory, holder, settings);

        ItemStack fill = settings.fill();
        ItemStack corners = settings.corners();

        deepVoidPage.item(6, 1, corners, NONE_ACTION);
        deepVoidPage.item(6, 2, fill, NONE_ACTION);
        deepVoidPage.item(6, 3, fill, NONE_ACTION);
        deepVoidPage.item(6, 4, fill, NONE_ACTION);
        deepVoidPage.item(6, 5, fill, NONE_ACTION);
        deepVoidPage.item(6, 6, fill, NONE_ACTION);
        deepVoidPage.item(6, 7, fill, NONE_ACTION);
        deepVoidPage.item(6, 8, fill, NONE_ACTION);
        deepVoidPage.item(6, 9, corners, NONE_ACTION);

        return deepVoidPage;
    }

}
