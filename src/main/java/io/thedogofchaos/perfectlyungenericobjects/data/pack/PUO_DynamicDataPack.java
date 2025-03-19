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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class PUO_DynamicDataPack implements PackResources {
    @Override
    public @Nullable IoSupplier<InputStream> getRootResource(String... strings) {
        return null;
    }

    @Override
    public @Nullable IoSupplier<InputStream> getResource(PackType packType, ResourceLocation resourceLocation) {
        return null;
    }

    @Override
    public void listResources(PackType packType, String s, String s1, ResourceOutput resourceOutput) {

    }

    @Override
    public Set<String> getNamespaces(PackType packType) {
        return Set.of();
    }

    @Override
    public @Nullable <T> T getMetadataSection(MetadataSectionSerializer<T> metadataSectionSerializer) throws IOException {
        return null;
    }

    @Override
    public String packId() {
        return "";
    }

    @Override
    public void close() {

    }
}
