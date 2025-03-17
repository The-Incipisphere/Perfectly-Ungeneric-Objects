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
