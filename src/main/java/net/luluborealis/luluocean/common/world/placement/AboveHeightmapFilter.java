package net.luluborealis.luluocean.common.world.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class AboveHeightmapFilter extends PlacementModifier {
    public static Codec<AboveHeightmapFilter> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Heightmap.Types.CODEC.fieldOf("above_type").forGetter(nearWaterPlacementFilter -> nearWaterPlacementFilter.heightmap)
    ).apply(builder, AboveHeightmapFilter::new));

    private final Heightmap.Types heightmap;

    public AboveHeightmapFilter(Heightmap.Types heightmap) {
        this.heightmap = heightmap;
    }


    @Override
    public @NotNull Stream<BlockPos> getPositions(PlacementContext placementContext, @NotNull RandomSource random, BlockPos blockPos) {
        return blockPos.getY() >= placementContext.getHeight(heightmap, blockPos.getX(), blockPos.getZ()) ? Stream.of(blockPos) : Stream.empty();
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return BYGPlacementModifierType.ABOVE_HEIGHTMAP_FILTER.get();
    }
}