package net.luluborealis.luluocean.common.world.biome;

import net.luluborealis.luluocean.mixin.access.VanillaBiomeAccess;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static net.luluborealis.luluocean.mixin.access.OverworldBiomesAccess.byg_invokeBaseOceanGeneration;
import static net.luluborealis.luluocean.mixin.access.OverworldBiomesAccess.byg_invokeGlobalOverworldGeneration;

@SuppressWarnings("UnreachableCode")
public class BYGOverworldBiomes {

    public static void addDefaultOverworldGeneration(BiomeGenerationSettings.Builder generationSettings) {
        byg_invokeGlobalOverworldGeneration(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
    }

    public static Biome lushStacks(HolderGetter<PlacedFeature> placedFeatureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = byg_invokeBaseOceanGeneration(placedFeatureGetter, carverGetter);

        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_WARM);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_NORMAL);
        BiomeDefaultFeatures.addLushCavesVegetationFeatures(generationSettings);

        BYGDefaultBiomeFeatures.addlushStacksSpires(generationSettings);
        BiomeDefaultFeatures.warmOceanSpawns(spawnSettings, 10, 4);
        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(1.0F).downfall(0.8F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(4445678).waterFogColor(270131).grassColorOverride(10275901).foliageColorOverride(10275901).waterFogColor(2835532).fogColor(12638463).skyColor(VanillaBiomeAccess.byg_invokeCalculateSkyColor(0.8F)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    public static Biome deadSea(HolderGetter<PlacedFeature> placedFeatureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatureGetter, carverGetter);
        addDefaultOverworldGeneration(generationSettings);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_NORMAL);
        BiomeDefaultFeatures.addDripstone(generationSettings);

        BYGDefaultBiomeFeatures.addDeadSeaSpires(generationSettings);

        spawnSettings.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 95, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 100, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 100, 4, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 15, 1, 1));

        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(2.0F).downfall(0.0F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(9230578).waterFogColor(2835532).fogColor(12638463).skyColor(VanillaBiomeAccess.byg_invokeCalculateSkyColor(0.8F)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }
}