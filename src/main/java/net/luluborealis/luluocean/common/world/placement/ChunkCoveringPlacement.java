package net.luluborealis.luluocean.common.world.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ChunkCoveringPlacement extends PlacementModifier {
    public static final ChunkCoveringPlacement INSTANCE = new ChunkCoveringPlacement();
    public static final Codec<ChunkCoveringPlacement> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public @NotNull Stream<BlockPos> getPositions(@NotNull PlacementContext placementContext, @NotNull RandomSource random, @NotNull BlockPos blockPos) {
        List<BlockPos> positions = new ArrayList<>();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int chunkMinX = SectionPos.sectionToBlockCoord(SectionPos.blockToSectionCoord(blockPos.getX()));
                int chunkMinZ = SectionPos.sectionToBlockCoord(SectionPos.blockToSectionCoord(blockPos.getZ()));
                positions.add(new BlockPos(chunkMinX + x, 0, chunkMinZ + z));
            }
        }
        return positions.stream();
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return BYGPlacementModifierType.CHUNK_COVERING_PLACEMENT.get();
    }
}