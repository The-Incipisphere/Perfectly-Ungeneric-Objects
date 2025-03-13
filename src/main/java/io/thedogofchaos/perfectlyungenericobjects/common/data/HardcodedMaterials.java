package io.thedogofchaos.perfectlyungenericobjects.common.data;

import io.thedogofchaos.perfectlyungenericobjects.common.material.Material;
import io.thedogofchaos.perfectlyungenericobjects.common.material.MaterialTextures;

import static io.thedogofchaos.perfectlyungenericobjects.PerfectlyUngenericObjects.id;

/** TODO: Nuke this class in favor of data-driven/KubeJS registration (but only when this mod is ready for those) */
public class HardcodedMaterials {
    public static Material Standardium = new Material.Builder(id("standardium"))
            .ingot() // e.g. also auto-gens Nuggets, Blocks and Dusts with pre-set properties (non exhaustive)
            .setTextures(MaterialTextures.DEV) // will be an enum class until i get some form of dynamically accessing textures
            .setColors(new int[]{0x00FF00, 0x008000, 0x004000, 0x800080}) // may or may not be useful in the case of other modules like Parcel Pyxis or Jade's Atrium
            .buildAndRegister();
    public static Material Componentium;
    public static Material Gemstonium;
    public static Material Fluidium;
    public static Material PerfectlyGenericObject;
}
