package net.luluborealis.luluocean.network.packet;

import net.luluborealis.luluocean.common.world.LevelBiomeTracker;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

public record LevelBiomeTrackerPacket(LevelBiomeTracker biomeTracker) implements BYGS2CPacket {

    public static LevelBiomeTrackerPacket read(FriendlyByteBuf buf) {
        try {
            return new LevelBiomeTrackerPacket(buf.readJsonWithCodec(LevelBiomeTracker.CODEC));
        } catch (Exception e) {
            throw new IllegalStateException("LevelBiomeTracker packet could not be read. This is really really bad...\n\n" + e.getMessage());
        }
    }


    @Override
    public void write(FriendlyByteBuf buf) {
        try {
            buf.writeJsonWithCodec(LevelBiomeTracker.CODEC, this.biomeTracker);
        } catch (Exception e) {
            throw new IllegalStateException("LevelBiomeTracker packet could not be written. This is really really bad...\n\n" + e.getMessage());
        }
    }

    @Override
    public void handle(Level level) {
        LevelBiomeTracker.client_instance = this    .biomeTracker;
    }
}
