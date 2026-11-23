/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.message;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public class InvalidUseMessageHandler implements InvalidUsageHandler<CommandSender> {

    private final PluginConfig pluginConfig;

    public InvalidUseMessageHandler(PluginConfig pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        String usage = result.getSchematic().first();
        Component component = this.pluginConfig.invalidUsage
                .replaceText(builder -> builder
                        .matchLiteral("${usage}")
                        .replacement(usage));

        invocation.sender().sendMessage(component);
    }

}
