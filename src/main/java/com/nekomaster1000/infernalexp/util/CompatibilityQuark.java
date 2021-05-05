package com.nekomaster1000.infernalexp.util;

import com.google.gson.JsonObject;

import com.nekomaster1000.infernalexp.InfernalExpansion;

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
