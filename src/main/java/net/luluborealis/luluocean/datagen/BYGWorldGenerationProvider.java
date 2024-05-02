package net.luluborealis.luluocean.datagen;

import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.biome.BYGBiomes;
import net.luluborealis.luluocean.common.world.feature.features.BYGFeaturesUtil;
import net.luluborealis.luluocean.common.world.feature.placement.BYGPlacedFeaturesUtil;
import net.luluborealis.luluocean.common.world.preset.BYGWorldPresets;
import net.luluborealis.luluocean.common.world.structure.BYGStructureSets;
import net.luluborealis.luluocean.common.world.structure.BYGStructures;
import net.luluborealis.luluocean.common.world.structure.village.pool.StructureTemplatePoolFactory;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = LuluOcean.MOD_ID)
public class BYGWorldGenerationProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, pContext -> BYGFeaturesUtil.CONFIGURED_FEATURES_FACTORIES.forEach((biomeResourceKey, factory) -> pContext.register(biomeResourceKey, factory.generate(pContext))))
            .add(Registries.PLACED_FEATURE, pContext -> BYGPlacedFeaturesUtil.PLACED_FEATURE_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.CONFIGURED_FEATURE)))))
            .add(Registries.BIOME, pContext -> BYGBiomes.BIOME_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.PLACED_FEATURE), pContext.lookup(Registries.CONFIGURED_CARVER))))).add(Registries.STRUCTURE, pContext -> BYGStructures.STRUCTURE_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext)))).add(Registries.STRUCTURE_SET, pContext -> BYGStructureSets.STRUCTURE_SET_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext.lookup(Registries.STRUCTURE))))).add(Registries.TEMPLATE_POOL, pContext -> StructureTemplatePoolFactory.STRUCTURE_TEMPLATE_POOL_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext)))).add(Registries.WORLD_PRESET, pContext -> BYGWorldPresets.WORLD_PRESET_FACTORIES.forEach((resourceKey, factory) -> pContext.register(resourceKey, factory.generate(pContext))));

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {

    }
}