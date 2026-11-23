/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.deepvoid;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.liteitemvoid.config.ConfigurationManager;
import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import dev.rollczi.liteitemvoid.view.ViewRegistry;
import io.papermc.paper.plugin.configuration.PluginMeta;
import net.dzikoysk.cdn.CdnException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Blank;
import panda.std.Result;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "deepvoid", aliases = { "void", "otchlan" })
public class DeepVoidCommand {

    private final PluginConfig config;
    private final DeepVoid deepVoid;
    private final ViewRegistry viewRegistry;
    private final ConfigurationManager configurationManager;
    private final PluginMeta pluginMeta;
    private final MiniMessage miniMessage;
    private final Logger logger;

    public DeepVoidCommand(
            PluginConfig config,
            DeepVoid deepVoid,
            ViewRegistry viewRegistry,
            ConfigurationManager configurationManager,
            PluginMeta pluginMeta,
            MiniMessage miniMessage,
            Logger logger
    ) {
        this.config = config;
        this.deepVoid = deepVoid;
        this.viewRegistry = viewRegistry;
        this.configurationManager = configurationManager;
        this.pluginMeta = pluginMeta;
        this.miniMessage = miniMessage;
        this.logger = logger;
    }

    @Execute
    public void deepVoidGui(@Context Player player) {
        if (deepVoid.isDisabled()) {
            player.sendMessage(config.voidDisabledInfo);
            return;
        }

        if (deepVoid.isClosed()) {
            long milliseconds = deepVoid.getTimeToOpen() * 50;
            String time = config.timeFormat.format(new Date(milliseconds));

            player.sendMessage(config.voidClosedInfo.replaceText(builder -> builder.matchLiteral("${time}").replacement(time)));
            return;
        }

        viewRegistry.deepVoid.show(player);
    }

    @Execute(name = "help")
    @Permission("itemvoid.admin")
    public void help(@Context CommandSender sender) {
        for (Component component : config.help) {
            sender.sendMessage(component);
        }
    }

    @Execute(name = "on")
    @Permission("itemvoid.admin")
    public void enable(@Context CommandSender sender) {
        if (deepVoid.isEnabled()) {
            sender.sendMessage(config.voidAlreadyEnabled);
            return;
        }

        sender.sendMessage(config.voidEnabled);
        deepVoid.enable();
    }

    @Execute(name = "off")
    @Permission("itemvoid.admin")
    public void disable(@Context CommandSender sender) {
        if (deepVoid.isDisabled()) {
            sender.sendMessage(config.voidAlreadyDisabled);
            return;
        }

        sender.sendMessage(config.voidDisabled);
        deepVoid.disable();
    }

    @Execute(name = "reload")
    @Permission("itemvoid.admin")
    public void reload(@Context CommandSender sender) {
        Result<Blank, CdnException> result = configurationManager.loadConfigs();

        if (result.isOk()) {
            sender.sendMessage(config.voidReload);
            return;
        }

        CdnException error = result.getError();
        Component infoError = config.voidReloadError.replaceText(builder -> builder.matchLiteral("${stacktrace}").replacement(error.toString()));

        sender.sendMessage(infoError);
        this.logger.log(Level.SEVERE, "Could not reload configuration", error);
    }

    @Execute(name = "clear")
    @Permission("itemvoid.admin")
    public void clear(@Context CommandSender sender) {
        this.deepVoid.enable();
        this.deepVoid.updateTimeToOpen(time -> config.timeClear);
        sender.sendMessage(config.voidClear);
    }

    @Execute(name = "force-clear")
    @Permission("itemvoid.admin")
    public void forceClear(@Context CommandSender sender) {
        this.deepVoid.enable();
        this.deepVoid.updateTimeToOpen(time -> 0L);
        sender.sendMessage(config.voidClear);
    }

    @Execute(name = "version")
    @Permission("itemvoid.admin")
    public void version(@Context CommandSender sender) {
        sender.sendMessage(miniMessage.deserialize("<gradient:#4f1794:#db2364><bold>LiteItemVoid <gray>" + pluginMeta.getVersion()));
    }

    @Execute(name = "info")
    @Permission("itemvoid.admin")
    public void info(@Context CommandSender sender) {
        sender.sendMessage(Component.empty());
        sender.sendMessage(miniMessage.deserialize(" <dark_gray>[<#4f1794>#</#4f1794>]</dark_gray> <gradient:#4f1794:#db2364><bold>LiteItemVoid</bold> <gray>by Rollczi"));
        sender.sendMessage(miniMessage.deserialize(" <gradient:#4f1794:#db2364>See discord <gray><click:open_url:'https://discord.com/invite/6cUhkj6uZJ'>[link] (6cUhkj6uZJ)</click>"));
        sender.sendMessage(Component.empty());
    }

}
