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

package io.thedogofchaos.perfectlyungenericobjects.common.material;

import io.thedogofchaos.perfectlyungenericobjects.common.registry.MaterialRegistry;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;

/** Inspired largely by GregTech Modern's own material system */
public class Material {
    @NotNull
    @Getter
    private final MaterialInfo materialInfo;
    @Getter
    private EnumSet<MaterialComponent> materialComponents;
    @Getter
    private EnumSet<MaterialFlag> materialFlags;

    // i wont even begin to claim that i know what the fuck this constructor is for
    protected Material(@NotNull ResourceLocation id) {
        this.materialInfo = new MaterialInfo(id);
        this.materialInfo.textures = MaterialTextures.DEV;
        this.materialComponents = EnumSet.noneOf(MaterialComponent.class);
        this.materialFlags = EnumSet.noneOf(MaterialFlag.class);
    }

    private Material(MaterialInfo materialInfo, EnumSet<MaterialComponent> materialComponents, EnumSet<MaterialFlag> materialFlags) {
        this.materialInfo = materialInfo;
        this.materialComponents = materialComponents;
        this.materialFlags = materialFlags;
    }

    public String getName() {
        return this.materialInfo.id.getPath();
    }

    public String getNameWithSuffix(String suffix) {
        return String.format("%s_%s", this.materialInfo.id.getPath(), suffix);
    }
    public String getNameWithPrefix(String prefix) {
        return String.format("%s_%s", prefix, this.materialInfo.id.getPath());
    }

    private void registerMaterial() {
        MaterialRegistry.getInstance().register(this);
    }

    public static class Builder {
        private EnumSet<MaterialComponent> materialComponents = EnumSet.noneOf(MaterialComponent.class);
        private EnumSet<MaterialFlag> materialFlags = EnumSet.noneOf(MaterialFlag.class);
        private final MaterialInfo materialInfo;

        /**
         * Creates a new material builder for the given material ID.
         *
         * @param id The unique identifier for this material (as a {@link ResourceLocation}).
         * @throws IllegalArgumentException if the material name is empty or ends with '_'.
         */
        public Builder(@NotNull ResourceLocation id) {
            if (id.getPath().isEmpty()) {
                throw new IllegalArgumentException("Material name cannot be empty!");
            }
            if (id.getPath().charAt(id.getPath().length() - 1) == '_')
                throw new IllegalArgumentException("Material name '"+id.getPath()+"' cannot end with a '_'!");
            this.materialInfo = new MaterialInfo(id);
        }

        // todo: define shit for the material registry (once it's ready) to be able to specifically autogen Blocks, Ingots, and Nuggets

        /**
         * Marks this material as having an ingot form.
         * <br> This will also automatically include dust, nugget, and block forms by default.
         *
         * @return The {@link Builder} instance ,for chaining.
         */
        public Builder ingot() {
            return ingot(true, true, true);
        }

        /**
         * Marks this material as having an ingot form, with control over additional forms.
         *
         * @param alsoGenerateDust If true, also includes a dust form.
         * @param alsoGenerateNugget If true, also includes a nugget form.
         * @param alsoGenerateBlock If true, also includes a block form.
         * @return The {@link Builder} instance, for chaining.
         */
        public Builder ingot(boolean alsoGenerateDust, boolean alsoGenerateNugget, boolean alsoGenerateBlock) {
            materialComponents.add(MaterialComponent.INGOT);
            if (alsoGenerateDust) materialComponents.add(MaterialComponent.DUST);
            if (alsoGenerateNugget) materialComponents.add(MaterialComponent.NUGGET);
            if (alsoGenerateBlock) materialComponents.add(MaterialComponent.STORAGE_BLOCK);
            return this;
        }

        /**
         * Sets the texture set for this material.
         *
         * @param textures The {@link MaterialTextures} defining the texture style you want.
         * @return The {@link Builder} instance, for chaining.
         */
        public Builder setTextures(MaterialTextures textures) {
            this.materialInfo.textures = textures;
            return this;
        }

        /**
         * Sets the primary colour for this material.
         *
         * @param colour The primary colour as an integer.
         * @return The {@link Builder} instance, for chaining.
         */
        public Builder setPrimaryColour(int colour) {
            this.materialInfo.colours.set(0, colour);
            return this;
        }

        /**
         * Sets the secondary colour for this material.
         *
         * @param colour The secondary colour as an integer.
         * @return The {@link Builder} instance, for chaining.
         */
        public Builder setSecondaryColour(int colour) {
            this.materialInfo.colours.set(1, colour);
            return this;
        }

        /**
         * Defines additional colours for the material, if applicable.
         * <br> This method only applies colours the first time it is called.
         *
         * @param additionalColours An array of integers representing the additional colour values.
         * @return The {@link Builder} instance, for chaining.
         */
        public Builder setAdditionalColours(int[] additionalColours) {
            if(!this.materialInfo.haveAdditionalColoursBeenSet){
                this.materialInfo.colours.addAll(IntArrayList.wrap(additionalColours));
                this.materialInfo.haveAdditionalColoursBeenSet = true;
            }
            return this;
        }

        /**
         * Finalizes the material and registers it.
         *
         * @return The constructed {@link Material} instance.
         */
        public Material buildAndRegister() {
            validateMaterialComponents();
            validateMaterialFlags();
            var material = new Material(materialInfo, materialComponents, materialFlags);
            material.registerMaterial();
            return material;
        }

        /** Checks whether the applied flags of a material are valid, given its components.
         * @throws IllegalStateException (see the method body for what it throws exactly)
         */
        private void validateMaterialFlags() {
            if(this.materialFlags.contains(MaterialFlag.HAS_COMPRESSION_RECIPES)) {
                if(
                        !materialComponents.containsAll(Set.of(MaterialComponent.NUGGET, MaterialComponent.INGOT, MaterialComponent.STORAGE_BLOCK)) &&
                        !materialComponents.containsAll(Set.of(MaterialComponent.NUGGET, MaterialComponent.INGOT)) &&
                        !materialComponents.containsAll(Set.of(MaterialComponent.INGOT, MaterialComponent.STORAGE_BLOCK)) &&
                        !materialComponents.containsAll(Set.of(MaterialComponent.GEM, MaterialComponent.STORAGE_BLOCK))
                ){
                    throw new IllegalStateException("Material Flag HAS_COMPRESSION_RECIPES requires a valid combination of the following components: (Nugget+Ingot+Block, Nugget+Ingot, Ingot+Block, or Gem+Block). '"+this.materialInfo.id+"' doesn't have any combinations of them! ");
                }
            }
        }

        /** Checks whether the combination of a material's components is valid.
         * @throws IllegalStateException If both {@link MaterialComponent#INGOT} and {@link MaterialComponent#GEM} exist on the same material.
         */
        private void validateMaterialComponents() {
            if(this.materialComponents.contains(MaterialComponent.INGOT) && this.materialComponents.contains(MaterialComponent.GEM)){
                throw new IllegalStateException("Tried to register '"+this.materialInfo.id+"', but it has both INGOT and GEM components, which is not allowed!");
            }
        }
    }

    public static class MaterialInfo {
        @Getter
        @NotNull
        private final ResourceLocation id;

        private boolean haveAdditionalColoursBeenSet = false;

        @Getter
        private MaterialTextures textures;

        @Getter
        private IntList colours;

        private MaterialInfo(@NotNull ResourceLocation id){
            this.id = id;
            this.colours = new IntArrayList(new int[]{-1,-1});
        }
    }

    /**
     * Represents the different physical forms a material can take.
     * A material may consist of one or more of these components, which determine
     * what items or blocks are generated for it.
     */
    public enum MaterialComponent {
        /** If this component is present on a material, an Ingot {@link Item} will be generated for it. THIS COMPONENT IS MUTUALLY EXCLUSIVE WITH {@link MaterialComponent#GEM}! */
        INGOT,
        /** If this component is present on a material, a Nugget {@link Item} will be generated for it. */
        NUGGET,
        /** If this component is present on a material, a {@link BlockItem} will be generated for it.</b> */
        STORAGE_BLOCK,
        /** If this component is present on a material, a Dust {@link Item} will be generated for it. */
        DUST,
        /** If this component is present on a material, a Gem {@link Item} will be generated for it. THIS COMPONENT IS MUTUALLY EXCLUSIVE WITH {@link MaterialComponent#INGOT}! */
        GEM
    }

    public enum MaterialFlag {
        /** If this flag is present alongside any of the following combinations of components, basic 9:1 compression recipes will be automatically generated.
         * <ul>
         *     <li>{@link MaterialComponent#NUGGET} and {@link MaterialComponent#INGOT}</li>
         *     <li>{@link MaterialComponent#INGOT} and {@link MaterialComponent#STORAGE_BLOCK}</li>
         *     <li>{@link MaterialComponent#NUGGET} and {@link MaterialComponent#INGOT} and {@link MaterialComponent#STORAGE_BLOCK}</li>
         *     <li>{@link MaterialComponent#GEM} and {@link MaterialComponent#STORAGE_BLOCK}</li>
         * </ul>
         */
        HAS_COMPRESSION_RECIPES
    }
}
