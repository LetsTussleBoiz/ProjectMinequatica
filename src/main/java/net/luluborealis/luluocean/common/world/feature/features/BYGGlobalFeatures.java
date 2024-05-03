package net.luluborealis.luluocean.common.world.feature.features;

import net.luluborealis.luluocean.common.world.feature.BYGFeatures;
import net.luluborealis.luluocean.common.world.feature.GlobalBiomeFeature;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import static net.luluborealis.luluocean.common.world.feature.features.BYGFeaturesUtil.createConfiguredFeature;
import static net.luluborealis.luluocean.common.world.feature.features.BYGFeaturesUtil.globalGenStagePath;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.*;

public class BYGGlobalFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOBAL_RAW_GENERATION = createConfiguredFeature(globalGenStagePath(RAW_GENERATION),
            BYGFeatures.GLOBAL, () -> new GlobalBiomeFeature.Config(HolderSet.direct())
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOBAL_LAKES = createConfiguredFeature(globalGenStagePath(LAKES), BYGFeatures.GLOBAL,
            () -> new GlobalBiomeFeature.Config(HolderSet.direct())
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOBAL_LOCAL_MODIFICATIONS = createConfiguredFeature(globalGenStagePath(LOCAL_MODIFICATIONS),
            BYGFeatures.GLOBAL, () -> new GlobalBiomeFeature.Config(HolderSet.direct())
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOBAL_UNDERGROUND_STRUCTURES = createConfiguredFeature(globalGenStagePath(UNDERGROUND_STRUCTURES),
            BYGFeatures.GLOBAL, () -> new GlobalBiomeFeature.Config(HolderSet.direct())
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOBAL_SURFACE_STRUCTURES = createConfiguredFeature(globalGenStagePath(SURFACE_STRUCTURES),
            BYGFeatures.GLOBAL,
            () -> new GlobalBiomeFeature.Config(HolderSet.direct())
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOBAL_STRONGHOLDS = createConfiguredFeature(globalGenStagePath(STRONGHOLDS), BYGFeatures.GLOBAL,
            () -> new GlobalBiomeFeature.Config(HolderSet.direct())
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOBAL_UNDERGROUND_DECORATION = createConfiguredFeature(globalGenStagePath(UNDERGROUND_DECORATION),
            BYGFeatures.GLOBAL, () -> new GlobalBiomeFeature.Config(HolderSet.direct())
    );
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOBAL_FLUID_SPRINGS = createConfiguredFeature(globalGenStagePath(FLUID_SPRINGS),
            BYGFeatures.GLOBAL, () -> new GlobalBiomeFeature.Config(HolderSet.direct())
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOBAL_TOP_LAYER_MODIFICATION = createConfiguredFeature(globalGenStagePath(TOP_LAYER_MODIFICATION),
            BYGFeatures.GLOBAL, () -> new GlobalBiomeFeature.Config(HolderSet.direct())
    );

    public static void loadClass() {
    }
}