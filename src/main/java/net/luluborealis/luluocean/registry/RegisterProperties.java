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

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public final class RegisterProperties {
	//Vanilla Blocks
	public static final IntegerProperty SOULS_TAKEN = IntegerProperty.create("souls_taken", 0, 2); //Sculk Shrieker
	public static final BooleanProperty HICCUPPING = BooleanProperty.create("hiccupping"); //Sculk Sensor

	private RegisterProperties() {
		throw new UnsupportedOperationException("RegisterProperties contains only static declarations.");
	}

	public static void init() {
	}

}
