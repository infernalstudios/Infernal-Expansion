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

package org.infernalstudios.infernalexp.api;

import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FireType {

    private static final Map<ResourceLocation, FireType> FIRE_TYPES = new HashMap<>();

    private final ResourceLocation name;
    private final ResourceLocation block;
    private final ResourceLocation sprite0;
    private final ResourceLocation sprite1;

    private FireType(ResourceLocation name, ResourceLocation block, ResourceLocation sprite0, ResourceLocation sprite1) {
        this.name = name;
        this.block = block;
        this.sprite0 = sprite0;
        this.sprite1 = sprite1;

        FIRE_TYPES.put(name, this);
    }

    public static FireType register(ResourceLocation name) {
        return register(name, name, "block/" + name.getPath());
    }

    public static FireType register(ResourceLocation name, ResourceLocation block) {
        return register(name, block, "block/" + name.getPath());
    }

    public static FireType register(ResourceLocation name, ResourceLocation block, String spriteLocation) {
        return register(name, block, spriteLocation + "_0", spriteLocation + "_1");
    }

    public static FireType register(ResourceLocation name, ResourceLocation block, String spriteLocation0, String spriteLocation1) {
        if (FIRE_TYPES.containsKey(name))
            throw new IllegalStateException(name.toString() + " already exists in the FireType registry.");

        return new FireType(name, block, new ResourceLocation(name.getNamespace(), spriteLocation0), new ResourceLocation(name.getNamespace(), spriteLocation1));
    }

    public static FireType getOrDefault(ResourceLocation name, FireType defaultType) {
        return FIRE_TYPES.getOrDefault(name, defaultType);
    }

    public static Set<FireType> getFireTypes() {
        return new HashSet<>(FIRE_TYPES.values());
    }

    public ResourceLocation getName() {
        return this.name;
    }

    public ResourceLocation getBlock() {
        return this.block;
    }

    @OnlyIn(Dist.CLIENT)
    public RenderMaterial getSprite0() {
        return RenderMaterialCache.getOrCreate(this.sprite0);
    }

    @OnlyIn(Dist.CLIENT)
    public RenderMaterial getSprite1() {
        return RenderMaterialCache.getOrCreate(this.sprite1);
    }

    @OnlyIn(Dist.CLIENT)
    private static final class RenderMaterialCache {

        private static final Map<ResourceLocation, RenderMaterial> RENDER_MATERIALS = new HashMap<>();

        private static RenderMaterial getOrCreate(ResourceLocation resourceLocation) {
            if (!RENDER_MATERIALS.containsKey(resourceLocation))
                RENDER_MATERIALS.put(resourceLocation, new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, resourceLocation));

            return RENDER_MATERIALS.get(resourceLocation);
        }

    }

}
