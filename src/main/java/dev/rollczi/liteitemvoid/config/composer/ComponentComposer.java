/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config.composer;

import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import net.dzikoysk.cdn.serdes.SimpleSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import panda.std.Result;
import dev.rollczi.liteitemvoid.command.paper.LegacyColorProcessor;

public class ComponentComposer implements Composer<Component>, SimpleDeserializer<Component>, SimpleSerializer<Component> {

    private final MiniMessage miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

    @Override
    public Result<Component, Exception> deserialize(String source) {
        return Result.ok(miniMessage.deserialize(source));
    }

    @Override
    public Result<String, Exception> serialize(Component entity) {
        return Result.ok(miniMessage.serialize(entity));
    }

}
