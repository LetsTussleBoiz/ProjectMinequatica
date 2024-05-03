package net.luluborealis.luluocean.common.world.biome;

import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.luluborealis.luluocean.common.world.feature.BYGPlacedFeatures;

public class BYGDefaultBiomeFeatures {
    public static void addDeadSeaSpires(BiomeGenerationSettings.Builder gen) {
        gen.addFeature(GenerationStep.Decoration.RAW_GENERATION, BYGPlacedFeatures.DEAD_SEA_SPIKES);
    }

    public static void addlushStacksSpires(BiomeGenerationSettings.Builder gen) {
        gen.addFeature(GenerationStep.Decoration.RAW_GENERATION, BYGPlacedFeatures.LUSH_STACKS_SPIKES);
    }
}