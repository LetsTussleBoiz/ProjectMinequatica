package net.luluborealis.luluocean.common.world.biome;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.luluborealis.luluocean.BYGConstants;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.placement.BYGPlacementModifierType;
import net.luluborealis.luluocean.common.world.structure.BYGStructureSets;
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

import static net.minecraft.tags.BiomeTags.*;
import static net.minecraftforge.common.Tags.Biomes.IS_DEAD;
import static net.minecraftforge.common.Tags.Biomes.IS_WASTELAND;

public class BYGBiomes {

    public static final DeferredRegister<Biome> PROVIDER = DeferredRegister.create(Registries.BIOME, LuluOcean.MOD_ID);
    public static final Multimap<TagKey<Biome>, ResourceKey<Biome>> BIOMES_BY_TAG = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);

    public static final Map<ResourceKey<Biome>, BiomeFactory> BIOME_FACTORIES = new Reference2ObjectOpenHashMap<>();

    /************Overworld Biomes************/
    public static final ResourceKey<Biome> LUSH_STACKS = createBiome("lush_stacks", BYGOverworldBiomes::lushStacks, IS_DEEP_OCEAN, IS_OCEAN, IS_OVERWORLD, IS_OCEAN, IS_DEEP_OCEAN);
    public static final ResourceKey<Biome> DEAD_SEA = createBiome("dead_sea", BYGOverworldBiomes::deadSea, IS_DEEP_OCEAN, IS_OCEAN, IS_WASTELAND, IS_DEAD, IS_OVERWORLD);

    @SafeVarargs
    public static <B extends Biome> ResourceKey<Biome> createBiome(String id, BiomeFactory biomeFactory, TagKey<Biome>... tags) {
        ResourceKey<Biome> biomeResourceKey = ResourceKey.create(Registries.BIOME, LuluOcean.createLocation(id));
        BIOME_FACTORIES.put(biomeResourceKey, biomeFactory);

        if (BYGConstants.BIOMES) {
            for (TagKey<Biome> tag : tags) {
                if (!tag.location().getNamespace().equals(LuluOcean.MOD_ID)) {
                    throw new IllegalArgumentException("Tag key must be from the BYG namespace!");
                }
                BIOMES_BY_TAG.put(tag, biomeResourceKey);
            }
        }



        return biomeResourceKey;
    }

    public static void loadClass() {
        BYGStructureSets.bootStrap();
        BYGPlacementModifierType.bootStrap();
    }


    @FunctionalInterface
    public interface BiomeFactory {

        Biome generate(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> worldCarverHolderGetter);
    }
}