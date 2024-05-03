package net.luluborealis.luluocean.common.world.biome;

import net.luluborealis.luluocean.LuluOcean;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class BYGBiomeTags {

    public static final TagKey<Biome> HAS_OVERGROWN_STONE_ARCH = create("has_structure/overgrown_stone_arch");
    public static final TagKey<Biome> HAS_STONE_ARCH = create("has_structure/stone_arch");

    private static TagKey<Biome> create(String id) {
        return TagKey.create(Registries.BIOME, LuluOcean.createLocation(id));
    }

}