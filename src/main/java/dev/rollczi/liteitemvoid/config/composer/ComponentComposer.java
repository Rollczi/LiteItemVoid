/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config.composer;

import dev.rollczi.liteitemvoid.util.LegacyColorProcessor;
import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import net.dzikoysk.cdn.serdes.SimpleSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import panda.std.Result;

public class ComponentComposer implements Composer<Component>, SimpleDeserializer<Component>, SimpleSerializer<Component> {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();

    @Override
    public Result<Component, Exception> deserialize(String source) {
        return Result.ok(MINI_MESSAGE.deserialize(source));
    }

    @Override
    public Result<String, Exception> serialize(Component entity) {
        return Result.ok(MINI_MESSAGE.serialize(entity));
    }

}
