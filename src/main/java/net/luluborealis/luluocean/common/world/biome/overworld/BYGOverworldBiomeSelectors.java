package net.luluborealis.luluocean.common.world.biome.overworld;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import corgitaco.corgilib.serialization.codec.CodecUtil;
import corgitaco.corgilib.serialization.codec.FromFileCodec;
import corgitaco.corgilib.serialization.codec.Wrapped;
import corgitaco.corgilib.serialization.jankson.JanksonUtil;
import net.luluborealis.luluocean.common.world.biome.BYGBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.luluborealis.luluocean.util.BYGUtil.convert2DArray;

public class BYGOverworldBiomeSelectors {
    public static final FromFileCodec<List<List<ResourceKey<Biome>>>> BIOME_LAYOUT_CODEC = FromFileCodec.create(CodecUtil.wrapCodecForCollectionSerializing(Codec.list(Codec.list(CodecUtil.BIOME_CODEC))).xmap(CodecUtil.WrapForSerialization::value, CodecUtil::wrap), "biome_layout");
    public static final Codec<List<List<ResourceKey<Biome>>>> OLD_BIOME_LAYOUT_CODEC = Codec.list(Codec.list(CodecUtil.BIOME_CODEC));
    public static final Map<String, Pair<Map<String, String>, Wrapped<List<List<ResourceKey<Biome>>>>>> BIOME_LAYOUTS = new HashMap<>();

    public static final String BIOME_LAYOUT = """
        
        [ ARID-ICY, DRY-ICY, NEUTRAL-ICY, WET-ICY, HUMID-ICY ],
        [ ARID-COLD, DRY-COLD, NEUTRAL-COLD, WET-COLD, HUMID-COLD ],
        [ ARID-NEUTRAL, DRY-NEUTRAL, NEUTRAL-NEUTRAL, WET-NEUTRAL, HUMID-NEUTRAL ],
        [ ARID-WARM, DRY-WARM, NEUTRAL-WARM, WET-WARM, HUMID-WARM ],
        [ ARID-HOT, DRY-HOT, NEUTRAL-HOT, WET-HOT, HUMID-HOT ]
        
        """;

    public static final String OCEAN_BIOMES_LAYOUT =
        """
            [ DEEP-ICY, DEEP-COLD, DEEP-NEUTRAL, DEEP-WARM, DEEP-HOT ]
            [ SHALLOW-ICY, SHALLOW-COLD, SHALLOW-NEUTRAL, SHALLOW-WARM, SHALLOW-HOT ],
            
            """;

    public static final String REQUIRES_VALID_KEYS =
        """
            All keys passed in must be valid in the biome registry!
            "minecraft:the_void" is invalid as it represents a value of "NULL(nothing)" internally.
            """;

    public static final String SHATTERED_BIOMES_LAYOUT = "Appearing on shattered terrain here is the \"shattered_biomes\" layout:\n" + BIOME_LAYOUT +
        """
            All keys passed in must be valid in the biome registry!
            In slots containing "minecraft:the_void", biomes at the equivalent temperature/humidity index in "middle_biomes" will be used instead.
            """;

    private static String invalidKeysOkay(String fallback) {
        return String.format("""
            All keys passed in must be valid in the biome registry!
            In slots containing "minecraft:the_void", biomes at the equivalent temperature/humidity index in "%s" will be used instead.
            """, fallback);
    }

    public static final String OCEANS_BIOMES_LAYOUT_COMMENT = "Appearing on terrain below sea level, here is the \"ocean_biomes\" layout:\n" + OCEAN_BIOMES_LAYOUT + REQUIRES_VALID_KEYS;

    protected static final Wrapped<List<List<ResourceKey<Biome>>>> OCEANS_VANILLA = create("oceans/oceans_vanilla", OCEANS_BIOMES_LAYOUT_COMMENT, new ResourceKey[][]{
        {Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.WARM_OCEAN},
        {Biomes.FROZEN_OCEAN, Biomes.COLD_OCEAN, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN}
    });

    public static final Wrapped<List<List<ResourceKey<Biome>>>> OCEANS = create("oceans/oceans_1", OCEANS_BIOMES_LAYOUT_COMMENT, new ResourceKey[][]{
        {Biomes.DEEP_FROZEN_OCEAN, Biomes.COLD_OCEAN, Biomes.OCEAN, BYGBiomes.LUSH_STACKS, BYGBiomes.DEAD_SEA},
        {Biomes.FROZEN_OCEAN, Biomes.COLD_OCEAN, Biomes.OCEAN, BYGBiomes.LUSH_STACKS, BYGBiomes.DEAD_SEA}
    });

    protected static Wrapped<List<List<ResourceKey<Biome>>>> create(String id, String header, ResourceKey<Biome>[][] biomeKeys) {
        return create(id, biomeKeys, ImmutableMap.of("", JanksonUtil.HEADER_OPEN + "\n" + header + "*/"));
    }

    protected static Wrapped<List<List<ResourceKey<Biome>>>> create(String id, ResourceKey<Biome>[][] biomeKeys) {
        return create(id, biomeKeys, new HashMap<>());
    }

    protected static Wrapped<List<List<ResourceKey<Biome>>>> create(String id, ResourceKey<Biome>[][] biomeKeys, Map<String, String> comments) {
        Wrapped<List<List<ResourceKey<Biome>>>> result = new Wrapped<>(Optional.of(id), convert2DArray(biomeKeys));
        BIOME_LAYOUTS.put(id, Pair.of(comments, result));
        return result;
    }
}