package net.luluborealis.luluocean.common.world.feature.features;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.luluborealis.luluocean.LuluOcean;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

//import static potionstudios.byg.mixin.access.VegetationFeaturesAccess.byg_invokeGrassPatch;

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

//    public static <FC extends FeatureConfiguration, F extends Feature<FC>> ResourceKey<ConfiguredFeature<?, ?>> createFlowerConfiguredFeature(String id, Supplier<Block> flowerBlock) {
//        return createConfiguredFeature(id, () -> Feature.FLOWER, (configuredFeatureBootstapContext) -> byg_invokeGrassPatch(SimpleStateProvider.simple(flowerBlock.get().defaultBlockState()), 15));
//    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> ResourceKey<ConfiguredFeature<?, ?>> createPatchConfiguredFeatureWithBlock(String id, Supplier<? extends Block> block, int tries) {
        return createPatchConfiguredFeatureWithState(id, () -> block.get().defaultBlockState(), tries);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createPatchConfiguredFeatureWithState(String id, Supplier<BlockState> state, int tries) {
        return createConfiguredFeature(id, () -> Feature.RANDOM_PATCH, (configuredFeatureBootstapContext) -> FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(state.get())), List.of(), tries));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> createPatchConfiguredFeatureWithState(Block block, int tries) {
        return Holder.direct(new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block)), List.of(), tries)));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createSimpleBlockConfiguredFeatureWithBlock(String id, Supplier<Block> block) {
        return createSimpleBlockConfiguredFeatureWithState(id, () -> block.get().defaultBlockState());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createSimpleBlockConfiguredFeatureWithState(String id, Supplier<BlockState> state) {
        return createConfiguredFeature(id, () -> Feature.SIMPLE_BLOCK, (configuredFeatureBootstapContext) -> new SimpleBlockConfiguration(BlockStateProvider.simple(state.get())));
    }


    public static BlockPredicateFilter createSolidDownAndAirAllAroundFilter(BlockPredicate... predicates) {
        return BlockPredicateFilter.forPredicate(BlockPredicate.allOf(new ImmutableList.Builder<BlockPredicate>().add(BlockPredicate.solid(BlockPos.ZERO.relative(Direction.DOWN)),
                        BlockPredicate.not(BlockPredicate.solid(BlockPos.ZERO.relative(Direction.WEST))),
                        BlockPredicate.not(BlockPredicate.solid(BlockPos.ZERO.relative(Direction.EAST))),
                        BlockPredicate.not(BlockPredicate.solid(BlockPos.ZERO.relative(Direction.NORTH))),
                        BlockPredicate.not(BlockPredicate.solid(BlockPos.ZERO.relative(Direction.SOUTH))),
                        BlockPredicate.not(BlockPredicate.solid(BlockPos.ZERO.relative(Direction.UP))))
                .add(BlockPredicate.matchesBlocks(List.of(Blocks.AIR, Blocks.CAVE_AIR)))
                .add(predicates).build()));
    }


    public static String globalGenStagePath(GenerationStep.Decoration stage) {
        return "global/" + stage.toString().toLowerCase(Locale.ROOT);
    }

    @FunctionalInterface
    public interface ConfiguredFeatureFactory {
        ConfiguredFeature<?, ?> generate(BootstapContext<ConfiguredFeature<?, ?>> configuredFeatureHolderGetter);
    }
}