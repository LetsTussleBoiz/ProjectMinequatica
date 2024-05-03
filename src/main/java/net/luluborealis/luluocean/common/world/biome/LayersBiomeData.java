package net.luluborealis.luluocean.common.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import corgitaco.corgilib.serialization.codec.CodecUtil;
import net.luluborealis.luluocean.util.BYGUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.biome.Biome;

import java.util.Collection;
import java.util.function.BiPredicate;

public record LayersBiomeData(SimpleWeightedRandomList<ResourceKey<Biome>> biomeWeights, int biomeSize) {
    public static final Codec<LayersBiomeData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
        SimpleWeightedRandomList.wrappedCodec(CodecUtil.BIOME_CODEC).fieldOf("biomeWeights").forGetter(layersBiomeData -> layersBiomeData.biomeWeights),
        Codec.INT.fieldOf("biomeSize").forGetter(layersBiomeData -> layersBiomeData.biomeSize)
    ).apply(builder, LayersBiomeData::new));

    public LayersBiomeData filter(BiPredicate<Collection<ResourceKey<Biome>>, ResourceKey<Biome>> filter) {
        return new LayersBiomeData(BYGUtil.combineWeightedRandomLists(filter, biomeWeights), biomeSize);
    }
}