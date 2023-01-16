/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config.composer;

import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import net.dzikoysk.cdn.serdes.SimpleSerializer;
import org.bukkit.Material;
import panda.std.Option;
import panda.std.Result;

public class MaterialComposer implements Composer<Material>, SimpleDeserializer<Material>, SimpleSerializer<Material> {

    @Override
    public Result<Material, Exception> deserialize(String source) {
        return Option.attempt(IllegalArgumentException.class, () -> Material.getMaterial(source))
                .orElse(Material.getMaterial("STAINED_GLASS_PANE"))
                .orElse(Material.STONE)
                .toResult(IllegalArgumentException::new);
    }

    @Override
    public Result<String, Exception> serialize(Material entity) {
        return Result.ok(entity.name());
    }

}
