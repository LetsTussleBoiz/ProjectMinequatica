package net.luluborealis.luluocean.networking;

import net.luluborealis.luluocean.networking.packet.WilderSensorHiccupPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LuluOceanNetworking {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("luluocean", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerSensorHiccupPacket() {
        int index = 0;
        INSTANCE.registerMessage(index++, WilderSensorHiccupPacket.class, WilderSensorHiccupPacket::toBytes,
                WilderSensorHiccupPacket::new, WilderSensorHiccupPacket::handle);
    }

    public static void sendToAll(@NotNull BlockEntity blockEntity, @NotNull Vec3 pos) {
        WilderSensorHiccupPacket sensorHiccupPacket = new WilderSensorHiccupPacket(
                pos.x(),
                pos.y(),
                pos.z()
        );
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() ->
                Objects.requireNonNull(blockEntity.getLevel()).getChunkAt(blockEntity.getBlockPos())), sensorHiccupPacket);
    }
}
