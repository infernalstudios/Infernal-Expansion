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

package org.infernalstudios.infernalexp.util;

import com.google.gson.JsonObject;

import org.infernalstudios.infernalexp.InfernalExpansion;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.ModList;

/**
 * Condition for use in recipes making use of Quark Flags.
 */

public class CompatibilityQuark implements ICondition {

    private final ResourceLocation resourceLocation;
    private final String flag;

    public CompatibilityQuark(ResourceLocation resourceLocation, String flag) {
        this.resourceLocation = resourceLocation;
        this.flag = flag;
    }

    @Override
    public ResourceLocation getID() {
        return this.resourceLocation;
    }

    @Override
    public boolean test() {
        if (ModList.get().isLoaded("quark")) {
            JsonObject jsonDummy = new JsonObject();
            jsonDummy.addProperty("type", "quark:flag");
            jsonDummy.addProperty("flag", this.flag);
            return CraftingHelper.getCondition(jsonDummy).test();
        } else {
            return false;
        }
    }

    public static class Serializer implements IConditionSerializer<CompatibilityQuark> {
        private final ResourceLocation resourceLocation;

        public Serializer() {
            this.resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, "quark_flag");
        }

        @Override
        public void write(JsonObject json, CompatibilityQuark value) {
            json.addProperty("flag", value.flag);
        }

        @Override
        public CompatibilityQuark read(JsonObject json) {
            return new CompatibilityQuark(this.resourceLocation, json.getAsJsonPrimitive("flag").getAsString());
        }

        @Override
        public ResourceLocation getID() {
            return this.resourceLocation;
        }
    }
}
