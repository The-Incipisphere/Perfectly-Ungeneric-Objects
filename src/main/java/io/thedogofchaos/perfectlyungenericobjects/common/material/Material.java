package io.thedogofchaos.perfectlyungenericobjects.common.material;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class Material {
    protected Material(MaterialInfo materialInfo) {
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

        public Builder ingot() {
            return this;
        }

        public Builder setTextures(MaterialTextures dev) {
            return this;
        }

        public Builder setColors(int[] colors) {
            this.materialInfo.setColours(colors);
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

        @Getter
        @Setter
        private int[] colours;

        private MaterialInfo(@NotNull ResourceLocation id){
            this.id = id;
        }
    }
}
