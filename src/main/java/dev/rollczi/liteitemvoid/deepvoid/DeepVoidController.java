/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.deepvoid;

import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import dev.rollczi.liteitemvoid.shared.ItemStackList;
import net.kyori.adventure.platform.AudienceProvider;
import org.bukkit.entity.Entity;
import dev.rollczi.liteitemvoid.scheduler.Scheduler;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class DeepVoidController {

    private final AudienceProvider audienceProvider;
    private final Server server;
    private final PluginConfig config;
    private final Scheduler scheduler;
    private final DeepVoid deepVoid;
    private final DeepVoidView view;

    public DeepVoidController(AudienceProvider audienceProvider, Server server, PluginConfig config, Scheduler scheduler, DeepVoid deepVoid, DeepVoidView view) {
        this.audienceProvider = audienceProvider;
        this.server = server;
        this.config = config;
        this.scheduler = scheduler;
        this.deepVoid = deepVoid;
        this.view = view;
        this.deepVoid.updateTimeToOpen(time -> config.timeClear + config.timeClearDelay);
    }

    public void runClearTask() {
        this.nextTick();
    }

    private void nextTick() {
        if (this.deepVoid.isDisabled()) {
            this.scheduler.runTaskLater(this::nextTick, 1L);
            return;
        }

        Component component = config.messagesInTime.get(this.deepVoid.getTimeToOpen());

        if (component != null) {
            audienceProvider.players().sendMessage(component);
        }

        if (this.deepVoid.getTimeToOpen() <= 0) {
            this.clearWorldsAndOpenDeepVoid();
            this.deepVoid.updateTimeToOpen(time -> config.timeClear + config.timeClearDelay);
            this.scheduler.runTaskLater(this::nextTick, 1L);
            return;
        }

        this.deepVoid.updateTimeToOpen(time -> time - 1);
        this.scheduler.runTaskLater(this::nextTick, 1L);
    }

    private void clearWorldsAndOpenDeepVoid() {
        this.deepVoid.open();
        this.deepVoid.clearItems();

        List<ItemStack> items = new ItemStackList(config.stacking);

        for (World world : this.server.getWorlds()) {
            if (!this.config.worlds.contains(world.getName())) {
                continue;
            }

            this.removeEntities(world, Item.class, item -> items.add(item.getItemStack()));

            if (this.config.animals) {
                this.removeEntities(world, Animals.class, animals -> {});
            }

            if (this.config.monsters) {
                this.removeEntities(world, Monster.class, monster -> {});
            }
        }

        this.deepVoid.addVoidItems(items);
        this.view.update(new DeepVoidView.Model(this.deepVoid.getVoidItems()));
        this.scheduler.runTaskLater(() -> {
            this.deepVoid.close();

            for (Player player : this.view.closeAll()) {
                this.audienceProvider.player(player.getUniqueId()).sendMessage(this.config.voidClosedForce);
            }

        }, this.config.timeCloseVoid);
    }

    private <T extends Entity> void removeEntities(World world, Class<T> type, Consumer<T> collector) {
        for (T entity : world.getEntitiesByClass(type)) {
            if (config.ignoreNameTags && entity.getCustomName() != null) {
                continue;
            }

            collector.accept(entity);
            entity.remove();
        }
    }

}
