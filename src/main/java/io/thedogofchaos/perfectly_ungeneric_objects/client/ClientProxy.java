package io.thedogofchaos.perfectly_ungeneric_objects.client;

import io.thedogofchaos.perfectly_ungeneric_objects.common.CommonProxy;
import net.minecraftforge.eventbus.api.IEventBus;

import static io.thedogofchaos.perfectly_ungeneric_objects.PerfectlyUngenericObjects.LOGGER;

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