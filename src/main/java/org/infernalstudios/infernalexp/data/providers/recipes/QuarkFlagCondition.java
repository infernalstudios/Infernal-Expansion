/*
 * Copyright 2023 Infernal Studios
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

package org.infernalstudios.infernalexp.data.providers.recipes;

import com.google.gson.JsonObject;

import org.infernalstudios.infernalexp.InfernalExpansion;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.ModList;

/**
 * Condition for use in recipes making use of Quark Flags.
 */

public class QuarkFlagCondition implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(InfernalExpansion.MOD_ID, "quark_flag");
    private final String flag;

    public QuarkFlagCondition(String flag) {
        this.flag = flag;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        if (ModList.get().isLoaded("quark")) {
            JsonObject jsonDummy = new JsonObject();
            jsonDummy.addProperty("type", "quark:flag");
            jsonDummy.addProperty("flag", this.flag);
            return CraftingHelper.getCondition(jsonDummy).test(context);
        } else {
            return false;
        }
    }

    // Deprecated but still need to implement it unless I want to define this class as abstract
    @Override
    public boolean test() {
        return false;
    }

    public static class Serializer implements IConditionSerializer<QuarkFlagCondition> {

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, QuarkFlagCondition value) {
            json.addProperty("flag", value.flag);
        }

        @Override
        public QuarkFlagCondition read(JsonObject json) {
            return new QuarkFlagCondition(json.getAsJsonPrimitive("flag").getAsString());
        }

        @Override
        public ResourceLocation getID() {
            return NAME;
        }
    }
}
