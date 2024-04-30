package net.luluborealis.luluocean.common.world.placement;

import com.mojang.serialization.Codec;
import net.luluborealis.luluocean.LuluOcean;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BYGPlacementModifierType {

    private static final DeferredRegister<PlacementModifierType<?>> PROVIDER = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, LuluOcean.MOD_ID);

    public static final RegistryObject<PlacementModifierType<ChunkCoveringPlacement>> CHUNK_COVERING_PLACEMENT = register("chunk_covering_placement", ChunkCoveringPlacement.CODEC);
    public static final RegistryObject<PlacementModifierType<NearWaterPlacementFilter>> NEAR_WATER_FILTER = register("near_water_filter", NearWaterPlacementFilter.CODEC);
    public static final RegistryObject<PlacementModifierType<AboveHeightmapFilter>> ABOVE_HEIGHTMAP_FILTER = register("above_heightmap_filter", AboveHeightmapFilter.CODEC);
    public static final RegistryObject<PlacementModifierType<IsBiomeTagFilter>> IS_BIOME_TAG_FILTER = register("is_biome_tag_filter", IsBiomeTagFilter.CODEC);
    public static final RegistryObject<PlacementModifierType<IsDimensionFilter>> IS_DIMENSION_FILTER = register("is_dimension_filter", IsDimensionFilter.CODEC);

    private static <P extends PlacementModifier> RegistryObject<PlacementModifierType<P>> register(String id, Codec<P> codec) {
        return PROVIDER.register(id, () -> () -> codec);
    }

    public static void bootStrap() {
    }
}