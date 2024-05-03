package net.luluborealis.luluocean.mixin.access;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CommandSourceStack.class, remap = false)
public interface CommandSourceStackAccess {

    @Accessor("source")
    CommandSource byg_getSource();
}
