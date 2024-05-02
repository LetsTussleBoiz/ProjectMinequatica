package net.luluborealis.luluocean.common.world.feature.features.overworld;

import com.google.common.collect.ImmutableList;
import net.luluborealis.luluocean.common.world.feature.BYGFeatures;
import net.luluborealis.luluocean.common.world.feature.config.PointyRockConfig;
import net.luluborealis.luluocean.common.world.feature.placement.BYGPlacedFeaturesUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.function.Supplier;

import static net.luluborealis.luluocean.common.world.feature.features.BYGFeaturesUtil.createConfiguredFeature;

public class BYGOverworldFeatures {
    private static final Supplier<WeightedStateProvider> LUSH_SPIKE_PROVIDER = () -> new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.STONE.defaultBlockState(), 6)
            .add(Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 3)
            .add(Blocks.ANDESITE.defaultBlockState(), 1)
    );

    private static final Supplier<WeightedStateProvider> SPIKE_PROVIDER = () -> new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(Blocks.STONE.defaultBlockState(), 6)
            .add(Blocks.ANDESITE.defaultBlockState(), 1)
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> LUSH_STACKS_SPIKE = createConfiguredFeature("lush_stacks_spike",
            BYGFeatures.POINTY_ROCK,
            (configuredFeatureBootstapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new PointyRockConfig.Builder()
                        .setBlock(LUSH_SPIKE_PROVIDER.get())
                        .setSeed(65)
                        .setPostFeatures(HolderSet.direct(BYGPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(CaveFeatures.MOSS_PATCH))))
                        .build();
            }
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> LUSH_STACKS_SPIKE_TALL = createConfiguredFeature("lush_stacks_tall_spike",
            BYGFeatures.POINTY_ROCK,
            (configuredFeatureBootstapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new PointyRockConfig.Builder()
                        .setBlock(LUSH_SPIKE_PROVIDER.get())
                        .setSeed(65)
                        .setPostFeatures(HolderSet.direct(BYGPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(CaveFeatures.MOSS_PATCH))))
                        .build();
            }
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_SEA_SPIKE = createConfiguredFeature("dead_sea_spike",
            BYGFeatures.POINTY_ROCK,
            () -> new PointyRockConfig.Builder()
                    .setBlock(SPIKE_PROVIDER.get())
                    .setSeed(65)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_SEA_SPIKE_TALL = createConfiguredFeature("dead_sea_tall_spike",
            BYGFeatures.TALL_POINTED_ROCK,
            () -> new PointyRockConfig.Builder()
                    .setBlock(SPIKE_PROVIDER.get())
                    .setSeed(85)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> LUSH_STACKS_SPIKES = createConfiguredFeature("lush_stacks_spikes",
            () -> Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstapContext -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);

                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(BYGPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(LUSH_STACKS_SPIKE)), 0.75F)),
                        BYGPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(LUSH_STACKS_SPIKE_TALL)));
            }
            ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_SEA_SPIKES = createConfiguredFeature("dead_sea_spikes",
            () -> Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);

                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(BYGPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(DEAD_SEA_SPIKE)), 0.75F)),
                        BYGPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(DEAD_SEA_SPIKE_TALL)));
            }
    );

    public static void loadClass() {
    }
}