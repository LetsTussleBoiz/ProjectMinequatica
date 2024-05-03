package net.luluborealis.luluocean.util;

import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import java.nio.file.Path;
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

    String curseForgeURL();

    boolean isClientEnvironment();

    boolean hasLoadErrors();

    @FunctionalInterface
    interface TagsUpdatedEvent {
        void onTagsUpdated(RegistryAccess access);
    }
}
