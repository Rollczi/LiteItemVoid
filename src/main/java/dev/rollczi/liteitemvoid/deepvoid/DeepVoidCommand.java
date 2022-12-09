/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.deepvoid;

import dev.rollczi.liteitemvoid.config.ConfigurationManager;
import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import dev.rollczi.liteitemvoid.view.ViewRegistry;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.PluginDescriptionFile;
import net.dzikoysk.cdn.CdnException;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import panda.std.Blank;
import panda.std.Result;

import java.util.Date;
import java.util.regex.Pattern;

@Section(route = "deepvoid", aliases = { "void", "otchlan" })
public class DeepVoidCommand {

    private static final Pattern TIME_PATTERN = Pattern.compile("\\$\\{time}");

    private final MiniMessage miniMessage;
    private final PluginConfig config;
    private final DeepVoid deepVoid;
    private final ViewRegistry viewRegistry;

    public DeepVoidCommand(MiniMessage miniMessage, PluginConfig config, DeepVoid deepVoid, ViewRegistry viewRegistry) {
        this.miniMessage = miniMessage;
        this.config = config;
        this.deepVoid = deepVoid;
        this.viewRegistry = viewRegistry;
    }

    @Execute
    public void deepVoidGui(Audience audience, Player player) {
        if (deepVoid.isDisabled()) {
            audience.sendMessage(config.voidDisabledInfo);
            return;
        }

        if (deepVoid.isClosed()) {
            long milliseconds = deepVoid.getTimeToOpen() * 50;
            Date date = new Date(milliseconds);

            audience.sendMessage(config.voidClosedInfo.replaceText(builder -> builder.match(TIME_PATTERN).replacement(config.timeFormat.format(date))));
            return;
        }

        viewRegistry.VOID.show(player);
    }

    @Execute(route = "help")
    @Permission("itemvoid.admin")
    public void help(Audience audience) {
        for (Component component : config.help) {
            audience.sendMessage(component);
        }
    }

    @Execute(route = "on")
    public void enable(Audience audience) {
        if (deepVoid.isEnabled()) {
            audience.sendMessage(config.voidAlreadyEnabled);
            return;
        }

        audience.sendMessage(config.voidEnabled);
        deepVoid.enable();
    }

    @Execute(route = "off")
    @Permission("itemvoid.admin")
    public void disable(Audience audience) {
        if (deepVoid.isDisabled()) {
            audience.sendMessage(config.voidAlreadyDisabled);
            return;
        }

        audience.sendMessage(config.voidDisabled);
        deepVoid.disable();
    }

    @Execute(route = "reload")
    @Permission("itemvoid.admin")
    public void reload(ConfigurationManager configurationManager, Audience audience) {
        Result<Blank, CdnException> result = configurationManager.loadConfigs();

        if (result.isOk()) {
            audience.sendMessage(config.voidReload);
            return;
        }

        CdnException error = result.getError();
        Component infoError = config.voidReloadError.replaceText(builder -> builder.replacement("${stacktrace}").replacement(error.toString()));

        audience.sendMessage(infoError);
        error.printStackTrace();
    }

    @Permission("itemvoid.admin")
    @Execute(route = "clear")
    public void clear(Audience audience) {
        this.deepVoid.enable();
        this.deepVoid.updateTimeToOpen(time -> config.timeClear);
        audience.sendMessage(config.voidClear);
    }

    @Permission("itemvoid.admin")
    @Execute(route = "force-clear")
    public void forceClear(Audience audience) {
        this.deepVoid.enable();
        this.deepVoid.updateTimeToOpen(time -> 0L);
        audience.sendMessage(config.voidClear);
    }

    @Permission("itemvoid.admin")
    @Execute(route = "version")
    public void version(Audience audience, PluginDescriptionFile description) {
        audience.sendMessage(miniMessage.deserialize("<gradient:#4f1794:#db2364><bold>LiteItemVoid <gray>" + description.getVersion()));
    }

    @Permission("itemvoid.admin")
    @Execute(route = "info")
    public void info(Audience audience) {
        audience.sendMessage(Component.empty());
        audience.sendMessage(miniMessage.deserialize(" <dark_gray>[<#4f1794>#</#4f1794>]</dark_gray> <gradient:#4f1794:#db2364><bold>LiteItemVoid</bold> <gray>by Rollczi"));
        audience.sendMessage(miniMessage.deserialize(" <gradient:#4f1794:#db2364>See discord <gray><click:open_url:'https://discord.com/invite/DFpmMJQsa4'>[link] (DFpmMJQsa4)</click>"));
        audience.sendMessage(Component.empty());
    }


}