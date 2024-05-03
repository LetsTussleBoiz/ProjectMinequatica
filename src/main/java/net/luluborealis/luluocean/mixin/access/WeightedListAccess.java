package net.luluborealis.luluocean.mixin.access;


import com.google.common.collect.ImmutableList;
import net.minecraft.util.random.WeightedRandomList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = WeightedRandomList.class, remap = false)
public interface WeightedListAccess<E> {

    @Accessor("totalWeight")
    int byg_getTotalWeight();

    @Accessor("items")
    ImmutableList<E> byg_getItems();
}