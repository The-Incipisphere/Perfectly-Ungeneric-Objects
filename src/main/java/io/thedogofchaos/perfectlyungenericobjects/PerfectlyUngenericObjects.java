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
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.nio.file.Path;

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

    /**
     * @return if we're running in a production environment
     */
    public static boolean isProd() {
        return FMLLoader.isProduction();
    }

    /**
     * @return if we're not running in a production environment
     */
    public static boolean isDev() {
        return !isProd();
    }

    /**
     * @return if we're running data generation
     */
    public static boolean isDataGen() {
        return FMLLoader.getLaunchHandler().isData();
    }

    /**
     * A friendly reminder that the server instance is populated on the server side only, so null/side check it!
     *
     * @return the current minecraft server instance
     */
    public static MinecraftServer getMinecraftServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    /**
     * @param modId the mod id to check for
     * @return if the mod whose id is {@code modId} is loaded or not
     */
    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    /**
     * For async stuff use this, otherwise use {@link PerfectlyUngenericObjects#isClientSide}
     *
     * @return if the current thread is the client thread
     */
    public static boolean isClientThread() {
        return isClientSide() && Minecraft.getInstance().isSameThread();
    }

    /**
     * @return if the FML environment is a client
     */
    public static boolean isClientSide() {
        return FMLEnvironment.dist.isClient();
    }

    /**
     * This check isn't the same for client and server!
     *
     * @return if it's safe to access the current instance {@link net.minecraft.world.level.Level Level} on client or if
     *         it's safe to access any level on server.
     */
    public static boolean canGetServerLevel() {
        if (isClientSide()) {
            return Minecraft.getInstance().level != null;
        }
        var server = getMinecraftServer();
        return server != null &&
                !(server.isStopped() || server.isShutdown() || !server.isRunning() || server.isCurrentlySaving());
    }

    /**
     * @return the path to the minecraft instance directory
     */
    public static Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }
}