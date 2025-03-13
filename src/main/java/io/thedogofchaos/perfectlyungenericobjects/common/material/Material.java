package io.thedogofchaos.perfectlyungenericobjects.common.material;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static io.thedogofchaos.perfectlyungenericobjects.PerfectlyUngenericObjects.LOGGER;

/** Inspired largely by Gregtech Modern's own material system */
public class Material {
    @NotNull
    @Getter
    private final MaterialInfo materialInfo;

    public Material(MaterialInfo materialInfo) {
        this.materialInfo = materialInfo;
    }

    protected Material(ResourceLocation id) {
        this.materialInfo = new MaterialInfo(id);
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

        private final MaterialInfo materialInfo;

        public Builder(@NotNull ResourceLocation id) {
            if (id.getPath().isEmpty()) {
                throw new IllegalArgumentException("Material name cannot be empty!");
            }
            if (id.getPath().charAt(id.getPath().length() - 1) == '_')
                throw new IllegalArgumentException("Material name '"+id.getPath()+"' cannot end with a '_'!");
            this.materialInfo = new MaterialInfo(id);
        }

        public Builder metal() {
            // todo: define shit for the material registry to autogen Blocks, Ingots, and Nuggets
            return this;
        }

        public Builder setTextures(MaterialTextures textures) {
            this.materialInfo.textures = textures;
            return this;
        }

        public Builder setPrimaryColour(int colour) {
            this.materialInfo.colours.set(0, colour);
            return this;
        }

        public Builder setSecondaryColour(int colour) {
            this.materialInfo.colours.set(1, colour);
            return this;
        }

        public Builder setAdditionalColours(int[] additionalColours) {
            if(!this.materialInfo.haveAdditionalColoursBeenSet){
                this.materialInfo.colours.addAll(IntArrayList.wrap(additionalColours));
                this.materialInfo.haveAdditionalColoursBeenSet = true;
            }
            return this;
        }

        public Material buildAndRegister() {
            var material = new Material(materialInfo);
            material.registerMaterial();
            return material;
        }
    }

    private static class MaterialInfo {
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
}
