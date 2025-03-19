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

package io.thedogofchaos.perfectlyungenericobjects.data.pack;

import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class PUO_PackSource implements RepositorySource {
    private final String name;
    private final PackType type;
    private final Pack.Position position;
    private final Function<String, PackResources> resources;

    @Override
    public void loadPacks(Consumer<Pack> onLoad) {
        onLoad.accept(Pack.readMetaAndCreate(name,
                Component.literal(name),
                true,
                resources::apply,
                type,
                position,
                PackSource.BUILT_IN));
    }
}

