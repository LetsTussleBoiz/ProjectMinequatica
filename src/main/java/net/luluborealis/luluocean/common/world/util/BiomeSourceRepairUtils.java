package net.luluborealis.luluocean.common.world.util;

import com.mojang.serialization.Codec;
import net.luluborealis.luluocean.common.world.biome.BYGDebugBiomeSource;
import net.luluborealis.luluocean.common.world.biome.LazyLoadSeed;
import net.luluborealis.luluocean.mixin.access.BiomeSourceAccess;
import net.luluborealis.luluocean.mixin.access.ChunkGeneratorAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.Optional;
import java.util.function.Supplier;

public class BiomeSourceRepairUtils {
    public static void repairBiomeSources(Registry<Biome> biomeRegistry, MinecraftServer server) {

        Registry<LevelStem> levelStems = server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);

        levelStems.forEach(dimension -> {
            BiomeSource biomeSource = dimension.generator().getBiomeSource();
            if (biomeSource instanceof LazyLoadSeed lazyLoadSeed) {
                lazyLoadSeed.lazyLoad(server.getWorldData().worldGenOptions().seed(), biomeRegistry);
            }
        });
    }

    private static boolean repair(LevelStem dimension, ResourceLocation targetBiomeSourceID, Supplier<BiomeSource> replacement) {
        ChunkGenerator generator = dimension.generator();
        Codec<? extends BiomeSource> codec = ((BiomeSourceAccess) generator.getBiomeSource()).byg_invokeCodec();
        if (!BuiltInRegistries.BIOME_SOURCE.getKey(codec).equals(targetBiomeSourceID) && !(generator.getBiomeSource() instanceof BYGDebugBiomeSource)) {
            BiomeSource replacementBiomeSource = replacement.get();
            ((ChunkGeneratorAccess) generator).byg_setBiomeSource(replacementBiomeSource);
            return true;
        }
        return false;
    }
}
