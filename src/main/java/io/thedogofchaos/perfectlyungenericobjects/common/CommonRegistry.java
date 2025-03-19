/*
    Perfectly Ungeneric Objects - A utility mod to make registering new materials a breeze.
    Copyright (C) 2025 - The Incipisphere

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.thedogofchaos.perfectlyungenericobjects.common;

import io.thedogofchaos.perfectlyungenericobjects.common.data.HardcodedMaterials;
import io.thedogofchaos.perfectlyungenericobjects.common.registry.MaterialRegistry;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonRegistry {
    public static void init(IEventBus modEventBus) {
        MaterialRegistry materialRegistry = MaterialRegistry.getInstance();
        materialRegistry.setMaterialRegistryIsFrozen(false);
        HardcodedMaterials.init();
        materialRegistry.generateMaterials();
        materialRegistry.setMaterialRegistryIsFrozen(true);
    }
}
