package io.thedogofchaos.perfectlyungenericobjects.common.material;

import lombok.Getter;

/** temporary enum class until i can implement data-driven/KubeJS registration of texture sets */
public enum MaterialTextures {
    DEV("dev");

    @Getter
    private final String textureSetName;

    MaterialTextures(String textureSetName) {
        this.textureSetName = textureSetName;
    }
}
