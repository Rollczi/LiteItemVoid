/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.message;

import dev.rollczi.liteitemvoid.command.paper.LegacyColorProcessor;
import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.valid.messages.InvalidUseMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class InvalidUseMessageHandler implements InvalidUseMessage {

    private final MiniMessage miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

    private final PluginConfig pluginConfig;

    public InvalidUseMessageHandler(PluginConfig pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    @Override
    public String message(LiteInvocation invocation, String useScheme) {
        Component component = pluginConfig.invalidUsage
                .replaceText(builder -> builder
                        .match("\\$\\{usage}")
                        .replacement(useScheme));

        return miniMessage.serialize(component);
    }

}
