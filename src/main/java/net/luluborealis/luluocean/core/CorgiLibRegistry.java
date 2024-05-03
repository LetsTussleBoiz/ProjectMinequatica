package net.luluborealis.luluocean.core;

import com.mojang.serialization.Codec;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.structure.arch.BlendingFunction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class CorgiLibRegistry {

    public static final ResourceKey<Registry<Codec<? extends BlendingFunction>>> BLENDING_FUNCTION_RESOURCE_KEY =
            ResourceKey.createRegistryKey(LuluOcean.createLocation("blending_function"));

    public static final Supplier<IForgeRegistry<Codec<? extends BlendingFunction>>> BLENDING_FUNCTION =
            DeferredRegister.create(BLENDING_FUNCTION_RESOURCE_KEY, LuluOcean.MOD_ID).makeRegistry(null);

    public static void init() {
        BlendingFunction.register();
    }
}
