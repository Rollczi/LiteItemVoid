/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.message;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import dev.rollczi.liteitemvoid.NotificationService;
import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public class InvalidUseMessageHandler implements InvalidUsageHandler<CommandSender> {

    private final PluginConfig pluginConfig;
    private final NotificationService notificationService;

    public InvalidUseMessageHandler(PluginConfig pluginConfig, NotificationService notificationService) {
        this.pluginConfig = pluginConfig;
        this.notificationService = notificationService;
    }

    @Override
    public void handle(CommandSender commandSender, LiteInvocation invocation, Schematic schematic) {
        Component component = pluginConfig.invalidUsage
                .replaceText(builder -> builder
                        .match("\\$\\{usage}")
                        .replacement(schematic.first()));

        this.notificationService.sendMessage(commandSender, component);
    }
}
