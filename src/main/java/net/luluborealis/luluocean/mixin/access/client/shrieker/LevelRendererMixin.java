/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.luluborealis.luluocean.mixin.access.client.shrieker;

import net.luluborealis.luluocean.registry.RegisterSounds;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Shadow
	@Nullable
	private ClientLevel level;

	@Inject(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 0), cancellable = true)
	private void levelEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
		assert this.level != null;
		if (this.level.getBlockState(pos).getValue(BlockStateProperties.WATERLOGGED) || this.level.getBlockState(pos.above()).getBlock() == Blocks.WATER || this.level.getFluidState(pos.above()).is(FluidTags.WATER)) {
			this.level
					.playLocalSound(
							(double) pos.getX() + 0.5D,
							(double) pos.getY() + SculkShriekerBlock.TOP_Y,
							(double) pos.getZ() + 0.5D,
							RegisterSounds.BLOCK_SCULK_SHRIEKER_GARGLE.get(),
							SoundSource.BLOCKS,
							2.0F,
							0.6F + this.level.random.nextFloat() * 0.4F,
							false
					);
			ci.cancel();
		}
	}

//	@Inject(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 0), require = 0)
//	private void shriekerGargle(int pType, BlockPos pPos, int pData, CallbackInfo ci) {
//		boolean toReturn = false;
//		if (this.level != null) {
//			if (this.level.getFluidState(pPos.above()).is(FluidTags.WATER)) {
//				this.level.playLocalSound(
//					(double) pPos.getX() + 0.5D,
//					(double) pPos.getY() + SculkShriekerBlock.TOP_Y,
//					(double) pPos.getZ() + 0.5D,
//					RegisterSounds.BLOCK_SCULK_SHRIEKER_GARGLE.get(),
//					SoundSource.BLOCKS,
//					2.0F,
//					0.6F + this.level.random.nextFloat() * 0.4F,
//					false
//				);
//			}
//		}
//		return original;
//	}
}
