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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import net.minecraft.ResourceLocationException;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

public class SpawnrateManager {

    private static final Gson GSON_INSTANCE = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).registerTypeAdapter(Double.class, new TypeAdapter<Double>() {
        @Override
        public void write(JsonWriter out, Double value) throws IOException {
            if (value == null || value.equals(0.0D)) {
                out.nullValue();
                return;
            }

            out.value(value.doubleValue());
        }

        @Override
        public Double read(JsonReader in) throws IOException {
            return in.nextDouble();
        }
    }).create();
    private static final Map<String, Map<String, SpawnInfo>> SPAWNRATES = new HashMap<>();

    private static final Map<String, Map<String, SpawnInfo>> configs = ImmutableMap.<String, Map<String, SpawnInfo>>builder()
        .put("basalt_giant", ImmutableMap.of(
            "default", new SpawnInfo(5, 1, 1, 0, 0),
            "minecraft:basalt_deltas", new SpawnInfo(1, 1, 1, 2.0D, 0.1D),
            "infernalexp:delta_shores", new SpawnInfo(1, 1, 1, 0.5D, 1.5D)
        ))
        .put("blackstone_dwarf", ImmutableMap.of(
            "default", new SpawnInfo(0, 0, 0, 0, 0)
        ))
        .put("blindsight", ImmutableMap.of(
            "default", new SpawnInfo(30, 1, 1, 0, 0),
            "infernalexp:glowstone_canyon", new SpawnInfo(1, 1, 1, 1.5D, 0.1D)
        ))
        .put("embody", ImmutableMap.of(
            "default", new SpawnInfo(50, 1, 5, 0, 0),
            "minecraft:soul_sand_valley", new SpawnInfo(30, 1, 3, 0, 0)
        ))
        .put("glowsilk_moth", ImmutableMap.of(
            "default", new SpawnInfo(5, 1, 1, 0, 0),
            "infernalexp:glowstone_canyon", new SpawnInfo(1, 1, 1, 3.0D, 0.1D),
            "minecraft:crimson_forest", new SpawnInfo(1, 1, 1, 1.0D, 0.4D),
            "minecraft:basalt_deltas", new SpawnInfo(1, 1, 1, 1.0D, 0.4D)
        ))
        .put("glowsquito", ImmutableMap.of(
            "default", new SpawnInfo(20, 1, 3, 0, 0),
            "infernalexp:glowstone_canyon", new SpawnInfo(3, 1, 7, 5.0D, 0.1D)
        ))
        .put("shroomloin", ImmutableMap.of(
            "default", new SpawnInfo(5, 1, 1, 0, 0),
            "minecraft:crimson_forest", new SpawnInfo(1, 1, 1, 2.0D, 0.4D)
        ))
        .put("voline", ImmutableMap.of(
            "default", new SpawnInfo(50, 1, 3, 0, 0),
            "minecraft:nether_wastes", new SpawnInfo(45, 3, 8, 0, 0),
            "minecraft:crimson_forest", new SpawnInfo(2, 1, 4, 0, 0)
        ))
        .put("warpbeetle", ImmutableMap.of(
            "default", new SpawnInfo(1, 1, 1, 0, 0),
            "minecraft:warped_forest", new SpawnInfo(1, 1, 1, 0.5D, 1.5D)
        ))
        .build();


    public SpawnrateManager() {
        loadResources();
    }

    private static void createResource(String entity) {
        if (!configs.containsKey(entity)) {
            InfernalExpansion.LOGGER.error("{} doesn't exist in the configs map", entity);
        }

        Map<String, SpawnInfo> configData = configs.get(entity);
        List<SpawnBiomeEntry> formattedConfigData = new ArrayList<>();

        configData.forEach((biome, spawnInfo) -> formattedConfigData.add(new SpawnBiomeEntry(biome, spawnInfo)));

        JsonObject config = new JsonObject();
        JsonElement configInner = GSON_INSTANCE.toJsonTree(formattedConfigData);
        config.add("biomes", configInner);


        try {
            String json = GSON_INSTANCE.toJson(config);
            FileWriter writer = new FileWriter(FMLPaths.CONFIGDIR.get() + "/infernalexp/spawnrates/" + entity + "_spawns.json");

            writer.write(json);
            writer.close();
        } catch (IOException exception) {
            InfernalExpansion.LOGGER.error("There was an error with creating the file {} /infernalexp/spawnrates/{}_spawns.json Exception: {}", FMLPaths.CONFIGDIR.get(), entity, exception);
        }
    }

    public static void createResources() {
        if (!new File(FMLPaths.CONFIGDIR.get() + "/infernalexp/spawnrates/").exists()) {
            try {
                Files.createDirectories(Paths.get(FMLPaths.CONFIGDIR.get() + "/infernalexp/spawnrates"));
            } catch (IOException exception) {
                InfernalExpansion.LOGGER.error("There was an error with creating the directory {} /infernalexp/spawnrates Exception: {}", FMLPaths.CONFIGDIR.get(), exception);
            }
        }

        for (String entity : configs.keySet()) {
            File file = new File(FMLPaths.CONFIGDIR.get() + "/infernalexp/spawnrates/" + entity + "_spawns.json");

            if (!file.exists()) {
                createResource(entity);
            }
        }
    }

    public void loadResources() {
        createResources();

        File spawnrateDirectory = new File(FMLPaths.CONFIGDIR.get().toString() + "/infernalexp/spawnrates");

        for (String entity : configs.keySet()) {
            String resourceLocation = InfernalExpansion.MOD_ID + ":" + entity;

            if (!ForgeRegistries.ENTITIES.containsKey(new ResourceLocation(resourceLocation)) || !InfernalExpansionConfig.MobSpawning.contains(entity)) {
                throw new ResourceLocationException(entity.split(":")[1] + " does not exist in the registry or is not a naturally spawning mob from Infernal Expansion");
            }

            Map<String, SpawnInfo> entitySpawns = new HashMap<>();

            try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(spawnrateDirectory + "/" + entity + "_spawns.json"), StandardCharsets.UTF_8))) {
                JsonObject jsonObject = GsonHelper.fromJson(GSON_INSTANCE, reader, JsonObject.class);

                if (jsonObject != null) {
                    for (JsonElement entry : jsonObject.getAsJsonArray("biomes")) {

                        JsonObject spawnInfo = entry.getAsJsonObject().getAsJsonObject("spawn_info");

                        entitySpawns.put(entry.getAsJsonObject().get("biome").getAsString(),
                            new SpawnInfo(
                                spawnInfo.get("spawn_rate").getAsInt(),
                                spawnInfo.get("min_count").getAsInt(),
                                spawnInfo.get("max_count").getAsInt(),
                                Optional.ofNullable(spawnInfo.get("energy_budget")).map(JsonElement::getAsDouble).orElse(0.0D),
                                Optional.ofNullable(spawnInfo.get("charge")).map(JsonElement::getAsDouble).orElse(0.0D)
                            )
                        );
                    }
                }

                SPAWNRATES.put(resourceLocation, entitySpawns);

            } catch (IOException exception) {
                InfernalExpansion.LOGGER.error("Couldn't read spawn table list {}, Exception: {}", entity + "_spawns.json", exception);
            }
        }
    }

    public Map<String, Map<String, SpawnInfo>> getSpawnrates() {
        return SPAWNRATES;
    }

    public record SpawnInfo(int spawnRate, int minCount, int maxCount, double energyBudget, double charge) {}

    private record SpawnBiomeEntry(String biome, SpawnInfo spawnInfo) {}
}
