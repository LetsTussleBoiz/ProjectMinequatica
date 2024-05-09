package net.luluborealis.luluocean.common.world.feature.features.overworld;

import com.google.common.collect.ImmutableList;
import net.luluborealis.luluocean.common.world.feature.LuluOceanFeatures;
import net.luluborealis.luluocean.common.world.feature.config.PointyRockConfig;
import net.luluborealis.luluocean.common.world.feature.placement.LuluOceanPlacedFeaturesUtil;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.function.Supplier;

import static net.luluborealis.luluocean.common.world.feature.features.LuluOceanFeaturesUtil.createConfiguredFeature;

public class LuluOceanOverworldFeatures {
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
            LuluOceanFeatures.POINTY_ROCK,
            (configuredFeatureBootstapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new PointyRockConfig.Builder()
                        .setBlock(LUSH_SPIKE_PROVIDER.get())
                        .setSeed(65)
                        .setPostFeatures(HolderSet.direct(LuluOceanPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(CaveFeatures.MOSS_PATCH))))
                        .build();
            }
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> LUSH_STACKS_SPIKE_TALL = createConfiguredFeature("lush_stacks_tall_spike",
            LuluOceanFeatures.TALL_POINTED_ROCK,
            (configuredFeatureBootstapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);
                return new PointyRockConfig.Builder()
                        .setBlock(LUSH_SPIKE_PROVIDER.get())
                        .setSeed(65)
                        .setPostFeatures(HolderSet.direct(LuluOceanPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(CaveFeatures.MOSS_PATCH))))
                        .build();
            }
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_SEA_SPIKE = createConfiguredFeature("dead_sea_spike",
            LuluOceanFeatures.POINTY_ROCK,
            () -> new PointyRockConfig.Builder()
                    .setBlock(SPIKE_PROVIDER.get())
                    .setSeed(65)
                    .build()
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_SEA_SPIKE_TALL = createConfiguredFeature("dead_sea_tall_spike",
            LuluOceanFeatures.TALL_POINTED_ROCK,
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
                        new WeightedPlacedFeature(LuluOceanPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(LUSH_STACKS_SPIKE)), 0.75F)),
                        LuluOceanPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(LUSH_STACKS_SPIKE_TALL)));
            }
            ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_SEA_SPIKES = createConfiguredFeature("dead_sea_spikes",
            () -> Feature.RANDOM_SELECTOR,
            (configuredFeatureBootstapContext) -> {
                HolderGetter<ConfiguredFeature<?, ?>> lookup = configuredFeatureBootstapContext.lookup(Registries.CONFIGURED_FEATURE);

                return new RandomFeatureConfiguration(ImmutableList.of(
                        new WeightedPlacedFeature(LuluOceanPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(DEAD_SEA_SPIKE)), 0.75F)),
                        LuluOceanPlacedFeaturesUtil.createPlacedFeatureDirect(lookup.getOrThrow(DEAD_SEA_SPIKE_TALL)));
            }
    );

    public static void loadClass() {
    }
}