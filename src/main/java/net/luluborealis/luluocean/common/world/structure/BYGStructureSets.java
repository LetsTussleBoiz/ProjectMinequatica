package net.luluborealis.luluocean.common.world.structure;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.luluborealis.luluocean.LuluOcean;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;
import java.util.Map;

public class BYGStructureSets {
    public static final Map<ResourceKey<StructureSet>, StructureSetFactory> STRUCTURE_SET_FACTORIES = new Reference2ObjectOpenHashMap<>();

    public static final ResourceKey<StructureSet> SEA_ARCHES = register((structureHolderGetter) -> new StructureSet(List.of(StructureSet.entry(structureHolderGetter.getOrThrow(BYGStructures.OVERGROWN_STONE_ARCH), 24), StructureSet.entry(structureHolderGetter.getOrThrow(BYGStructures.STONE_ARCH), 1)), new RandomSpreadStructurePlacement(5, 2, RandomSpreadType.LINEAR, 457854789)));
    private static ResourceKey<StructureSet> register(StructureSetFactory factory) {
        ResourceKey<StructureSet> structureSetResourceKey = ResourceKey.create(Registries.STRUCTURE_SET, LuluOcean.createLocation("sea_arches"));
        STRUCTURE_SET_FACTORIES.put(structureSetResourceKey, factory);
        return structureSetResourceKey;
    }

    @FunctionalInterface
    public interface StructureSetFactory {
        StructureSet generate(HolderGetter<Structure> placedFeatureHolderGetter);
    }
}