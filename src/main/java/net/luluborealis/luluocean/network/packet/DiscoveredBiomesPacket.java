package net.luluborealis.luluocean.network.packet;

import com.mojang.serialization.Codec;
import net.luluborealis.luluocean.server.level.BYGPlayerTrackedData;
import net.luluborealis.luluocean.util.BYGCodecUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;
import java.util.Set;

public record DiscoveredBiomesPacket(Map<String, Set<ResourceKey<Biome>>> discoveredBiomes) implements BYGS2CPacket {

    public static DiscoveredBiomesPacket read(FriendlyByteBuf buf) {
        try {
            return new DiscoveredBiomesPacket(buf.readWithCodec(NbtOps.INSTANCE, Codec.unboundedMap(Codec.STRING, BYGCodecUtil.BIOME_SET_CODEC)));
        } catch (Exception e) {
            throw new IllegalStateException("Discovered Biomes packet could not be read. This is really really bad...\n\n" + e.getMessage());
        }
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        try {
            buf.writeWithCodec(NbtOps.INSTANCE, Codec.unboundedMap(Codec.STRING, BYGCodecUtil.BIOME_SET_CODEC), this.discoveredBiomes);
        } catch (Exception e) {
            throw new IllegalStateException("Discovered Biomes packet could not be written. This is really really bad...\n\n" + e.getMessage());
        }
    }

    @Override
    public void handle(Level level) {
        Map<String, Set<ResourceKey<Biome>>> discoveredBiomesByNameSpace = ((BYGPlayerTrackedData.Access) Minecraft.getInstance().player).getPlayerTrackedData().discoveredBiomesByNameSpace();
        discoveredBiomesByNameSpace.clear();
        discoveredBiomesByNameSpace.putAll(this.discoveredBiomes);
    }
}