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

import com.mojang.serialization.Codec;
import net.luluborealis.luluocean.LuluOcean;
import net.luluborealis.luluocean.misc.WilderSharedConstants;
import net.luluborealis.luluocean.particle.options.FloatingSculkBubbleParticleOptions;
import net.luluborealis.luluocean.particle.options.WindParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class RegisterParticles {
	public static final DeferredRegister<ParticleType<?>> PROVIDER = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE.key(), LuluOcean.MOD_ID);
	public static final RegistryObject<ParticleType<FloatingSculkBubbleParticleOptions>> FLOATING_SCULK_BUBBLE = register("floating_sculk_bubble", false, FloatingSculkBubbleParticleOptions.DESERIALIZER, particleType -> FloatingSculkBubbleParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<WindParticleOptions>> WIND = register("wind", false, WindParticleOptions.DESERIALIZER, particleType -> WindParticleOptions.CODEC);

	private RegisterParticles() {
		throw new UnsupportedOperationException("RegisterParticles contains only static declarations.");
	}

	public static void registerParticles() {
		WilderSharedConstants.logWithModId("Registering Particles for", WilderSharedConstants.UNSTABLE_LOGGING);
	}

	@NotNull
	private static RegistryObject<ParticleType<?>> register(@NotNull String name, boolean alwaysShow) {
		return PROVIDER.register(name, () -> new SimpleParticleType(alwaysShow));
	}

	@NotNull
	private static RegistryObject<ParticleType<?>> register(@NotNull String name) {
		return register(name, false);
	}

	@NotNull
	private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(
			@NotNull String name, boolean alwaysShow,
			@NotNull ParticleOptions.Deserializer<T> factory,
			Function<ParticleType<T>, Codec<T>> codecGetter) {
		return PROVIDER.register(name, () -> new ParticleType<T>(alwaysShow, factory) {
			@Override
			public @NotNull Codec<T> codec() {
				return codecGetter.apply(this);
			}
		});
	}
}
