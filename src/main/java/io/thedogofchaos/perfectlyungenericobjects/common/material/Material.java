package io.thedogofchaos.perfectlyungenericobjects.common.material;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static io.thedogofchaos.perfectlyungenericobjects.PerfectlyUngenericObjects.LOGGER;

/** Inspired largely by Gregtech Modern's own material system */
public class Material {
    private static int MINIMUM_COLOURS_LENGTH = 2; // TODO: possibly make this dynamic based on which mods of the suite are installed? or is that overcomplicating things?

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
        // do shit here
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
            // todo: define shit to
            return this;
        }

        public Builder setTextures(MaterialTextures textures) {
            this.materialInfo.textures = textures;
            return this;
        }

        public Builder setPrimaryColour(int colour) {
            this.materialInfo.colours[0] = colour;
            return this;
        }

        public Builder setSecondaryColour(int colour) {
            this.materialInfo.colours[1] = colour;
            return this;
        }

        public Builder setAdditionalColours(int[] additionalColours) {
            if(!this.materialInfo.haveAdditionalColoursBeenSet){
                var originalColours = this.materialInfo.colours;

                var newLength = additionalColours.length + originalColours.length;
                int[] mergedColours = new int[newLength];

                System.arraycopy(originalColours, 0, mergedColours, 0, originalColours.length);
                System.arraycopy(additionalColours, 0, mergedColours, originalColours.length, additionalColours.length);

                this.materialInfo.colours = mergedColours; // !: DESTRUCTIVE ACTION, BE WEARY.
                this.materialInfo.haveAdditionalColoursBeenSet = true;
            }
            return this;
        }

        public Material buildAndRegister() {
            // validateColours(this.materialInfo.colours);
            var material = new Material(materialInfo);
            material.registerMaterial();
            return material;
        }

        private void validateColours(int[] colours) {
            if (colours != null) {
                if (colours.length < MINIMUM_COLOURS_LENGTH) {
                   LOGGER.warn("To whoever is registering '"+materialInfo.id.getPath()+"', you provided Material.Builder#setColors(int[]) with an int[] that had less elements than the minimum of ("+ MINIMUM_COLOURS_LENGTH +"). As such, the int[] you provided has been merged on top of a default array.") ;
                }
            } else {
                // basic values so anything after this doesn't throw a hissyfit if colours is null
                this.materialInfo.colours = new int[]{0xFFFFFF, 0xC0C0C0, 0x808080};
                LOGGER.warn("To whoever is registering '"+materialInfo.id.getPath()+"', you either: Forgot to call Material.Builder#setColors(int[]), OR directly passed null to it. Either way, the material in question has had a default set of colours applied to it, to prevent catastrophes down the line.");
            }
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
        private int[] colours; // TODO: deliberate on whether should i keep this as int[], or should i abstract a little to IntList

        private MaterialInfo(@NotNull ResourceLocation id){
            this.id = id;
            this.colours = new int[MINIMUM_COLOURS_LENGTH];
            Arrays.fill(this.colours, -1);
        }
    }
}
