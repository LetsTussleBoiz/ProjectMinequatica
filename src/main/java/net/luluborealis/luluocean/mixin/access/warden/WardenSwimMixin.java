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

package net.luluborealis.luluocean.mixin.access.warden;

import net.luluborealis.luluocean.config.EntityConfig;
import net.luluborealis.luluocean.entity.ai.WardenLookControl;
import net.luluborealis.luluocean.entity.ai.WardenMoveControl;
import net.luluborealis.luluocean.entity.ai.WardenNavigation;
import net.luluborealis.luluocean.misc.interfaces.SwimmingWardenInterface;
import net.luluborealis.luluocean.registry.RegisterSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = Warden.class, priority = 1001)
public abstract class WardenSwimMixin extends Monster implements SwimmingWardenInterface {

	@Unique
	private float wilderWild$leaningPitch;
	@Unique
	private float wilderWild$lastLeaningPitch;
	@Unique
	private boolean wilderWild$isSwimming;

	private WardenSwimMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Shadow
	public abstract boolean isDiggingOrEmerging();

	@Shadow
	public abstract Brain<Warden> getBrain();

	@Shadow @Final private static Logger LOGGER;

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo ci) {
		this.wilderWild$updateSwimAmount();
	}

	/**
	 * Writes Warden NBT swim tag to save data
	 * @param nbt
	 * @param info
	 */
	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		nbt.putBoolean("newSwimming", this.wilderWild$isSwimming);
	}

	/**
	 * Checks save data for Warden NBT swim tag
	 * @param nbt
	 * @param info
	 */
	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		this.wilderWild$isSwimming = nbt.getBoolean("newSwimming");
	}

	/**
	 * Controls pitch of Warden to ensure he is tilted correctly while swimming
	 */
	@Unique
	private void wilderWild$updateSwimAmount() {
		this.wilderWild$lastLeaningPitch = this.wilderWild$leaningPitch;
		if (this.isVisuallySwimming()) {
			this.wilderWild$leaningPitch = Math.min(1F, this.wilderWild$leaningPitch + 0.09F);
		} else {
			this.wilderWild$leaningPitch = Math.max(0F, this.wilderWild$leaningPitch - 0.09F);
		}
	}

	/**
	 * Boolean to check if Warden is animated swimming
	 * @return
	 */
	@Override
	public boolean isVisuallySwimming() {
		return this.wilderWild$isSwimming || super.isVisuallySwimming();
	}

	/**
	 * Injects custom Warden Navigation class into AI
	 * @param level
	 * @param info
	 */
	@Inject(at = @At("RETURN"), method = "createNavigation", cancellable = true)
	public void wilderWild$createNavigation(Level level, CallbackInfoReturnable<PathNavigation> info) {
		info.setReturnValue(new WardenNavigation(Warden.class.cast(this), level));
	}

	/**
	 * Assigns variables to travel vectors and AI, then initiates swimming/standing animation based on what liquid it is in
	 * @param travelVector
	 */
	@Override
	public void travel(@NotNull Vec3 travelVector) {
		Warden warden = Warden.class.cast(this);
		if (this.wilderWild$isTouchingWaterOrLava()) {
			this.moveRelative(this.getSpeed(), travelVector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
			if (!this.isDiggingOrEmerging() && !warden.hasPose(Pose.SNIFFING) &&
					!warden.hasPose(Pose.DYING) && !warden.hasPose(Pose.ROARING)) {
				if (this.wilderWild$isSubmergedInWaterOrLava() && !this.onGround()) {
					LOGGER.info("Pose: Swimming");
				} else {
					LOGGER.info("Pose: Standing");
				}
			}

			this.goDownInWater();

			if (warden.getEyeInFluidType() != ForgeMod.EMPTY_TYPE.get()){
				this.wilderWild$isSwimming = this.getFluidTypeHeight(this.getEyeInFluidType()) >= this.getEyeHeight(this.getPose());
			} else {
				this.wilderWild$isSwimming = false;
			}
		} else {
			super.travel(travelVector);
			this.wilderWild$isSwimming = false;
		}
	}

	/**
	 * Modifies Warden AI to not have a pathfinding malus against water, so he doesn't freak out if he's swimming
	 * @param entityType
	 * @param level
	 * @param ci
	 */
	@Inject(method = "<init>", at = @At("TAIL"))
	private void wardenEntity(EntityType<? extends Monster> entityType, Level level, CallbackInfo ci) {
		Warden wardenEntity = Warden.class.cast(this);
		wardenEntity.setPathfindingMalus(BlockPathTypes.WATER, 0F);
		wardenEntity.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1F);
		wardenEntity.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1F);
		this.moveControl = new WardenMoveControl(wardenEntity, 0.05F, 80F, 0.23F, 1F);
		this.lookControl = new WardenLookControl(wardenEntity, 10);
	}

	/**
	 * Verifies the Warden cannot swim through Air, which in Forge counts as a LIQUID FOR SOME REASON
	 * @param type
	 * @return
	 */
	@Override
	public boolean canSwimInFluidType(FluidType type) {
        return type != ForgeMod.EMPTY_TYPE.get();
    }

	/**
	 * Override of method that Makes Warden breath underwater
	 * @return true
	 */
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	/**
	 * Override of method that Makes Warden not be pushed by water
	 * @return false
	 */
	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	/**
	 * A quick check to get the custom swimming sounds of the Warden
	 * @return Warden swim sounds
	 */
	@Override
	@NotNull
	public SoundEvent getSwimSound() {
		return EntityConfig.WardenConfig.wardenSwims ? RegisterSounds.ENTITY_WARDEN_SWIM.get() : super.getSwimSound();
	}

	/**
	 * Rewrites Warden AI to modify jumping behavior when in combat
	 * @param fluid
	 */
	@Override
	public void jumpInLiquid(@NotNull TagKey<Fluid> fluid) {
		if (this.getBrain().hasMemoryValue(MemoryModuleType.ROAR_TARGET) || this.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
			Optional<LivingEntity> ATTACK_TARGET = this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
			Optional<LivingEntity> ROAR_TARGET = this.getBrain().getMemory(MemoryModuleType.ROAR_TARGET);
			LivingEntity target = ATTACK_TARGET.orElseGet(() -> ROAR_TARGET.orElse(null));

			if (target != null) {
				boolean isTargetTouchingWater = wilderWild$targetTouchingWaterOrLava(target);
				boolean wardenIsInWater = this.wilderWild$isSubmergedInWaterOrLava();
				boolean targetIsAboveWarden = target.getY() > this.getY();

				if (!isTargetTouchingWater || (!wardenIsInWater && targetIsAboveWarden)) {
					super.jumpInLiquid(fluid);
					return;
				}
			}
		}

//		if(this.wilderWild$isSubmergedInWaterOrLava() || this.wilderWild$isTouchingWaterOrLava()){
//			this.goDownInWater();
//		}
		//super.jumpInLiquid(fluid);
	}

	/**
	 * An equation to calculate how far the Warden will sim
	 * @param tickDelta is the change in tick
	 * @return how far the warden swims
	 */
	@Override
	public float getSwimAmount(float tickDelta) {
		return Mth.lerp(tickDelta, this.wilderWild$lastLeaningPitch, this.wilderWild$leaningPitch);
	}

	/**
	 * Forces the game to acknowledge the Warden as an aquatic mob and not push him w/ water via a mixin override
	 * @return boolean
	 */
	@Override
	protected boolean updateInWaterStateAndDoFluidPushing() {
		Warden warden = Warden.class.cast(this);
		this.fluidHeight.clear();
		warden.updateInWaterStateAndDoWaterCurrentPushing();
		boolean bl = warden.updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, 0.1D);
		return this.wilderWild$isTouchingWaterOrLava() || bl;
	}

	/**
	 * Sets bounding box of Warden when actively swimming
	 * @param pose
	 * @param info
	 */
	@Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
	public void wilderWild$modifySwimmingDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> info) {
		if (!this.isDiggingOrEmerging() && this.isVisuallySwimming()) {
			info.setReturnValue(EntityDimensions.scalable(this.getType().getWidth(), 0.85F));
		}
	}

	@Unique
	private static boolean wilderWild$targetTouchingWaterOrLava(@NotNull Entity target) {
		return target.isInWaterOrBubble() || target.isInLava() || target.isVisuallySwimming();
	}

	/***
	 * Returns boolean based on if Warden is TOUCHING water
	 * @return boolean
	 */
	@Override
	public boolean wilderWild$isTouchingWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isInWaterOrBubble() || warden.isInLava();
	}

	/***
	 * Returns boolean based on if Warden is in water up to eye level (underwater)
	 * @return boolean
	 */
	@Override
	public boolean wilderWild$isSubmergedInWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.getEyeInFluidType() != ForgeMod.EMPTY_TYPE.get();
	}
}
