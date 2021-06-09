package com.nekomaster1000.infernalexp.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

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

public class SpawnrateManager {

    private static final Gson GSON_INSTANCE = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private static final Map<String, Map<String, SpawnInfo>> SPAWNRATES = new HashMap<>();

    // Set spawnCostPerEntity and maxSpawnCost to 0 to not change them
    private static final Map<String, Map<String, SpawnInfo>> configs = ImmutableMap.<String, Map<String, SpawnInfo>>builder()
        .put("basalt_giant", ImmutableMap.of(
            "default", new SpawnInfo(10, 1, 1, 0, 0),
            "minecraft:basalt_deltas", new SpawnInfo(30, 1, 1, 3.2D, 0.1D),
            "infernalexp:delta_shores", new SpawnInfo(4, 1, 1, 100.2D, 0.1D)
        ))
        .put("blackstone_dwarf", ImmutableMap.of(
            "default", new SpawnInfo(0, 0, 0, 0, 0)
        ))
        .put("blindsight", ImmutableMap.of(
            "default", new SpawnInfo(30, 1, 1, 0, 0),
            "infernalexp:glowstone_canyon", new SpawnInfo(30, 1, 1, 7.2D, 0.9D)
        ))
        .put("embody", ImmutableMap.of(
            "default", new SpawnInfo(60, 1, 5, 0, 0),
            "minecraft:soul_sand_valley", new SpawnInfo(120, 1, 3, 1.2D, 0.1D)
        ))
        .put("glowsilk_moth", ImmutableMap.of(
            "default", new SpawnInfo(5, 1, 1, 0, 0),
            "infernalexp:glowstone_canyon", new SpawnInfo(1, 1, 1, 4.2D, 0.1D),
            "minecraft:crimson_forest", new SpawnInfo(1, 1, 1, 8.2D, 0.1D),
            "minecraft:basalt_deltas", new SpawnInfo(1, 1, 1, 8.2D, 0.1D)
        ))
        .put("glowsquito", ImmutableMap.of(
            "default", new SpawnInfo(20, 1, 3, 0, 0),
            "infernalexp:glowstone_canyon", new SpawnInfo(80, 1, 10, 1.2D, 0.1D)
        ))
        .put("shroomloin", ImmutableMap.of(
            "default", new SpawnInfo(5, 1, 3, 0, 0),
            "minecraft:crimson_forest", new SpawnInfo(30, 1, 1, 16.2D, 0.1D)
        ))
        .put("voline", ImmutableMap.of(
            "default", new SpawnInfo(20, 1, 1, 0, 0),
            "minecraft:nether_wastes", new SpawnInfo(50, 1, 3, 1.2D, 0.1D),
            "minecraft:crimson_forest", new SpawnInfo(1, 1, 5,  8.2D, 0.1D)
        ))
        .put("warpbeetle", ImmutableMap.of(
            "default", new SpawnInfo(5, 1, 1, 0, 0),
            "minecraft:warped_forest", new SpawnInfo(50, 1, 3, 2.6D, 0.1D)
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
                JsonObject jsonObject = JSONUtils.fromJson(GSON_INSTANCE, reader, JsonObject.class);

                if (jsonObject != null) {
                    for (JsonElement entry : jsonObject.getAsJsonArray("biomes")) {

                        JsonObject spawnInfo = entry.getAsJsonObject().getAsJsonObject("spawn_info");

                        entitySpawns.put(entry.getAsJsonObject().get("biome").getAsString(),
                            new SpawnInfo(
                                spawnInfo.get("spawn_rate").getAsInt(),
                                spawnInfo.get("min_count").getAsInt(),
                                spawnInfo.get("max_count").getAsInt(),
                                Optional.ofNullable(spawnInfo.get("spawn_cost_per_entity")).map(JsonElement::getAsDouble).orElse(0.0D),
                                Optional.ofNullable(spawnInfo.get("max_spawn_cost")).map(JsonElement::getAsDouble).orElse(0.0D)
//                                        Optional.ofNullable(entry.getAsJsonObject().get("creature_spawn_probability")).map(JsonElement::getAsFloat).orElse(0.0F),
//                                        Optional.ofNullable(entry.getAsJsonObject().getAsJsonArray("spawn_info_changes")).map(changes ->
//                                            new HashMap<ResourceLocation, SpawnInfo>() {{
//                                                for (JsonElement change : changes) {
//                                                    JsonObject changeSpawnInfo = change.getAsJsonObject().getAsJsonObject("spawn_info");
//
//                                                    put(new ResourceLocation(change.getAsJsonObject().get("name").getAsString()),
//                                                        new SpawnInfo(
//                                                            Optional.ofNullable(changeSpawnInfo.get("spawnrate")).map(JsonElement::getAsInt).orElse(0),
//                                                            Optional.ofNullable(changeSpawnInfo.get("min_count")).map(JsonElement::getAsInt).orElse(0),
//                                                            Optional.ofNullable(changeSpawnInfo.get("max_count")).map(JsonElement::getAsInt).orElse(0),
//                                                            0.0F, null
//                                                        ));
//                                                }
//                                            }}
//                                        ).orElse(null)
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

    public static class SpawnInfo {

        private final int spawnRate;
        private final int minCount;
        private final int maxCount;

        private final Double spawnCostPerEntity;
        private final Double maxSpawnCost;

        public SpawnInfo(int spawnRate, int minCount, int maxCount, double spawnCostPerEntity, double maxSpawnCost) {
            this.spawnRate = spawnRate;
            this.minCount = minCount;
            this.maxCount = maxCount;

            if (spawnCostPerEntity == 0) {
                this.spawnCostPerEntity = null;
            } else {
                this.spawnCostPerEntity = spawnCostPerEntity;
            }

            if (maxSpawnCost == 0) {
                this.maxSpawnCost = null;
            } else {
                this.maxSpawnCost = maxSpawnCost;
            }
        }

        public int getSpawnRate() {
            return spawnRate;
        }

        public int getMinCount() {
            return minCount;
        }

        public int getMaxCount() {
            return maxCount;
        }

        public Double getSpawnCostPerEntity() {
            return spawnCostPerEntity;
        }

        public Double getMaxSpawnCost() {
            return maxSpawnCost;
        }
    }

    private static class SpawnBiomeEntry {

        private final String biome;
        private final SpawnInfo spawnInfo;

        public SpawnBiomeEntry(String biome, SpawnInfo spawnInfo) {
            this.biome = biome;
            this.spawnInfo = spawnInfo;
        }
    }
}
