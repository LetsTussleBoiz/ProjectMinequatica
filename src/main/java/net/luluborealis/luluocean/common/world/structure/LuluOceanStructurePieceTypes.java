package net.luluborealis.luluocean.common.world.structure;

import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.structure.arch.ArchPiece;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;

public class LuluOceanStructurePieceTypes {
    public static final DeferredRegister<StructurePieceType> PROVIDER = DeferredRegister.create(Registries.STRUCTURE_PIECE, LuluOcean.MOD_ID);
    public static final RegistryObject<StructurePieceType> ARCH_PIECE = setFullContextPieceId(ArchPiece::new, "ArchPiece");

    private static RegistryObject<StructurePieceType> setFullContextPieceId(StructurePieceType type, String id) {
        return PROVIDER.register(id.toLowerCase(Locale.ROOT), () -> type);
    }

    public static void bootStrap() {
    }
}