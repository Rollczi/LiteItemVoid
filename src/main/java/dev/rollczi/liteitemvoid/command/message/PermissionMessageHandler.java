/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.message;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public class PermissionMessageHandler implements MissingPermissionsHandler<CommandSender> {

    private final PluginConfig pluginConfig;

    public PermissionMessageHandler(PluginConfig pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> chain) {
        String permissions = missingPermissions.asJoinedText();
        Component component = this.pluginConfig.invalidPermission
                .replaceText(builder -> builder
                        .matchLiteral("${permissions}")
                        .replacement(permissions));

        invocation.sender().sendMessage(component);
    }

}
