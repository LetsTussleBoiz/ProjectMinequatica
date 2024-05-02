package net.luluborealis.luluocean.common.world.structure;

import com.mojang.serialization.Codec;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.common.world.structure.arch.ArchStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BYGStructureTypes {

    public static final DeferredRegister<StructureType<?>> PROVIDER = DeferredRegister.create(Registries.STRUCTURE_TYPE, LuluOcean.MOD_ID);

    public static final RegistryObject<StructureType<ArchStructure>> ARCH = register("arch", () -> ArchStructure.CODEC);

    private static <S extends Structure> RegistryObject<StructureType<S>> register(String id, Supplier<? extends Codec<S>> codec) {
        return PROVIDER.register(id, () -> codec::get);
    }

    public static void loadClass() {
    }
}