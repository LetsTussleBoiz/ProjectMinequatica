package net.luluborealis.luluocean.common.world.feature;

import com.google.common.collect.ImmutableList;
import net.luluborealis.luluocean.common.world.feature.features.BYGGlobalFeatures;
import net.luluborealis.luluocean.common.world.feature.features.overworld.BYGOverworldFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.*;

import static net.luluborealis.luluocean.common.world.feature.features.BYGFeaturesUtil.globalGenStagePath;
import static net.luluborealis.luluocean.common.world.feature.placement.BYGPlacedFeaturesUtil.createPlacedFeature;
import static net.luluborealis.luluocean.common.world.feature.placement.BYGPlacedFeaturesUtil.oceanFloorSquaredWithCount;

public class BYGPlacedFeatures {

    public static final ResourceKey<PlacedFeature> GLOBAL_RAW_GENERATION = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.RAW_GENERATION), BYGGlobalFeatures.GLOBAL_RAW_GENERATION);
    public static final ResourceKey<PlacedFeature> GLOBAL_LAKES = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.LAKES), BYGGlobalFeatures.GLOBAL_LAKES);
    public static final ResourceKey<PlacedFeature> GLOBAL_LOCAL_MODIFICATIONS = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.LOCAL_MODIFICATIONS), BYGGlobalFeatures.GLOBAL_LOCAL_MODIFICATIONS);
    public static final ResourceKey<PlacedFeature> GLOBAL_UNDERGROUND_STRUCTURES = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.UNDERGROUND_STRUCTURES), BYGGlobalFeatures.GLOBAL_UNDERGROUND_STRUCTURES);
    public static final ResourceKey<PlacedFeature> GLOBAL_SURFACE_STRUCTURES = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.SURFACE_STRUCTURES), BYGGlobalFeatures.GLOBAL_SURFACE_STRUCTURES);
    public static final ResourceKey<PlacedFeature> GLOBAL_STRONGHOLDS = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.STRONGHOLDS), BYGGlobalFeatures.GLOBAL_STRONGHOLDS);
    public static final ResourceKey<PlacedFeature> GLOBAL_UNDERGROUND_DECORATION = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.UNDERGROUND_DECORATION), BYGGlobalFeatures.GLOBAL_UNDERGROUND_DECORATION);
    public static final ResourceKey<PlacedFeature> GLOBAL_FLUID_SPRINGS = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.FLUID_SPRINGS), BYGGlobalFeatures.GLOBAL_FLUID_SPRINGS);
    public static final ResourceKey<PlacedFeature> GLOBAL_TOP_LAYER_MODIFICATION = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.TOP_LAYER_MODIFICATION), BYGGlobalFeatures.GLOBAL_TOP_LAYER_MODIFICATION);


    public static final NoiseThresholdCountPlacement LUSH_STACKS_SPIKES_NOISE = NoiseThresholdCountPlacement.of(0.545, 1, 0);

    public static final ResourceKey<PlacedFeature> LUSH_STACKS_SPIKES = createPlacedFeature("lush_stacks_spikes", BYGOverworldFeatures.LUSH_STACKS_SPIKES, () -> new ImmutableList.Builder<PlacementModifier>().addAll(oceanFloorSquaredWithCount(1)).add(LUSH_STACKS_SPIKES_NOISE).build());
    public static final ResourceKey<PlacedFeature> DEAD_SEA_SPIKES = createPlacedFeature("dead_sea_spikes", BYGOverworldFeatures.DEAD_SEA_SPIKES, () -> new ImmutableList.Builder<PlacementModifier>().addAll(oceanFloorSquaredWithCount(1)).add(LUSH_STACKS_SPIKES_NOISE).build());

    public static void loadClass() {
    }
}