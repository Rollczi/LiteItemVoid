/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config.plugin;

import dev.rollczi.liteitemvoid.util.LegacyColorProcessor;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.SkullMeta;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
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
    public String skull = "";
    public int durability = 0;
    public Component name = MINI_MESSAGE.deserialize("<green>name</green>");
    public List<Component> lore = Collections.emptyList();

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
        List<Component> itemLore = new ArrayList<>(this.lore.size());

        for (Component line : this.lore) {
            itemLore.add(RESET.append(line));
        }

        ItemStack itemStack = ItemStack.of(this.skull.isEmpty() ? this.type : Material.PLAYER_HEAD);

        itemStack.editMeta(meta -> {
            meta.displayName(RESET.append(this.name));
            meta.lore(itemLore);

            if (this.durability > 0 && meta instanceof Damageable damageable) {
                damageable.setDamage(this.durability);
            }

            if (!this.skull.isEmpty() && meta instanceof SkullMeta skullMeta) {
                PlayerProfile profile = Bukkit.createProfile(UUID.nameUUIDFromBytes(this.skull.getBytes(StandardCharsets.UTF_8)));
                profile.setProperty(new ProfileProperty("textures", this.skull));
                skullMeta.setPlayerProfile(profile);
            }
        });

        return itemStack;
    }

    public static ItemConfig skull(String skull, Component name) {
        return new ItemConfig(Material.STONE, skull, 0, name);
    }

    public static ItemConfig skull(String skull, Component name, List<Component> lore) {
        return new ItemConfig(Material.STONE, skull, 0, name, lore);
    }

}
