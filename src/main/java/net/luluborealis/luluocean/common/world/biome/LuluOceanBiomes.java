package net.luluborealis.luluocean.common.world.biome;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.structure.LuluOceanStructurePieceTypes;
import net.luluborealis.luluocean.common.world.structure.LuluOceanStructureSets;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static net.luluborealis.luluocean.common.world.biome.LuluOceanBiomeTags.HAS_OVERGROWN_STONE_ARCH;
import static net.luluborealis.luluocean.common.world.biome.LuluOceanBiomeTags.HAS_STONE_ARCH;
import static net.minecraft.tags.BiomeTags.*;
import static net.minecraft.tags.BiomeTags.IS_OVERWORLD;
import static net.minecraftforge.common.Tags.Biomes.*;

public class LuluOceanBiomes {
    public static final DeferredRegister<Biome> PROVIDER = DeferredRegister.create(Registries.BIOME, LuluOcean.MOD_ID);
    public static final Multimap<TagKey<Biome>, ResourceKey<Biome>> BIOMES_BY_TAG = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);
    public static final Map<ResourceKey<Biome>, LuluOceanBiomes.BiomeFactory> BIOME_FACTORIES = new Reference2ObjectOpenHashMap<>();

    /************My Biomes************/
    public static final ResourceKey<Biome> LUSH_STACKS = createBiome(
            "lush_stacks",
            LuluOceanOverworldBiomes::lushStacks,
            IS_DEEP_OCEAN,
            IS_OCEAN,
            IS_OVERWORLD,
            IS_OCEAN,
            IS_DEEP_OCEAN,
            HAS_OVERGROWN_STONE_ARCH);
    public static final ResourceKey<Biome> DEAD_SEA = createBiome(
            "dead_sea",
            LuluOceanOverworldBiomes::deadSea,
            IS_DEEP_OCEAN,
            IS_OCEAN,
            IS_WASTELAND,
            IS_DEAD,
            IS_OVERWORLD,
            HAS_STONE_ARCH);
    public static final ResourceKey<Biome> FROZEN_DEPTHS = createBiome(
            "frozen_depths",
            LuluOceanOverworldBiomes::frozenDepths,
            IS_DEEP_OCEAN,
            IS_OCEAN,
            IS_SNOWY,
            IS_OVERWORLD,
            HAS_STONE_ARCH);

    @SafeVarargs
    public static ResourceKey<Biome> createBiome(String id, LuluOceanBiomes.BiomeFactory biomeFactory, TagKey<Biome>... tags) {
        ResourceKey<Biome> biomeResourceKey = ResourceKey.create(Registries.BIOME, LuluOcean.createLocation(id));
        BIOME_FACTORIES.put(biomeResourceKey, biomeFactory);

//        for (TagKey<Biome> tag : tags) {
//            if (!tag.location().getNamespace().equals(LuluOcean.MOD_ID)) {
//                throw new IllegalArgumentException("Tag key must be from the LuluOcean namespace!");
//            }
//            BIOMES_BY_TAG.put(tag, biomeResourceKey);
//        }
        return biomeResourceKey;
    }

    @FunctionalInterface
    public interface BiomeFactory {
        Biome generate(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> worldCarverHolderGetter);
    }

    public static void loadClass() {
        LuluOceanStructurePieceTypes.bootStrap();
        LuluOceanStructureSets.bootStrap();
    }
}
