package net.luluborealis.luluocean.common.world.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class ModLoaderBiomeTags {

    public static class Forge {
        public static final TagKey<Biome> IS_HOT = create("is_hot");
        public static final TagKey<Biome> IS_HOT_OVERWORLD = create("is_hot/overworld");
        public static final TagKey<Biome> IS_HOT_NETHER = create("is_hot/nether");
        public static final TagKey<Biome> IS_HOT_END = create("is_hot/end");

        public static final TagKey<Biome> IS_COLD = create("is_cold");
        public static final TagKey<Biome> IS_COLD_OVERWORLD = create("is_cold/overworld");
        public static final TagKey<Biome> IS_COLD_NETHER = create("is_cold/nether");
        public static final TagKey<Biome> IS_COLD_END = create("is_cold/end");

        public static final TagKey<Biome> IS_SPARSE = create("is_sparse");
        public static final TagKey<Biome> IS_SPARSE_OVERWORLD = create("is_sparse/overworld");
        public static final TagKey<Biome> IS_SPARSE_NETHER = create("is_sparse/nether");
        public static final TagKey<Biome> IS_SPARSE_END = create("is_sparse/end");
        public static final TagKey<Biome> IS_DENSE = create("is_dense");
        public static final TagKey<Biome> IS_DENSE_OVERWORLD = create("is_dense/overworld");
        public static final TagKey<Biome> IS_DENSE_NETHER = create("is_dense/nether");
        public static final TagKey<Biome> IS_DENSE_END = create("is_dense/end");

        public static final TagKey<Biome> IS_WET = create("is_wet");
        public static final TagKey<Biome> IS_WET_OVERWORLD = create("is_wet/overworld");
        public static final TagKey<Biome> IS_WET_NETHER = create("is_wet/nether");
        public static final TagKey<Biome> IS_WET_END = create("is_wet/end");
        public static final TagKey<Biome> IS_DRY = create("is_dry");
        public static final TagKey<Biome> IS_DRY_OVERWORLD = create("is_dry/overworld");
        public static final TagKey<Biome> IS_DRY_NETHER = create("is_dry/nether");
        public static final TagKey<Biome> IS_DRY_END = create("is_dry/end");

        public static final TagKey<Biome> IS_SAVANNA = create("is_savanna");
        public static final TagKey<Biome> IS_CONIFEROUS = create("is_coniferous");

        public static final TagKey<Biome> IS_SPOOKY = create("is_spooky");
        public static final TagKey<Biome> IS_DEAD = create("is_dead");
        public static final TagKey<Biome> IS_LUSH = create("is_lush");
        public static final TagKey<Biome> IS_MUSHROOM = create("is_mushroom");
        public static final TagKey<Biome> IS_MAGICAL = create("is_magical");
        public static final TagKey<Biome> IS_RARE = create("is_rare");
        public static final TagKey<Biome> IS_PLATEAU = create("is_plateau");
        public static final TagKey<Biome> IS_MODIFIED = create("is_modified");

        public static final TagKey<Biome> IS_WATER = create("is_water");

        public static final TagKey<Biome> IS_PLAINS = create("is_plains");
        public static final TagKey<Biome> IS_SWAMP = create("is_swamp");
        public static final TagKey<Biome> IS_SANDY = create("is_sandy");
        public static final TagKey<Biome> IS_DESERT = create("is_desert");
        public static final TagKey<Biome> IS_SNOWY = create("is_snowy");
        public static final TagKey<Biome> IS_WASTELAND = create("is_wasteland");
        public static final TagKey<Biome> IS_BEACH = create("is_beach");
        public static final TagKey<Biome> IS_VOID = create("is_void");
        public static final TagKey<Biome> IS_UNDERGROUND = create("is_underground");
        public static final TagKey<Biome> IS_CAVE = create("is_cave");

        public static final TagKey<Biome> IS_PEAK = create("is_peak");
        public static final TagKey<Biome> IS_SLOPE = create("is_slope");

        public static final TagKey<Biome> IS_OVERWORLD = create("is_overworld");
        public static final TagKey<Biome> IS_END = create("is_end");

        private static TagKey<Biome> create(String id) {
            return TagKey.create(Registries.BIOME, new ResourceLocation("forge", id));
        }
    }
}