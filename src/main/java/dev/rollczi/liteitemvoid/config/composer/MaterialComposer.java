/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config.composer;

import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import net.dzikoysk.cdn.serdes.SimpleSerializer;
import org.bukkit.Material;
import panda.std.Result;

public class MaterialComposer implements Composer<Material>, SimpleDeserializer<Material>, SimpleSerializer<Material> {

    @Override
    public Result<Material, Exception> deserialize(String source) {
        Material material = Material.matchMaterial(source);

        if (material == null) {
            return Result.ok(Material.STONE);
        }

        return Result.ok(material);
    }

    @Override
    public Result<String, Exception> serialize(Material entity) {
        return Result.ok(entity.name());
    }

}
