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

package net.luluborealis.luluocean.registry;

import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public final class RegisterSounds {
	public static final DeferredRegister<SoundEvent> PROVIDER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LuluOcean.MOD_ID);

	public static final RegistryObject<SoundEvent> ENTITY_WARDEN_KIRBY_DEATH = registerSound("entity.warden.kirby_death");
	public static final RegistryObject<SoundEvent> ENTITY_WARDEN_DYING = registerSound("entity.warden.dying");
	public static final RegistryObject<SoundEvent> ENTITY_WARDEN_UNDERWATER_DYING = registerSound("entity.warden.dying_underwater");
	public static final RegistryObject<SoundEvent> ENTITY_WARDEN_SWIM = registerSound("entity.warden.swim");
	public static final RegistryObject<SoundEvent> ENTITY_WARDEN_BRAP = registerSound("entity.warden.brap");
	public static final RegistryObject<SoundEvent> BLOCK_SCULK_SENSOR_HICCUP = registerSound("block.sculk_sensor.hiccup");
	public static final RegistryObject<SoundEvent> BLOCK_SCULK_SHRIEKER_GARGLE = registerSound("block.sculk_shrieker.gargle");
	public static final RegistryObject<SoundEvent> ENTITY_WARDEN_STELLA_HEARTBEAT = registerSound("entity.warden.stella_heartbeat");

	private RegisterSounds() {
		throw new UnsupportedOperationException("RegisterSounds contains only static declarations.");
	}

	private static RegistryObject<SoundEvent> registerSound(String path) {
		var id = WilderSharedConstants.id(path);
		return PROVIDER.register(path, () -> SoundEvent.createVariableRangeEvent(id));
	}

	@NotNull
	private static Holder.Reference<SoundEvent> registerForHolder(@NotNull String string) {
		return registerForHolder(WilderSharedConstants.id(string));
	}

	@NotNull
	private static Holder.Reference<SoundEvent> registerForHolder(@NotNull ResourceLocation resourceLocation) {
		return registerForHolder(resourceLocation, resourceLocation);
	}

	@NotNull
	private static Holder.Reference<SoundEvent> registerForHolder(@NotNull ResourceLocation resourceLocation, @NotNull ResourceLocation resourceLocation2) {
		return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation2));
	}

	public static void init() {
		WilderSharedConstants.logWithModId("Registering SoundEvents for", WilderSharedConstants.UNSTABLE_LOGGING);
	}

}
