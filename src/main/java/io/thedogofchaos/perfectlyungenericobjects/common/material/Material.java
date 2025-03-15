package io.thedogofchaos.perfectlyungenericobjects.common.material;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

/** Inspired largely by GregTech Modern's own material system */
public class Material {
    @NotNull
    @Getter
    private final MaterialInfo materialInfo;
    @Getter
    private EnumSet<MaterialComponent> components;

    public Material(@NotNull MaterialInfo materialInfo) {
        this.materialInfo = materialInfo;
    }

    protected Material(ResourceLocation id, EnumSet<MaterialComponent> components) {
        this.materialInfo = new MaterialInfo(id);
        this.components = components;
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
        // do shit here once the material registry is up and running
    }

    public static class Builder {
        private EnumSet<MaterialComponent> components = EnumSet.noneOf(MaterialComponent.class);
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
            components.add(MaterialComponent.INGOT);
            if (alsoGenerateDust) components.add(MaterialComponent.DUST);
            if (alsoGenerateNugget) components.add(MaterialComponent.NUGGET);
            if (alsoGenerateBlock) components.add(MaterialComponent.BLOCK);
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
            // this.materialInfo.colours.replaceAll(colour -> colour == -1 ? 0xFFFFFF : colour); // might not need this
            var material = new Material(materialInfo);
            material.registerMaterial();
            return material;
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
        private IntList colours; // TODO: deliberate on whether should i keep this as int[], or should i abstract a little to IntList

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
        /** The material can exist as an ingot. Typically used for metals. */
        INGOT,
        /** A smaller unit of an ingot, often obtained through crafting or smelting. */
        NUGGET,
        /** A solid block form of the material, usually craftable from its ingots or dust. */
        BLOCK,
        /** A powdered form of the material, often produced via grinding or chemical processing. */
        DUST,
        /** A gem-like form of the material, representing crystalline or valuable substances. */
        GEM
    }
}
