/*
 * Copyright 2022 Infernal Studios
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

import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.InfernalExpansion;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface FireTypeAccess {

    FireTypes getFireType();

    void setFireType(FireTypes type);

    enum FireTypes {
        FIRE(new ResourceLocation("fire"), () -> new FireType(new ResourceLocation("block/fire_0"), new ResourceLocation("block/fire_1"))),
        SOUL_FIRE(new ResourceLocation("soul_fire"), () -> new FireType(new ResourceLocation("block/soul_fire_0"), new ResourceLocation("block/soul_fire_1"))),
        GLOW_FIRE(new ResourceLocation(InfernalExpansion.MOD_ID, "glow_fire"), () -> new FireType(new ResourceLocation(InfernalExpansion.MOD_ID, "block/glow_fire_0"), new ResourceLocation(InfernalExpansion.MOD_ID, "block/glow_fire_1"))),
        ENDER_FIRE(new ResourceLocation("endergetic", "ender_fire"), () -> new FireType(new ResourceLocation("endergetic", "block/ender_fire_0"), new ResourceLocation("endergetic", "block/ender_fire_1"))),
        BORIC_FIRE(new ResourceLocation("byg", "boric_fire"), () -> new FireType(new ResourceLocation("byg", "block/boric_fire_0"), new ResourceLocation("byg", "block/boric_fire_1"))),
        CRYPTIC_FIRE(new ResourceLocation("byg", "cryptic_fire"), () -> new FireType(new ResourceLocation("byg", "block/cryptic_fire_0"), new ResourceLocation("byg", "block/cryptic_fire_0")));

        public static final Map<ResourceLocation, FireTypes> NAME_LOOKUP = Arrays.stream(values()).collect(Collectors.toMap(FireTypes::getName, (fireType) -> fireType));
        private final ResourceLocation name;
        private final Supplier<FireType> supplier;

        FireTypes(ResourceLocation name, Supplier<FireType> supplier) {
            this.name = name;
            this.supplier = supplier;
        }

        @Override
        public String toString() {
            return getName().toString();
        }

        public ResourceLocation getName() {
            return name;
        }

        public Supplier<FireType> getSupplier() {
            return supplier;
        }

        @Nonnull
        public static FireTypes byName(ResourceLocation name) {
            return NAME_LOOKUP.get(name) == null ? FIRE : NAME_LOOKUP.get(name);
        }
    }

    class FireType {

        private final ResourceLocation associatedSprite0;
        private final ResourceLocation associatedSprite1;

        public FireType(ResourceLocation associatedSprite0, ResourceLocation associatedSprite1) {
            this.associatedSprite0 = associatedSprite0;
            this.associatedSprite1 = associatedSprite1;
        }

        @OnlyIn(Dist.CLIENT)
        public RenderMaterial getAssociatedSprite0() {
            return new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, associatedSprite0);
        }

        @OnlyIn(Dist.CLIENT)
        public RenderMaterial getAssociatedSprite1() {
            return new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, associatedSprite1);
        }

    }

}
