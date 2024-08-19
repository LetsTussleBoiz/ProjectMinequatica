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

package net.luluborealis.luluocean.config;

public final class EntityConfig {

	public EntityConfig(){}

	public EntityConfig get() {
		return this;
	}

	public static class WardenConfig {
		public static boolean wardenAttacksImmediately = true;

		public static boolean wardenSwims = true;

		public static boolean wardenCustomTendrils = true;

		public static boolean wardenImprovedDig = true;

		public static boolean wardenImprovedEmerge = true;

		public static boolean wardenBedrockSniff = true;

		public static boolean wardenDeathAnimation = true;

		public static boolean wardenEmergesFromCommand = true;

		public static boolean wardenEmergesFromEgg = false;

	}
}
