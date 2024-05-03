package net.luluborealis.luluocean.network.packet;

import net.luluborealis.luluocean.LuluOcean;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public interface BYGS2CPacket {

    Map<String, Handler<?>> S2C_PACKETS = Util.make(new HashMap<>(), map -> {
        LuluOcean.logInfo("Initializing BYG network...");
        map.put("discovered_biomes", new Handler<>(DiscoveredBiomesPacket.class, DiscoveredBiomesPacket::write, DiscoveredBiomesPacket::read, DiscoveredBiomesPacket::handle));
        map.put("construct_byg_player_tracked_data", new Handler<>(ConstructBYGPlayerTrackedDataPacket.class, ConstructBYGPlayerTrackedDataPacket::write, ConstructBYGPlayerTrackedDataPacket::read, ConstructBYGPlayerTrackedDataPacket::handle));
        map.put("level_biome_tracker", new Handler<>(LevelBiomeTrackerPacket.class, LevelBiomeTrackerPacket::write, LevelBiomeTrackerPacket::read, LevelBiomeTrackerPacket::handle));
        LuluOcean.logInfo("Initialized BYG network!");
    });


    void write(FriendlyByteBuf buf);

    void handle(Level level);

    record Handler<T extends BYGS2CPacket>(Class<T> clazz, BiConsumer<T, FriendlyByteBuf> write,
                                           Function<FriendlyByteBuf, T> read,
                                           BiConsumer<T, Level> handle) {
    }
}