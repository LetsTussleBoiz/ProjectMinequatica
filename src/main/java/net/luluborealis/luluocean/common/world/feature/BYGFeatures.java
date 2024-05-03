package net.luluborealis.luluocean.common.world.feature;

import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.feature.config.PointyRockConfig;
import net.luluborealis.luluocean.common.world.feature.gen.overworld.PointyRockFeature;
import net.luluborealis.luluocean.common.world.feature.gen.overworld.TallPointedRocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BYGFeatures {
    private static final DeferredRegister<Feature<?>> PROVIDER = DeferredRegister.create(ForgeRegistries.FEATURES, LuluOcean.MOD_ID);

    public static final RegistryObject<Feature<GlobalBiomeFeature.Config>> GLOBAL = createFeature("global", GlobalBiomeFeature::new);

    //public static final RegistryObject<Feature<SimpleBlockProviderConfig>> ARCH = createFeature("arch", () -> new ArchFeature(SimpleBlockProviderConfig.CODEC.stable()));
    //public static final RegistryObject<Feature<SimpleBlockProviderConfig>> SPIKE = createFeature("spike", () -> new SpikeFeature(SimpleBlockProviderConfig.CODEC.stable()));

    /********************************************************************Features*************************************************************************/
    //DeadSea
    public static final RegistryObject<Feature<PointyRockConfig>> POINTY_ROCK = createFeature("pointed_rock", () -> new PointyRockFeature(PointyRockConfig.CODEC.stable()));
    public static final RegistryObject<Feature<PointyRockConfig>> TALL_POINTED_ROCK = createFeature("tall_pointed_rock", () -> new TallPointedRocks(PointyRockConfig.CODEC.stable()));

    public static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> createFeature(String id, Supplier<? extends F> feature) {
        return PROVIDER.register(id, feature);
    }

    public static void loadClass() {
    }
}