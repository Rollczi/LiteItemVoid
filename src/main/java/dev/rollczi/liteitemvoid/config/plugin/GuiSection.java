/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config.plugin;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.entity.Exclude;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import dev.rollczi.liteitemvoid.command.paper.LegacyColorProcessor;

import java.util.function.Supplier;

import static dev.rollczi.liteitemvoid.config.plugin.ItemConfig.skull;

@Contextual
public class GuiSection {

    @Exclude
    private final MiniMessage miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

    @Description( { "#PL: Tytuł gui", "#EN: Title of gui" } )
    public Component title = of("<dark_gray> » ItemVoid</dark_gray>");

    @Description( {
            "#PL: INFO Jeśli nie chcesz główki to pozostaw skull na \"\"",
            "#EN: INFO If you don't want to use head leave \"\""
    } )

    @Description( { "#PL: Przycisk poprzedniej strony", "#EN: Previous page button" } )
    public ItemConfig previous = skull(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=",
            of("&a&lPrevious Page")
    );

    @Description( { "#PL: Przycisk następnej strony", "#EN: Next page button" } )
    public ItemConfig next = skull(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19",
            of("&a&lNext Page")
    );

    @Description( { "#PL: Slot w którym ma znajdować się przycisk poprzedniej strony", "#EN: Slot of previous page button" } )
    public int previousSlot = 3;

    @Description( { "#PL: Slot w którym ma znajdować się przycisk następnej strony", "#EN: Slot of next page button" } )
    public int nextSlot = 7;

    @Description( { "#PL: Wypełnienie gui", "#EN: Fill gui" } )
    public ItemConfig fill = tryOr(
            () -> new ItemConfig(Material.valueOf("GRAY_STAINED_GLASS_PANE"), 0, "&7 &7"),
            () -> new ItemConfig(Material.valueOf("STAINED_GLASS_PANE"), 7, "&7 &7")
    );

    @Description( { "#PL: Ozdobny item wyświetlany w rogach gui", "#EN: Decorative item in gui corners" } )
    public ItemConfig cornerFill = tryOr(
            () -> new ItemConfig(Material.valueOf("BLACK_STAINED_GLASS_PANE"), 0, "&7 - &7"),
            () -> new ItemConfig(Material.valueOf("STAINED_GLASS_PANE"), 15, "&7 - &7")
    );

    private Component of(String text) {
        return miniMessage.deserialize(text);
    }

    private <T> T tryOr(Supplier<T> supplier, Supplier<T> defaultValue) {
        try {
            return supplier.get();
        } catch (Throwable throwable) {
            return defaultValue.get();
        }
    }

}
