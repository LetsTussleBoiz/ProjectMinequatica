package net.luluborealis.luluocean.datagen;

import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.biome.LuluOceanBiomes;
import net.luluborealis.luluocean.common.world.feature.features.LuluOceanFeaturesUtil;
import net.luluborealis.luluocean.common.world.feature.placement.LuluOceanPlacedFeaturesUtil;
import net.luluborealis.luluocean.common.world.structure.LuluOceanStructureSets;
import net.luluborealis.luluocean.common.world.structure.LuluOceanStructures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = LuluOcean.MOD_ID)
public class LuluOceanDataGen {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, pContext -> LuluOceanFeaturesUtil.CONFIGURED_FEATURES_FACTORIES.forEach((biomeResourceKey, factory) -> pContext.register(biomeResourceKey, factory.generate(pContext))))
            .add(Registries.PLACED_FEATURE, pContext -> LuluOceanPlacedFeaturesUtil.PLACED_FEATURE_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.CONFIGURED_FEATURE)))))
            .add(Registries.BIOME, pContext -> LuluOceanBiomes.BIOME_FACTORIES.forEach((resourceKey, factory) ->
                    pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.PLACED_FEATURE),
                            pContext.lookup(Registries.CONFIGURED_CARVER))))).add(Registries.STRUCTURE, pContext ->
                    LuluOceanStructures.STRUCTURE_FACTORIES.forEach((resourceKey, factory) ->
                            pContext.register(resourceKey, factory.generate(pContext)))).add(Registries.STRUCTURE_SET, pContext ->
                    LuluOceanStructureSets.STRUCTURE_SET_FACTORIES.forEach((resourceKey, factory) ->
                            pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.STRUCTURE)))));

    @SubscribeEvent
    static void onDatagen(final GatherDataEvent event) {
        final var gen = event.getGenerator();
        gen.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(gen.getPackOutput(), event.getLookupProvider(), BUILDER, Set.of(LuluOcean.MOD_ID)));
    }

}
