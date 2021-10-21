/*
 * Copyright 2021 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.access;

import com.google.common.base.Supplier;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.client.ClientFireType;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public interface FireTypeAccess {
    KnownFireTypes getFireType();

    void setFireType(KnownFireTypes type);

    Material LOCATION_SOUL_FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/soul_fire_0"));
    Material LOCATION_SOUL_FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/soul_fire_1"));

    Material LOCATION_GLOW_FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(InfernalExpansion.MOD_ID, "block/glow_fire_0"));
    Material LOCATION_GLOW_FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(InfernalExpansion.MOD_ID, "block/glow_fire_1"));

    Material LOCATION_ENDER_FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("endergetic", "block/ender_fire_0"));
    Material LOCATION_ENDER_FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("endergetic", "block/ender_fire_1"));

    Material LOCATION_BORIC_FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("byg", "block/boric_fire_0"));
    Material LOCATION_BORIC_FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("byg", "block/boric_fire_1"));

    Material LOCATION_CRYPTIC_FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("byg", "block/cryptic_fire_0"));
    Material LOCATION_CRYPTIC_FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("byg", "block/cryptic_fire_1"));

    public static enum KnownFireTypes {
        FIRE("fire", () -> new ClientFireType(ModelBakery.FIRE_0, ModelBakery.FIRE_1)),
        SOUL_FIRE("soul_fire", () -> new ClientFireType(LOCATION_SOUL_FIRE_0, LOCATION_SOUL_FIRE_1)),
        GLOW_FIRE("glow_fire", () -> new ClientFireType(LOCATION_GLOW_FIRE_0, LOCATION_GLOW_FIRE_1)),
        ENDER_FIRE("ender_fire", () -> new ClientFireType(LOCATION_ENDER_FIRE_0, LOCATION_ENDER_FIRE_1)),
        BORIC_FIRE("boric_fire", () -> new ClientFireType(LOCATION_BORIC_FIRE_0, LOCATION_BORIC_FIRE_1)),
        CRYPTIC_FIRE("cryptic_fire", () -> new ClientFireType(LOCATION_CRYPTIC_FIRE_0, LOCATION_CRYPTIC_FIRE_1));

        public static final KnownFireTypes[] VALUES = values();
        public static final Map<String, KnownFireTypes> NAME_LOOKUP = Arrays.stream(VALUES).collect(Collectors.toMap(KnownFireTypes::getName, (fireType) -> fireType));
        private final String name;
        private final Supplier<ClientFireType> supplier;

        KnownFireTypes(String name, Supplier<ClientFireType> supplier) {
            this.name = name;
            this.supplier = supplier;
        }

        @Override
        public String toString() {
            return getName();
        }

        public String getName() {
            return name;
        }

        public Supplier<ClientFireType> getSupplier() {
            return supplier;
        }

        @Nullable
        public static KnownFireTypes byName(String name) {
            return name.equals("") ? FIRE : NAME_LOOKUP.get(name.toLowerCase(Locale.ROOT));
        }
    }

}
