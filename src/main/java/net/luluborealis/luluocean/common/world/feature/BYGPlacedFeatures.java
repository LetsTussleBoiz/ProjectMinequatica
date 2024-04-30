package net.luluborealis.luluocean.common.world.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import static net.minecraft.data.worldgen.placement.VegetationPlacements.treePlacement;
import static net.minecraft.data.worldgen.placement.VegetationPlacements.worldSurfaceSquaredWithCount;

public class BYGPlacedFeatures {

    public static final ResourceKey<PlacedFeature> GLOBAL_RAW_GENERATION = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.RAW_GENERATION), BYGGlobalFeatures.GLOBAL_RAW_GENERATION);
    public static final ResourceKey<PlacedFeature> GLOBAL_LAKES = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.LAKES), BYGGlobalFeatures.GLOBAL_LAKES);
    public static final ResourceKey<PlacedFeature> GLOBAL_LOCAL_MODIFICATIONS = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.LOCAL_MODIFICATIONS), BYGGlobalFeatures.GLOBAL_LOCAL_MODIFICATIONS);
    public static final ResourceKey<PlacedFeature> GLOBAL_UNDERGROUND_STRUCTURES = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.UNDERGROUND_STRUCTURES), BYGGlobalFeatures.GLOBAL_UNDERGROUND_STRUCTURES);
    public static final ResourceKey<PlacedFeature> GLOBAL_SURFACE_STRUCTURES = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.SURFACE_STRUCTURES), BYGGlobalFeatures.GLOBAL_SURFACE_STRUCTURES);
    public static final ResourceKey<PlacedFeature> GLOBAL_STRONGHOLDS = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.STRONGHOLDS), BYGGlobalFeatures.GLOBAL_STRONGHOLDS);
    public static final ResourceKey<PlacedFeature> GLOBAL_UNDERGROUND_ORES = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.UNDERGROUND_ORES), BYGGlobalFeatures.GLOBAL_UNDERGROUND_ORES);
    public static final ResourceKey<PlacedFeature> GLOBAL_UNDERGROUND_DECORATION = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.UNDERGROUND_DECORATION), BYGGlobalFeatures.GLOBAL_UNDERGROUND_DECORATION);
    public static final ResourceKey<PlacedFeature> GLOBAL_FLUID_SPRINGS = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.FLUID_SPRINGS), BYGGlobalFeatures.GLOBAL_FLUID_SPRINGS);
    public static final ResourceKey<PlacedFeature> GLOBAL_VEGETAL_DECORATION = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.VEGETAL_DECORATION), BYGGlobalFeatures.GLOBAL_VEGETAL_DECORATION);
    public static final ResourceKey<PlacedFeature> GLOBAL_TOP_LAYER_MODIFICATION = createPlacedFeature(globalGenStagePath(GenerationStep.Decoration.TOP_LAYER_MODIFICATION), BYGGlobalFeatures.GLOBAL_TOP_LAYER_MODIFICATION);


    public static final NoiseThresholdCountPlacement CRAG_NOISE = NoiseThresholdCountPlacement.of(0, 1, 0);

    public static final NoiseThresholdCountPlacement DENSE_NOISE = NoiseThresholdCountPlacement.of(-0.545, 1, 2);
    public static final NoiseThresholdCountPlacement LUSH_STACKS_SPIKES_NOISE = NoiseThresholdCountPlacement.of(0.545, 1, 0);

    //public static final ResourceKey<PlacedFeature> TINY_LILY_PAD = createPlacedFeature("tiny_lily_pad", BYGOverworldVegetationFeatures.TINY_LILY_PAD, () -> worldSurfaceSquaredWithCount(4));
    //public static final ResourceKey<PlacedFeature> LILY_PAD = createPlacedFeature("lily_pad", VegetationFeatures.PATCH_WATERLILY, () -> CountPlacement.of(15), InSquarePlacement::spread, () -> HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES), BiomeFilter::biome);

    public static final ResourceKey<PlacedFeature> LUSH_STACKS_SPIKES = createPlacedFeature("lush_stacks_spikes", BYGOverworldFeatures.LUSH_STACKS_SPIKES, () -> new ImmutableList.Builder<PlacementModifier>().addAll(oceanFloorSquaredWithCount(1)).add(LUSH_STACKS_SPIKES_NOISE).build());
    public static final ResourceKey<PlacedFeature> DEAD_SEA_SPIKES = createPlacedFeature("dead_sea_spikes", BYGOverworldFeatures.DEAD_SEA_SPIKES, () -> new ImmutableList.Builder<PlacementModifier>().addAll(oceanFloorSquaredWithCount(1)).add(LUSH_STACKS_SPIKES_NOISE).build());

    //public static final ResourceKey<PlacedFeature> ARCH_FEATURE = createPlacedFeature("arch_feature", BYGOverworldFeatures.ARCH_FEATURE, InSquarePlacement::spread, () -> RarityFilter.onAverageOnceEvery(12), () -> PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter::biome);
    //public static final ResourceKey<PlacedFeature> VINES_1 = createPlacedFeature("vines", VegetationFeatures.VINES, () -> CountPlacement.of(256), InSquarePlacement::spread, () -> HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(100)), BiomeFilter::biome);
    //public static final ResourceKey<PlacedFeature> VINES_2 = createPlacedFeature("vines_2", VegetationFeatures.VINES, () -> CountPlacement.of(256), InSquarePlacement::spread, () -> HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(100)), BiomeFilter::biome);

    public static void loadClass() {
    }
}