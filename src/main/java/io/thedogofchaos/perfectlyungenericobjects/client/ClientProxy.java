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

package io.thedogofchaos.perfectlyungenericobjects.client;

import io.thedogofchaos.perfectlyungenericobjects.common.CommonProxy;
import net.minecraftforge.eventbus.api.IEventBus;

import static io.thedogofchaos.perfectlyungenericobjects.PerfectlyUngenericObjects.LOGGER;

/**
 * The ClientProxy class handles all client-specific operations for the mod.
 *
 * <p>
 * It extends CommonProxy, so it already has all the common functionality shared by both client and server.
 * This class should be used for any tasks that should only happen on the client side, like rendering, input handling,
 * and sound management.
 * </p>
 *
 * <p>
 * Since this class is only loaded on the client, it's safe to use client-only code here.
 * </p>
 */
public class ClientProxy extends CommonProxy {
    public ClientProxy(){
        super();
        LOGGER.info("ClientProxy loading!");
    }

    @Override
    public void init(IEventBus modEventBus) {
        super.init(modEventBus);
    }
}