package net.luluborealis.luluocean.util;

import net.minecraft.world.level.levelgen.PositionalRandomFactory;

public interface SeedGetter {
    PositionalRandomFactory getRandom();
}