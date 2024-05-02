package net.luluborealis.luluocean.common.world.biome;

import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.luluborealis.luluocean.common.world.feature.BYGPlacedFeatures;

public class BYGDefaultBiomeFeatures {
    public static void addDeadSeaSpires(BiomeGenerationSettings.Builder gen) {
        gen.addFeature(GenerationStep.Decoration.RAW_GENERATION, BYGPlacedFeatures.DEAD_SEA_SPIKES);
    }

//    public static void addOvergrownVines(BiomeGenerationSettings.Builder gen) {
//        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BYGPlacedFeatures.VINES_1);
//        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BYGPlacedFeatures.VINES_2);
//    }

    public static void addVanillaSunFlowers(BiomeGenerationSettings.Builder gen) {
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUNFLOWER);
    }

//    public static void addLilyPads(BiomeGenerationSettings.Builder gen) {
//        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BYGPlacedFeatures.TINY_LILY_PAD);
//        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, BYGPlacedFeatures.LILY_PAD);
//    }

    public static void addlushStacksSpires(BiomeGenerationSettings.Builder gen) {
        gen.addFeature(GenerationStep.Decoration.RAW_GENERATION, BYGPlacedFeatures.LUSH_STACKS_SPIKES);
    }

    public static void addDefaultEndFeatures(BiomeGenerationSettings.Builder gen) {
        gen.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EndPlacements.CHORUS_PLANT);
    }
}