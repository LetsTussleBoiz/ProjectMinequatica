package net.luluborealis.luluocean.common.world.feature;

import com.google.common.collect.ImmutableList;
import net.luluborealis.luluocean.common.world.feature.features.overworld.LuluOceanOverworldFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.*;
import static net.luluborealis.luluocean.common.world.feature.placement.LuluOceanPlacedFeaturesUtil.createPlacedFeature;
import static net.luluborealis.luluocean.common.world.feature.placement.LuluOceanPlacedFeaturesUtil.oceanFloorSquaredWithCount;

public class LuluOceanPlacedFeatures {

    public static final NoiseThresholdCountPlacement LUSH_STACKS_SPIKES_NOISE = NoiseThresholdCountPlacement.of(0.545, 1, 0);

    public static final ResourceKey<PlacedFeature> LUSH_STACKS_SPIKES = createPlacedFeature("lush_stacks_spikes", LuluOceanOverworldFeatures.LUSH_STACKS_SPIKES, () -> new ImmutableList.Builder<PlacementModifier>().addAll(oceanFloorSquaredWithCount(1)).add(LUSH_STACKS_SPIKES_NOISE).build());
    public static final ResourceKey<PlacedFeature> DEAD_SEA_SPIKES = createPlacedFeature("dead_sea_spikes", LuluOceanOverworldFeatures.DEAD_SEA_SPIKES, () -> new ImmutableList.Builder<PlacementModifier>().addAll(oceanFloorSquaredWithCount(1)).add(LUSH_STACKS_SPIKES_NOISE).build());

    public static void loadClass() {
    }
}