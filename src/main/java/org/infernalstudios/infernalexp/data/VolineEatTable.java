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

package org.infernalstudios.infernalexp.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.infernalstudios.infernalexp.InfernalExpansion;

import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.world.item.Item;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class VolineEatTable extends SimpleJsonResourceReloadListener {

    private static final Gson GSON_INSTANCE = (new GsonBuilder()).create();
    private static final Map<Item, Map<Item, Integer>> VOLINE_EAT_TABLE = new HashMap<>();

    public VolineEatTable() {
        super(GSON_INSTANCE, "loot_tables/gameplay");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, "loot_tables/gameplay/voline_eat_table.json");

        try {
            Optional<Resource> iResource = resourceManagerIn.getResource(resourceLocation);
            try (Reader reader = new InputStreamReader(iResource.get().open())) {
                JsonObject jsonObject = GsonHelper.fromJson(GSON_INSTANCE, reader, JsonObject.class);

                if (jsonObject != null) {
                    for (JsonElement entry : jsonObject.getAsJsonArray("entries")) {

                        VOLINE_EAT_TABLE.put(ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry.getAsJsonObject().get("accepted_item").getAsString())),
                            new HashMap<Item, Integer>() {{
                                for (JsonElement item : entry.getAsJsonObject().getAsJsonArray("returned_items")) {
                                    put(ForgeRegistries.ITEMS.getValue(new ResourceLocation(item.getAsJsonObject().get("item").getAsString())),
                                        item.getAsJsonObject().get("amount").getAsInt());
                                }
                            }}
                        );
                    }
                }

            } catch (RuntimeException | IOException exception) {
                InfernalExpansion.LOGGER.error("Couldn't read voline eat table list {} in data pack {}", resourceLocation, iResource.get().sourcePackId(), exception);

            }
        } catch (NoSuchElementException exception) {
            InfernalExpansion.LOGGER.error("Couldn't read voline eat table from {}", resourceLocation, exception);
        }

    }

    public Map<Item, Map<Item, Integer>> getVolineEatTable() {
        return VOLINE_EAT_TABLE;
    }

}
