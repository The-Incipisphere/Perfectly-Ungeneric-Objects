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

package io.thedogofchaos.perfectlyungenericobjects.common.registry;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.thedogofchaos.perfectlyungenericobjects.common.material.Material;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

import static io.thedogofchaos.perfectlyungenericobjects.common.CommonProxy.REGISTRATE;
import static io.thedogofchaos.perfectlyungenericobjects.PerfectlyUngenericObjects.LOGGER;

/** Singleton class that handles registration of all materials defined using this mod. */
@ApiStatus.Internal
public class MaterialRegistry {
    /** The Singleton instance of this class. Initialises early.*/
    private static final MaterialRegistry INSTANCE = new MaterialRegistry();
    private final Map<ResourceLocation, Material> MATERIALS = new HashMap<>();
    private final Map<String, RegistryEntry<Block>> MATERIAL_BLOCKS = new HashMap<>();
    private final Map<String, RegistryEntry<Item>> MATERIAL_ITEMS = new HashMap<>();

    /** Disclaimer: If you're trying to set this field from another mod, you acknowledge that serious bullshit can happen, and should not complain if said bullshit does happen. (if you do complain anyway, <b>I will fucking {@code System.exit(-1);} you.</b>) */
    @Getter
    @Setter
    private boolean materialRegistryIsFrozen = false;

    /** DISCLAIMER: If you use reflection to make this constructor public, and shit hits the fan because of that, it's your fault for not using {@link MaterialRegistry#getInstance()}.*/
    private MaterialRegistry(){}

    /** Returns the singleton instance of this class.*/
    public static MaterialRegistry getInstance() {
        return INSTANCE;
    }

    public void register(Material material) {
        if (!this.materialRegistryIsFrozen) {
            if(!this.MATERIALS.containsKey(material.getMaterialInfo().getId())){
                this.MATERIALS.put(material.getMaterialInfo().getId(), material);
            } else {
                throw new IllegalStateException("Tried to register '"+material.getMaterialInfo().getId()+"', but that material has already been registered!");
            }
        } else {
            throw new IllegalStateException("Tried to register '"+material.getMaterialInfo().getId()+"', but the material registry has been frozen!");
        }
    }

    /** All material generation takes place through this method. */
    public void generateMaterials() {
        LOGGER.debug("Generating ");
        var materials = this.MATERIALS.values();
        materials.forEach(material -> {
            if(material.getMaterialComponents().contains(Material.MaterialComponent.INGOT)){
                ItemEntry<Item> ingotItemEntry = makeMaterialItem(material);
                MATERIAL_ITEMS.put(material.getNameWithSuffix("ingot"), ingotItemEntry);
            }
            if(material.getMaterialComponents().contains(Material.MaterialComponent.STORAGE_BLOCK)){
                BlockEntry<Block> storageBlockItemEntry = makeMaterialBlock(material);
                MATERIAL_BLOCKS.put(material.getNameWithSuffix("ingot"), storageBlockItemEntry);
            }
        });
    }

    private BlockEntry<Block> makeMaterialBlock(Material material) {
        LOGGER.debug("Attempting to genererate {}", material.getNameWithSuffix("storage_block"));
        return REGISTRATE
                .block(material.getNameWithSuffix("storage_block"), Block::new)
                .item()
                .build()
                .register();
    }

    private ItemEntry<Item> makeMaterialItem(Material material) {
        LOGGER.debug("Attempting to genererate {}", material.getNameWithSuffix("ingot"));
        return REGISTRATE
                .item(material.getNameWithSuffix("ingot"), Item::new)
                .register();
    }

    private ItemEntry<Item> makeNuggetItem(Material material) {
        LOGGER.debug("Attempting to genererate {}", material.getNameWithSuffix("nugget"));
        return REGISTRATE
                .item(material.getNameWithSuffix("nugget"), Item::new)
                .register();
    }
}
