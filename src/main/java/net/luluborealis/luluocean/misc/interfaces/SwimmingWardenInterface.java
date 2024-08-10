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

package net.luluborealis.luluocean.misc.interfaces;

import org.spongepowered.asm.mixin.Unique;

public interface SwimmingWardenInterface {

	boolean wilderWild$isTouchingWaterOrLava();

	boolean wilderWild$isSubmergedInWaterOrLava();

    @Unique
    boolean projectMinequatica_1_20_1$isTouchingWaterOrLava();

    @Unique
    boolean projectMinequatica_1_20_1$isSubmergedInWaterOrLava();
}
