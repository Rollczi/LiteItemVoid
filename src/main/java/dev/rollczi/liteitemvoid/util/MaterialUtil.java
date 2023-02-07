package dev.rollczi.liteitemvoid.util;

import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

public final class MaterialUtil {

    private static final Set<String> AIR_MATERIALS = new HashSet<>();

    static {
        AIR_MATERIALS.add("AIR");
        AIR_MATERIALS.add("CAVE_AIR");
        AIR_MATERIALS.add("VOID_AIR");
    }

    private MaterialUtil() {
    }

    public static boolean isAir(Material material) {
        try {
            return material.isAir();
        }
        catch (NoSuchMethodError ignored) {
            return AIR_MATERIALS.contains(material.name());
        }
    }

}
