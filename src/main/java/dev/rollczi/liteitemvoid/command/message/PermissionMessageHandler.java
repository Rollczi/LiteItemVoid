/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.message;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import dev.rollczi.litecommands.handle.PermissionHandler;
import dev.rollczi.liteitemvoid.NotificationService;
import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Joiner;

public class PermissionMessageHandler implements PermissionHandler<CommandSender> {

    private final NotificationService notificationService;
    private final PluginConfig pluginConfig;

    public PermissionMessageHandler(NotificationService notificationService, PluginConfig pluginConfig) {
        this.notificationService = notificationService;
        this.pluginConfig = pluginConfig;
    }

    @Override
    public void handle(CommandSender commandSender, LiteInvocation invocation, RequiredPermissions requiredPermissions) {
        Component component = pluginConfig.invalidPermission
                .replaceText(builder -> builder
                        .match("\\$\\{permissions}")
                        .replacement(Joiner.on(", ").join(requiredPermissions.getPermissions()).toString()));

        this.notificationService.sendMessage(commandSender, component);
    }
}
