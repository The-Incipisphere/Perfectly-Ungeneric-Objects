package io.thedogofchaos.becsmodtemplate.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static io.thedogofchaos.becsmodtemplate.Becs_Mod_Template.LOGGER;

/**<p>
 * This class handles the logic for your mod on dedicated servers.
 * Most of your mod's features, like registration and event handling, should be managed here.
 * </p>
 * <p>
 * This class is extended by ClientProxy because both Minecraft clients and dedicated servers share some common code.
 * (Fun fact: Singleplayer is actually just a server running locally on your computer!)
 * </p>
 * <p>
 * However, <b>do not</b> attempt to perform client-only actions or access client-only classes (e.g. for: rendering, input handling, or sound management) in this class or anything else accessed by this class.
 * Doing so will cause crashes on dedicated servers, as those client-only classes do not exist in the server build.
 * </p>
 */
public class CommonProxy {
    // public static Registrate REGISTRATE; // UNCOMMENT IF USING REGISTRATE
    /** You don't usually need to do anything with this field, only for things that expect a {@link FMLJavaModLoadingContext} field as a parameter.*/
    public final FMLJavaModLoadingContext modLoadingContext = FMLJavaModLoadingContext.get();
    /**<p>
     * <blockquote cite="https://forge.gemwire.uk/wiki/Events">
     * An <b>event</b> is a signal that is fired on an <b>event bus</b> to inform registered listeners about some type of action or state.
     * This is the primary way by which Forge allows mods to hook into vanilla and game behavior;
     * Forge has an array of different events which are fired when different actions happen within the game, and mods may act upon receiving these events.
     * </blockquote>
     * <a href="https://forge.gemwire.uk/wiki/Events">Forge Community Wiki: Events</a>
     * </p>
     * <p>
     * In this case, this is getting the event bus from the {@link CommonProxy#modLoadingContext} above, so that you can register your mod's event listeners to the bus.
     * </p>
     */
    public final IEventBus modEventBus = modLoadingContext.getModEventBus();

    public CommonProxy(){
        LOGGER.info("CommonProxy loading!");
    }

    /** This method should only be executed by either {@code super.init(modEventBus);} in {@link io.thedogofchaos.becsmodtemplate.client.ClientProxy#init(IEventBus)} or {@code proxy.init(proxy.modEventBus);} in {@link io.thedogofchaos.becsmodtemplate.Becs_Mod_Template}*/
    public void init(IEventBus modEventBus) {
        // REGISTRATE = Registrate.create(Becs_Mod_Template.MOD_ID); // UNCOMMENT IF USING REGISTRATE
    }
}
