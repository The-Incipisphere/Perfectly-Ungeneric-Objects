package io.thedogofchaos.perfectlyungenericobjects.common.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.thedogofchaos.perfectlyungenericobjects.common.material.Material;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

/** Singleton class that handles registration of all materials defined using this mod. */
public class MaterialRegistry {
    /** The Singleton instance of this class. Initialises early.*/
    private static final MaterialRegistry INSTANCE = new MaterialRegistry();
    private final Map<ResourceLocation, Material> MATERIALS = new HashMap<>();
    private final Map<String, RegistryEntry<BlockItem>> MATERIAL_BLOCKS = new HashMap<>();
    private final Map<String, RegistryEntry<Item>> MATERIAL_ITEMS = new HashMap<>();

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
            if(this.MATERIALS.containsKey(material.getMaterialInfo().getId())){
                this.MATERIALS.put(material.getMaterialInfo().getId(), material);
            } else {
                throw new IllegalStateException("Tried to register '"+material.getMaterialInfo().getId()+"', but that material has already been registered!");
            }
        } else {
            throw new IllegalStateException("Tried to register '"+material.getMaterialInfo().getId()+"', but the material registry has been frozen!");
        }
    }

    public void generateMaterials() {
        var materials = this.MATERIALS.values();
        materials.forEach(material -> {
            if(material.getMaterialComponents().contains(Material.MaterialComponent.NUGGET)){
                ItemEntry<Item> nuggetItemEntry = makeNuggetItem(material); // TODO: implement makeNuggetItem
                MATERIAL_ITEMS.put(material.getNameWithSuffix("nugget"), nuggetItemEntry);
            }
            if(material.getMaterialComponents().contains(Material.MaterialComponent.INGOT)){
                ItemEntry<Item> ingotItemEntry = makeIngotItem(material); // TODO: implement makeIngotItem
                MATERIAL_ITEMS.put(material.getNameWithSuffix("ingot"), ingotItemEntry);
            }
            if(material.getMaterialComponents().contains(Material.MaterialComponent.STORAGE_BLOCK)){
                ItemEntry<BlockItem> storageBlockItemEntry = makeStorageBlockItem(material); // TODO: implement makeStorageBlockItem
                MATERIAL_BLOCKS.put(material.getNameWithSuffix("ingot"), storageBlockItemEntry);
            }
        });
    }
}
