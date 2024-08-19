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
import net.luluborealis.luluocean.entity.render.animations.WilderWarden;
import net.luluborealis.luluocean.misc.interfaces.SwimmingWardenInterface;
import net.luluborealis.luluocean.registry.RegisterSounds;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(method = "isAlive", at = @At("HEAD"), cancellable = true)
	public void isAlive(CallbackInfoReturnable<Boolean> info) {
		if (this.wilderWild$isWardenWithDeathAnimation()) {
			info.setReturnValue(((WilderWarden) this).wilderWild$getDeathTicks() <= 0 && !LivingEntity.class.cast(this).isRemoved());
		}
	}

	@Inject(method = "tickDeath", at = @At("HEAD"), cancellable = true)
	public void tickDeath(CallbackInfo info) {
		if (this.wilderWild$isWardenWithDeathAnimation()) {
			Warden warden = Warden.class.cast(this);
			WilderWarden wilderWarden = (WilderWarden) warden;
			Level level = warden.level();
			int deathTicks = wilderWarden.wilderWild$getDeathTicks() + 1;
			wilderWarden.wilderWild$setDeathTicks(deathTicks);
			if (!level.isClientSide()) {
				if (deathTicks == 35) {
					warden.deathTime = 35;
				} else if (deathTicks == 53) {
					level.broadcastEntityEvent(warden, EntityEvent.POOF);
					level.broadcastEntityEvent(warden, (byte) 69420);
				} else if (deathTicks == 70) {
					warden.remove(Entity.RemovalReason.KILLED);
				}
			}
			info.cancel();
		}
	}

	@Inject(method = "die", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;broadcastEntityEvent(Lnet/minecraft/world/entity/Entity;B)V"))
	public void die(DamageSource damageSource, CallbackInfo info) {
		if (this.wilderWild$isWardenWithDeathAnimation()) {
			Warden warden = Warden.class.cast(this);
			warden.getBrain().removeAllBehaviors();
			warden.setNoAi(true);
			WilderWarden wilderWarden = (WilderWarden) warden;
			if (!wilderWarden.wilderWild$isStella()) {
				if (!(warden instanceof SwimmingWardenInterface swimmingWardenInterface) || !swimmingWardenInterface.wilderWild$isSubmergedInWaterOrLava()) {

					warden.playSound(RegisterSounds.ENTITY_WARDEN_DYING.get(), 5.0F, 1.0F);
				} else {
					warden.playSound(RegisterSounds.ENTITY_WARDEN_UNDERWATER_DYING.get(), 5.0F, warden.getVoicePitch());
				}
			}
		}
	}

	@Unique
	public boolean wilderWild$isWardenWithDeathAnimation() {
		return LivingEntity.class.cast(this) instanceof Warden warden && (EntityConfig.WardenConfig.wardenDeathAnimation || ((WilderWarden) warden).wilderWild$isStella());
	}

}
