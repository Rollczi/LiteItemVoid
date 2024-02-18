/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config.plugin;

import com.google.common.collect.ImmutableMap;
import dev.rollczi.liteitemvoid.util.LegacyColorProcessor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.ItemStack;
import dev.rollczi.liteitemvoid.config.AbstractConfigWithResource;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.entity.Exclude;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoidViewSettings;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PluginConfig extends AbstractConfigWithResource implements DeepVoidViewSettings {

    @Exclude
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    @Exclude
    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

    public PluginConfig(File folder, String child) {
        super(folder, child);
    }

    @Description( {
            "#PL: Sekcja konfiguracji gui. W tej sekcji możesz zmienić wygląd gui",
            "#EN: Section of gui config. You can change gui look in this section"
    })
    public GuiSection gui = new GuiSection();

    @Description("# 1 tick -> 50 miliseconds")
    @Description("# 20 ticks -> 1 second")
    @Description("# 600 ticks -> 30 seconds")
    @Description("# 1200 ticks -> 1 minute")

    @Description({
            "#PL: Czas w tickach, po którym zostanie oczyszczony świat",
            "#EN: Time in ticks, after which the world will be cleared"
    })
    public long timeClear = 1200L;

    @Description({
            "#PL: Odstęp w tikach między kolejnymi oczyszczeniami świata",
            "#EN: Delay in ticks between world cleanings"
    })
    public long timeClearDelay = 2400L;

    @Description({
            "#PL: Czas w tickach, po którym itemVoid zostanie zamknięty",
            "#EN: Time in ticks, after which itemVoid will be closed"
    })
    public long timeCloseVoid = 2000L;

    @Description({
            "#PL: Czy usuwać wrogie potwory ze świata",
            "#EN: Should plugin remove aggressive mobs from world"
    })
    public boolean monsters = false;

    @Description({
            "#PL: Czy usuwać przyjazne zwierzęta ze świata",
            "#EN: Should plugin remove friendly mobs from world"
    })
    public boolean animals = false;

    @Description({
            "#PL: Lista światów, które zostaną wyczyszczone",
            "#EN: List of worlds which are going to be cleared"
    })
    public List<String> worlds = Arrays.asList("world", "world_nether", "world_the_end");

    @Description({
            "#PL: Czy ignorować byty z custom nametag'iem?",
            "#EN: Should ignore entities with custom nametag?"
    })
    public boolean ignoreNameTags = true;

    @Description({
            "#PL: Czy stakowanie itemów ma być włączone? (może mieć negatywny wpływ na wydajność jeśli true)",
            "#EN: Should stacking be enabled? (may have a negative effect on performance if true)"
    })
    public boolean stacking = true;

    @Description("# ---------- #")
    @Description("#  Messages  #")
    @Description("# ---------- #")

    public SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss:SS");

    public List<Component> help = Arrays.asList(
        of("&7"),
        of("<dark_gray>[<#4f1794>#</#4f1794>]</dark_gray> <gradient:#4f1794:#db2364><bold>LiteItemVoid</gradient> &7By Rollczi"),
        of("&7"),
        of("<#8a26bf> /void <gray> - <#db2364>Opens the Abyss inventory."),
        of("<#8a26bf> /void help <gray> - <#db2364>Displays helpful commands."),
        of("<#8a26bf> /void on <gray> - <#db2364>Turns on the Void."),
        of("<#8a26bf> /void off <gray> - <#db2364>Turns off the Void."),
        of("<#8a26bf> /void clear <gray> - <#db2364>Clears the Void."),
        of("<#8a26bf> /void force-clear <gray> - <#db2364>Clears the Void without delay."),
        of("<#8a26bf> /void reload <gray> - <#db2364>Reloads the plugin."),
        of("<#8a26bf> /void info <gray> - <#db2364>Displays the plugin info."),
        of("<#8a26bf> /void version <gray> - <#db2364>Displays the plugin version."),
        of("&7")
    );

    public Component invalidUsage = of("&8 » &cCorrect command use&8 » &7${usage}");
    public Component invalidPermission = of("&8 » &cYou have no permissions to use this command! &7(${permissions})");

    public Component voidEnabled = of("&8 » &7ItemVoid has been &aenabled!");
    public Component voidDisabled = of("&8 » &7ItemVoid has been &cdisabled!");
    public Component voidAlreadyEnabled = of("&8 » &cItemVoid is already enabled!");
    public Component voidAlreadyDisabled = of("&8 » &cItemVoid is already disabled!");
    public Component voidReload = of("&8 » &aPlugin has been successful reloaded!");
    public Component voidReloadError = of("&8 » &cError with reloading the plugin! <hover:show_text:'<red>${stacktrace}'>&7(stacktrace)</gray><hover></red>");
    public Component voidClear = of("&8 » &aCleaning the worlds!");
    public Component voidClosedInfo = of("&8 » &cThe ItemVoid will open in &e${time}&c!");
    public Component voidClosedForce = of("&8 » &cThe ItemVoid has been closed!");
    public Component voidDisabledInfo = of("&8 » &cItemVoid was disabled by admin!");

    public Map<Long, Component> messagesInTime = new ImmutableMap.Builder<Long, Component>()
            .put(1200L, of("&8 » &7ItemVoid will collect items for <#d95b87>60s!"))
            .put(600L, of("&8 » &7ItemVoid will collect items for <#d95b87>30s!"))
            .put(200L, of("&8 » &7ItemVoid will collect items for <#d95b87>10s!"))
            .put(100L, of("&8 » &7ItemVoid will collect items for <#d95b87>5s!"))
            .put(80L, of("&8 » &7ItemVoid will collect items for <#d95b87>4s!"))
            .put(60L, of("&8 » &7ItemVoid will collect items for <#d95b87>3s!"))
            .put(40L, of("&8 » &7ItemVoid will collect items for <#d95b87>2s!"))
            .put(20L, of("&8 » &7ItemVoid will collect items for <#d95b87>1s!"))
            .put(0L, of("&8 » &7ItemVoid has been opened <hover:show_text:'<gray>Click!</gray>'><click:run_command:'/void'><#d95b87>(/void)</#d95b87></click>"))
            .build();

    private Component of(String text) {
        return MINI_MESSAGE.deserialize(text);
    }

    @Override
    public String getTitle() {
        return LEGACY_SERIALIZER.serialize(this.gui.title);
    }

    @Override
    public ItemStack corners() {
        return this.gui.cornerFill.toItem();
    }

    @Override
    public ItemStack fill() {
        return this.gui.fill.toItem();
    }

    @Override
    public ItemStack nextPage() {
        return this.gui.next.toItem();
    }

    @Override
    public ItemStack previousPage() {
        return this.gui.previous.toItem();
    }

}
