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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class RegisterGameEvents {
	public static final DeferredRegister<GameEvent> PROVIDER = DeferredRegister.create(BuiltInRegistries.GAME_EVENT.key(), LuluOcean.MOD_ID);
	public static final RegistryObject<GameEvent> SCULK_SENSOR_ACTIVATE = register(
			"sculk_sensor_activate",
			() -> new GameEvent(WilderSharedConstants.string("sculk_sensor_activate"),16));
	public static final RegistryObject<GameEvent> TENDRIL_EXTRACT_XP = register(
			"hanging_tendril_extract_xp",
			() -> new GameEvent(WilderSharedConstants.string("hanging_tendril_extract_xp"),16));

	private RegisterGameEvents() {
		throw new UnsupportedOperationException("RegisterGameEvents contains only static declarations.");
	}

	public static void registerEvents() {
		WilderSharedConstants.logWithModId("Registering GameEvents for", WilderSharedConstants.UNSTABLE_LOGGING);
	}

	@NotNull
	private static RegistryObject<GameEvent> register(@NotNull String path, Supplier<GameEvent> gameEvent) {
		return PROVIDER.register(path, gameEvent);
	}

	@Contract(pure = true)
	public static void loadClass() {
	}
}
