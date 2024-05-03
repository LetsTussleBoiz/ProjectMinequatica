package net.luluborealis.luluocean.core;

import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.feature.BYGFeatures;
import net.luluborealis.luluocean.common.world.feature.BYGPlacedFeatures;
import net.luluborealis.luluocean.common.world.feature.features.BYGStructurePlacedFeatures;
import net.luluborealis.luluocean.common.world.feature.features.overworld.BYGOverworldFeatures;
import net.luluborealis.luluocean.common.world.structure.BYGStructureTypes;
import net.luluborealis.luluocean.common.world.structure.BYGStructures;

public class BYGRegistry {
    public static void loadClasses() {
        BYGFeatures.loadClass();
        BYGStructureTypes.loadClass();
        BYGOverworldFeatures.loadClass();
        BYGPlacedFeatures.loadClass();
        BYGStructurePlacedFeatures.loadClass();
        BYGStructures.loadClass();
        LuluOcean.logInfo("Loaded BYG registry");
    }
}