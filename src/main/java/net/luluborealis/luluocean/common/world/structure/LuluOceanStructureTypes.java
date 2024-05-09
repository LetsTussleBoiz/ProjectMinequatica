package net.luluborealis.luluocean.common.world.structure;

import com.mojang.serialization.Codec;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.structure.arch.ArchStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class LuluOceanStructureTypes {

    public static final DeferredRegister<StructureType<?>> PROVIDER = DeferredRegister.create(Registries.STRUCTURE_TYPE, LuluOcean.MOD_ID);

    public static final RegistryObject<StructureType<ArchStructure>> ARCH = PROVIDER.register("arch", () -> explicitStructureTypeTyping(ArchStructure.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(Codec<T> structureCodec) {
        return () -> structureCodec;
    }

    public static void loadClass() {
    }
}