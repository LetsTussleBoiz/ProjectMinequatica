package net.luluborealis.luluocean.util;

import net.luluborealis.luluocean.network.packet.BYGS2CPacket;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;

import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;

public interface ModPlatform {

    ModPlatform INSTANCE = Util.make(() -> {
        final var loader = ServiceLoader.load(ModPlatform.class);
        final var it = loader.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("No Mod Platform was found on the classpath!");
        } else {
            final ModPlatform factory = it.next();
            if (it.hasNext()) {
                throw new RuntimeException("More than one Mod Platform was found on the classpath!");
            }
            return factory;
        }
    });

    Path configPath();

    boolean isModLoaded(String isLoaded);

    <P extends BYGS2CPacket> void sendToClient(ServerPlayer player, P packet);

    String curseForgeURL();

    boolean isClientEnvironment();

    boolean hasLoadErrors();

    @FunctionalInterface
    interface TagsUpdatedEvent {
        void onTagsUpdated(RegistryAccess access);
    }

    default <P extends BYGS2CPacket> void sendToAllClients(List<ServerPlayer> players, P packet) {
        for (ServerPlayer player : players) {
            sendToClient(player, packet);
        }
    }
}
