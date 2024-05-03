package net.luluborealis.luluocean.common.world.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.mixin.access.BlockStateProviderAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BYGStateProviders {
    private static final DeferredRegister<BlockStateProviderType<?>> PROVIDER = DeferredRegister.create(Registries.BLOCK_STATE_PROVIDER_TYPE, LuluOcean.MOD_ID);

    public static final RegistryObject<BlockStateProviderType<BetweenNoiseThresholdProvider>> BETWEEN_NOISE_THRESHOLD_PROVIDER = register("between_noise_threshold_provider", BetweenNoiseThresholdProvider.CODEC);

    private static <P extends BlockStateProvider> RegistryObject<BlockStateProviderType<P>> register(String id, Codec<P> codec) {
        return PROVIDER.register(id, () -> BlockStateProviderAccess.byg_create(codec));
    }

    public static void loadClass() {}
}
