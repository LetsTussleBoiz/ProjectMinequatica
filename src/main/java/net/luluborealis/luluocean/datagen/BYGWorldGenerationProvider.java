package net.luluborealis.luluocean.datagen;

import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.feature.features.BYGFeaturesUtil;
import net.luluborealis.luluocean.common.world.feature.placement.BYGPlacedFeaturesUtil;
import net.luluborealis.luluocean.common.world.structure.BYGStructureSets;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = LuluOcean.MOD_ID)
public class BYGWorldGenerationProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, pContext ->
                    BYGFeaturesUtil.CONFIGURED_FEATURES_FACTORIES.forEach((biomeResourceKey, factory) ->
                            pContext.register(biomeResourceKey, factory.generate(pContext))))
            .add(Registries.PLACED_FEATURE, pContext ->
                    BYGPlacedFeaturesUtil.PLACED_FEATURE_FACTORIES.forEach((resourceKey, factory) ->
                            pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.CONFIGURED_FEATURE)))))
            .add(Registries.STRUCTURE_SET, pContext ->
                    BYGStructureSets.STRUCTURE_SET_FACTORIES.forEach(((resourceKey, factory) ->
                            pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.STRUCTURE))))));
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {

    }
}