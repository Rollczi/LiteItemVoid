/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config.plugin;

import dev.rollczi.liteitemvoid.util.LegacyColorProcessor;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import panda.utilities.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Contextual
public class ItemConfig {

    @Exclude
    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

    @Exclude
    private static final TextComponent RESET = Component.text()
            .decoration(TextDecoration.ITALIC, false)
            .build();

    public Material type = Material.STONE;
    public String skull = StringUtils.EMPTY;
    public int durability = 0;
    public Component name = MINI_MESSAGE.deserialize("<green>text</green>");
    public List<Component> lore = Collections.singletonList(MINI_MESSAGE.deserialize("<green>lore</green>"));

    public ItemConfig() {}

    public ItemConfig(Material type, int durability, String name) {
        this.type = type;
        this.durability = durability;
        this.name = MINI_MESSAGE.deserialize(name);
    }

    public ItemConfig(Material type, int durability, String name, List<String> lore) {
        this.type = type;
        this.durability = durability;
        this.name = MINI_MESSAGE.deserialize(name);
        this.lore = lore.stream()
                .map(MINI_MESSAGE::deserialize)
                .collect(Collectors.toList());
    }

    public ItemConfig(Material type, String skull, int durability, Component name) {
        this.type = type;
        this.skull = skull;
        this.durability = durability;
        this.name = name;
    }

    public ItemConfig(Material type, String skull, int durability, Component name, List<Component> lore) {
        this.type = type;
        this.skull = skull;
        this.durability = durability;
        this.name = name;
        this.lore = lore;
    }

    public ItemStack toItem() {
        if (skull.isEmpty()) {
            return ItemBuilder.from(new ItemStack(type, 1, (short) durability))
                    .name(RESET.append(name))
                    .lore(lore.stream()
                            .map(RESET::append)
                            .collect(Collectors.toList()))
                    .build();
        }

        return ItemBuilder.skull()
                .texture(skull)
                .name(RESET.append(name))
                .lore(lore.stream()
                        .map(RESET::append)
                        .collect(Collectors.toList()))
                .build();
    }

    public static ItemConfig skull(String skull, Component name) {
        return new ItemConfig(Material.STONE, skull, 0, name);
    }

    public static ItemConfig skull(String skull, Component name, List<Component> lore) {
        return new ItemConfig(Material.STONE, skull, 0, name, lore);
    }

}
