package net.luluborealis.luluocean.common.world.feature.features;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.luluborealis.luluocean.LuluOcean;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class BYGFeaturesUtil {

    public static final Map<ResourceKey<ConfiguredFeature<?, ?>>, ConfiguredFeatureFactory> CONFIGURED_FEATURES_FACTORIES = new Reference2ObjectOpenHashMap<>();



    public static <FC extends FeatureConfiguration, F extends Feature<FC>> ResourceKey<ConfiguredFeature<?, ?>> createConfiguredFeature(String id, Supplier<? extends F> feature, Function<BootstapContext<ConfiguredFeature<?, ?>>, ? extends FC> config) {
        ResourceLocation bygID = LuluOcean.createLocation(id);

        ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, bygID);

        CONFIGURED_FEATURES_FACTORIES.put(configuredFeatureResourceKey, configuredFeatureHolderGetter -> new ConfiguredFeature<>(feature.get(), config.apply(configuredFeatureHolderGetter)));

        return configuredFeatureResourceKey;
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> ResourceKey<ConfiguredFeature<?, ?>> createConfiguredFeature(String id, Supplier<? extends F> feature, Supplier<? extends FC> config) {
        ResourceLocation bygID = LuluOcean.createLocation(id);

        ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, bygID);

        CONFIGURED_FEATURES_FACTORIES.put(configuredFeatureResourceKey, configuredFeatureHolderGetter -> new ConfiguredFeature<>(feature.get(), config.get()));

        return configuredFeatureResourceKey;
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> createConfiguredFeature(F feature, Supplier<? extends FC> config) {
        return Holder.direct(new ConfiguredFeature<>(feature, config.get()));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> createConfiguredFeature(F feature, FC config) {
        return Holder.direct(new ConfiguredFeature<>(feature, config));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> createConfiguredFeature(Supplier<F> feature, Supplier<FC> config) {
        return Holder.direct(new ConfiguredFeature<>(feature.get(), config.get()));
    }


    @FunctionalInterface
    public interface ConfiguredFeatureFactory {
        ConfiguredFeature<?, ?> generate(BootstapContext<ConfiguredFeature<?, ?>> configuredFeatureHolderGetter);
    }
}