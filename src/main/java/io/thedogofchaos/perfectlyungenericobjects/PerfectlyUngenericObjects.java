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

package io.thedogofchaos.perfectlyungenericobjects;

import com.google.common.base.CaseFormat;
import com.mojang.logging.LogUtils;
import io.thedogofchaos.perfectlyungenericobjects.common.CommonProxy;
import io.thedogofchaos.perfectlyungenericobjects.client.ClientProxy;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(PerfectlyUngenericObjects.MOD_ID)
public class PerfectlyUngenericObjects {
    /** Your mods' id. MUST ALWAYS BE THE SAME AS THE mod_id FIELD IN gradle.properties */
    public static final String MOD_ID = "perfectly_ungeneric_objects";
    /** The human-readable version of your mod's id. Works best if this is the same as the mod_name field in gradle.properties. */
    public static final String MOD_NAME = "Perfectly Ungeneric Objects";

    /** Initialises a SLF4J logging instance, for outputting information to the console (and by extension, the logfiles) on-demand. */
    public static final Logger LOGGER = LogUtils.getLogger();

    public PerfectlyUngenericObjects() {
        PerfectlyUngenericObjects.init();

        // DistExecutor allows us to make sure that the correct code is being loaded for the current side that the mod is running on (either a dedicated client, or a dedicated server).
        // Dedicated servers WILL CRASH if they encounter ANY method calls or classes that would be client-only, like rendering calls. Use this to be sure.
        CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        proxy.init(proxy.modEventBus);
    }

    /** Do anything that needs to be executed statically here. */
    public static void init() {
        LOGGER.info("We're loading {} on the {}", MOD_NAME, FMLEnvironment.dist);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, path));
    }
}