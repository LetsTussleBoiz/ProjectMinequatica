/*
 * Copyright 2024 FrozenBlock
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

package net.luluborealis.luluocean.networking.packet;

import io.netty.buffer.ByteBuf;
import net.luluborealis.luluocean.misc.WilderSharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.ICustomPacket;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public record WilderSensorHiccupPacket(double x, double y, double z){

	private static final int PARTICLE_COLOR = 5578058;
	public static final double COLOR_X = (double) (PARTICLE_COLOR >> 16 & 255) / 255.0D;
	public static final double COLOR_Y = (double) (PARTICLE_COLOR >> 8 & 255) / 255.0D;
	public static final double COLOR_Z = (double) (PARTICLE_COLOR & 255) / 255.0D;

	public WilderSensorHiccupPacket(@NotNull FriendlyByteBuf buf) {
		this(buf.readDouble(), buf.readDouble(), buf.readDouble());
	}



	public void toBytes(@NotNull FriendlyByteBuf buf) {
		buf.writeDouble(this.x());
		buf.writeDouble(this.y());
		buf.writeDouble(this.z());
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			LocalPlayer localPlayer = Minecraft.getInstance().player;
            assert localPlayer != null;
            localPlayer.clientLevel.addParticle(
					ParticleTypes.ENTITY_EFFECT,
					this.x(),
					this.y(),
					this.z(),
					1D,
					1D,
					1D
			);
		});
		ctx.get().setPacketHandled(true);
	}
}
