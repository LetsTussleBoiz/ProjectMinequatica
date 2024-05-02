package net.luluborealis.luluocean.mixin.access;

import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Biome.class)
public interface BiomeAccess {

    @Invoker("getGrassColorFromTexture")
    int byg_invokeGetGrassColorFromTexture();

    @Accessor("climateSettings")
    Biome.ClimateSettings byg_getClimateSettings();
}