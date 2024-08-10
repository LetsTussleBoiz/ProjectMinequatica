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

import net.luluborealis.luluocean.LuluOcean;
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
import org.jetbrains.annotations.NotNull;
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
	private float projectMinequatica_1_20_1$leaningPitch;
	@Unique
	private float projectMinequatica_1_20_1$lastLeaningPitch;
	@Unique
	private boolean projectMinequatica_1_20_1$newSwimming;

	private WardenSwimMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Unique
	private static boolean projectMinequatica_1_20_1$touchingWaterOrLava(@NotNull Entity entity) {
		return entity.isInWaterOrBubble() || entity.isInLava() || entity.isVisuallySwimming();
	}

	@Unique
	private static boolean projectMinequatica_1_20_1$submergedInWaterOrLava(@NotNull Entity entity) {
		return entity.isEyeInFluid(FluidTags.WATER) || entity.isEyeInFluid(FluidTags.LAVA) || entity.isVisuallySwimming();
	}

	@Shadow
	public abstract boolean isDiggingOrEmerging();

	@Shadow
	public abstract Brain<Warden> getBrain();

	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo ci) {
		this.projectMinequatica_1_20_1$updateSwimAmount();
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		nbt.putBoolean("newSwimming", this.projectMinequatica_1_20_1$newSwimming);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		this.projectMinequatica_1_20_1$newSwimming = nbt.getBoolean("newSwimming");
	}

	@Unique
	private void projectMinequatica_1_20_1$updateSwimAmount() {
		this.projectMinequatica_1_20_1$lastLeaningPitch = this.projectMinequatica_1_20_1$leaningPitch;
		if (this.isVisuallySwimming()) {
			this.projectMinequatica_1_20_1$leaningPitch = Math.min(1F, this.projectMinequatica_1_20_1$leaningPitch + 0.09F);
		} else {
			this.projectMinequatica_1_20_1$leaningPitch = Math.max(0F, this.projectMinequatica_1_20_1$leaningPitch - 0.09F);
		}
	}

	@Override
	public boolean isVisuallySwimming() {
		return this.projectMinequatica_1_20_1$newSwimming || super.isVisuallySwimming();
	}

	@Inject(at = @At("RETURN"), method = "createNavigation", cancellable = true)
	public void projectMinequatica_1_20_1$createNavigation(Level level, CallbackInfoReturnable<PathNavigation> info) {
		info.setReturnValue(new WardenNavigation(Warden.class.cast(this), level));
	}

	@Override
	public void travel(@NotNull Vec3 travelVector) {
		Warden warden = Warden.class.cast(this);
		var temp = this.isEffectiveAi();
		var temp2 = this.projectMinequatica_1_20_1$isTouchingWaterOrLava();
		if (this.projectMinequatica_1_20_1$isTouchingWaterOrLava() && EntityConfig.WardenConfig.wardenSwims) {
			this.moveRelative(this.getSpeed(), travelVector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
			if (!this.isDiggingOrEmerging() && !warden.hasPose(Pose.SNIFFING) && !warden.hasPose(Pose.DYING) && !warden.hasPose(Pose.ROARING)) {
				if (this.projectMinequatica_1_20_1$isSubmergedInWaterOrLava()) {
					warden.setPose(Pose.SWIMMING);
				} else {
					warden.setPose(Pose.STANDING);
				}
			}

			this.projectMinequatica_1_20_1$newSwimming = this.getFluidHeight(FluidTags.WATER) >= this.getEyeHeight(this.getPose()) * 0.75 || this.getFluidHeight(FluidTags.LAVA) >= this.getEyeHeight(this.getPose()) * 0.75;
		} else {
			super.travel(travelVector);
			this.projectMinequatica_1_20_1$newSwimming = false;
		}
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void projectMinequatica_1_20_1$wardenEntity(EntityType<? extends Monster> entityType, Level level, CallbackInfo ci) {
		Warden wardenEntity = Warden.class.cast(this);
		wardenEntity.setPathfindingMalus(BlockPathTypes.WATER, 0F);
		wardenEntity.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1F);
		wardenEntity.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1F);
		this.moveControl = new WardenMoveControl(wardenEntity, 0.05F, 80F, 0.23F, 1F);
		this.lookControl = new WardenLookControl(wardenEntity, 10);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return EntityConfig.WardenConfig.wardenSwims;
	}

	@Override
	public boolean isPushedByFluid() {
		return !EntityConfig.WardenConfig.wardenSwims;
	}

	@Override
	@NotNull
	public SoundEvent getSwimSound() {
		return EntityConfig.WardenConfig.wardenSwims ? RegisterSounds.ENTITY_WARDEN_SWIM.get() : super.getSwimSound();
	}

	@Override
	public void jumpInLiquid(@NotNull TagKey<Fluid> fluid) {
		if (EntityConfig.WardenConfig.wardenSwims && (this.getBrain().hasMemoryValue(MemoryModuleType.ROAR_TARGET) || this.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET))) {
			Optional<LivingEntity> ATTACK_TARGET = this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
			Optional<LivingEntity> ROAR_TARGET = this.getBrain().getMemory(MemoryModuleType.ROAR_TARGET);
			LivingEntity target = ATTACK_TARGET.orElseGet(() -> ROAR_TARGET.orElse(null));

			if (target != null) {
				if ((!projectMinequatica_1_20_1$touchingWaterOrLava(target) || !projectMinequatica_1_20_1$submergedInWaterOrLava(this)) && target.getY() > this.getY()) {
					super.jumpInLiquid(fluid);
				}
			}
		} else {
			super.jumpInLiquid(fluid);
		}
	}

	@Unique
	@Override
	public float getSwimAmount(float tickDelta) {
		return Mth.lerp(tickDelta, this.projectMinequatica_1_20_1$lastLeaningPitch, this.projectMinequatica_1_20_1$leaningPitch);
	}

	@Override
	protected boolean updateInWaterStateAndDoFluidPushing() {
		if (EntityConfig.WardenConfig.wardenSwims) {
			Warden warden = Warden.class.cast(this);
			this.fluidHeight.clear();
			warden.updateInWaterStateAndDoWaterCurrentPushing();
			boolean bl = warden.updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, 0.1D);
			return this.projectMinequatica_1_20_1$isTouchingWaterOrLava() || bl;
		}
		return super.updateInWaterStateAndDoFluidPushing();
	}

	@Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
	public void projectMinequatica_1_20_1$modifySwimmingDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> info) {
		if (!this.isDiggingOrEmerging() && this.isVisuallySwimming()) {
			info.setReturnValue(EntityDimensions.scalable(this.getType().getWidth(), 0.85F));
		}
	}

	@Unique
	@Override
	public boolean projectMinequatica_1_20_1$isTouchingWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isInWaterOrBubble() || warden.isInLava();
	}

	@Unique
	@Override
	public boolean projectMinequatica_1_20_1$isSubmergedInWaterOrLava() {
		Warden warden = Warden.class.cast(this);
		return warden.isEyeInFluid(FluidTags.WATER) || warden.isEyeInFluid(FluidTags.LAVA);
	}
}
