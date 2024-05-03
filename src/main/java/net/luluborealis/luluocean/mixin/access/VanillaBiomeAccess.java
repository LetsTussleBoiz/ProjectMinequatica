package net.luluborealis.luluocean.mixin.access;

import net.minecraft.data.worldgen.biome.OverworldBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = OverworldBiomes.class, remap = false)
public interface VanillaBiomeAccess {

    @Invoker("calculateSkyColor")
    static int byg_invokeCalculateSkyColor(float f) {
        throw new Error("Mixin did not apply!");
    }
}
