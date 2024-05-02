package net.luluborealis.luluocean.common.world.preset;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.biome.BYGBiomeTags;
import net.luluborealis.luluocean.common.world.biome.BYGDebugBiomeSource;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Map;

public class BYGWorldPresets {

    public static final Map<ResourceKey<WorldPreset>, WorldPresetFactory> WORLD_PRESET_FACTORIES = new Reference2ObjectOpenHashMap<>();


    public static final ResourceKey<WorldPreset> DEBUG_BIOMES = register("debug_biomes", (context) -> {
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureSet> structureSets = context.lookup(Registries.STRUCTURE_SET);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);


        Holder<NoiseGeneratorSettings> overworldNoiseGenSettings = noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
        Holder<DimensionType> overworldDimensionType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.OVERWORLD);
        LevelStem overworldStem = new LevelStem(overworldDimensionType, new NoiseBasedChunkGenerator(new BYGDebugBiomeSource(biomes, BYGBiomeTags.IS_OVERWORLD), overworldNoiseGenSettings));

        Map<ResourceKey<LevelStem>, LevelStem> stemMap = Map.of(
                LevelStem.OVERWORLD, overworldStem
        );



        return new WorldPreset(stemMap);
    });


    private static ResourceKey<WorldPreset> register(String id, WorldPresetFactory factory) {
        ResourceKey<WorldPreset> worldPresetResourceKey = ResourceKey.create(Registries.WORLD_PRESET, LuluOcean.createLocation(id));
        WORLD_PRESET_FACTORIES.put(worldPresetResourceKey, factory);

        return worldPresetResourceKey;
    }

    public static void loadClass() {}

    @FunctionalInterface
    public interface WorldPresetFactory {

        WorldPreset generate(BootstapContext<WorldPreset> worldPresetBootstapContext);
    }
}