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

package io.thedogofchaos.perfectlyungenericobjects.common.data;

import io.thedogofchaos.perfectlyungenericobjects.PerfectlyUngenericObjects;
import io.thedogofchaos.perfectlyungenericobjects.common.material.Material;
import io.thedogofchaos.perfectlyungenericobjects.common.material.MaterialTextures;

import static io.thedogofchaos.perfectlyungenericobjects.PerfectlyUngenericObjects.id;

/** TODO: Nuke this class in favor of data-driven/KubeJS registration (but only when this mod is ready for those) */
public class HardcodedMaterials {
    public static Material Standardium;
    public static Material Componentium;
    public static Material Gemstonium;
    public static Material Fluidium;
    public static Material PerfectlyGenericObject;

    public static void init(){
        PerfectlyUngenericObjects.LOGGER.debug("we should now be loading hardcoded materials");

        Standardium = new Material.Builder(id("standardium"))
                .ingot()
                .setTextures(MaterialTextures.DEV) // will be an enum class until i get some way of dynamically accessing textures
                .setPrimaryColour(0x00FF00)
                .setSecondaryColour(0x008000)
                .setAdditionalColours(new int[]{0x008000, 0x004000, 0x800080})
                .buildAndRegister();
    }
}
