package io.thedogofchaos.perfectlyungenericobjects.common;

import io.thedogofchaos.perfectlyungenericobjects.common.registry.MaterialRegistry;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonRegistry {
    public static void init(IEventBus modEventBus) {
        MaterialRegistry materialRegistry = MaterialRegistry.getInstance();

        materialRegistry.setMaterialRegistryIsFrozen(false);
        materialRegistry.generateMaterials();
        materialRegistry.setMaterialRegistryIsFrozen(true);
    }
}
