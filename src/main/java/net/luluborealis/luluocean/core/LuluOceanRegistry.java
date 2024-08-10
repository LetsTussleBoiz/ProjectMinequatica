package net.luluborealis.luluocean.core;

import net.luluborealis.luluocean.common.world.biome.LuluOceanBiomes;
import net.luluborealis.luluocean.common.world.feature.LuluOceanFeatures;
import net.luluborealis.luluocean.common.world.feature.LuluOceanPlacedFeatures;
import net.luluborealis.luluocean.common.world.feature.features.LuluOceanStructurePlacedFeatures;
import net.luluborealis.luluocean.common.world.feature.features.overworld.LuluOceanOverworldFeatures;
import net.luluborealis.luluocean.common.world.structure.LuluOceanStructureTypes;
import net.luluborealis.luluocean.common.world.structure.LuluOceanStructures;
import net.luluborealis.luluocean.registry.RegisterGameEvents;

public class LuluOceanRegistry {
    public static void loadClasses() {
        RegisterGameEvents.loadClass();
        LuluOceanFeatures.loadClass();
        LuluOceanBiomes.loadClass();
        LuluOceanStructureTypes.loadClass();
        LuluOceanOverworldFeatures.loadClass();
        LuluOceanPlacedFeatures.loadClass();
        LuluOceanStructurePlacedFeatures.loadClass();
        LuluOceanStructures.loadClass();
    }
}