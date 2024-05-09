package net.luluborealis.luluocean.common.world.biome;

import net.luluborealis.luluocean.common.world.feature.LuluOceanPlacedFeatures;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class LuluOceanDefaultBiomeFeatures {
    public static void addlushStacksSpires(BiomeGenerationSettings.Builder gen) {
        gen.addFeature(GenerationStep.Decoration.RAW_GENERATION, LuluOceanPlacedFeatures.LUSH_STACKS_SPIKES);
    }

    public static void addDeadSeaSpires(BiomeGenerationSettings.Builder gen) {
        gen.addFeature(GenerationStep.Decoration.RAW_GENERATION, LuluOceanPlacedFeatures.DEAD_SEA_SPIKES);
    }
}
