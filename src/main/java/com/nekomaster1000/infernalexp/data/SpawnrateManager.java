package com.nekomaster1000.infernalexp.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpawnrateManager extends JsonReloadListener {

    private static final Gson GSON_INSTANCE = (new GsonBuilder()).create();
    private static final Map<String, Map<String, SpawnInfo>> SPAWNRATES = new HashMap<>();

    public SpawnrateManager() {
        super(GSON_INSTANCE, "spawnrates");
        loadResources();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        loadResources();
    }

    // This function uses our own resourceManager so we can load it before world load
    private void loadResources() {
        List<String> entities = new ArrayList<>();

        ForgeRegistries.ENTITIES.getKeys().forEach(resourceLocation -> {
            if (resourceLocation.getNamespace().equals(InfernalExpansion.MOD_ID) && InfernalExpansionConfig.MobSpawning.contains(resourceLocation.getPath())) {
                entities.add(resourceLocation.toString());
            }
        });

        entities.forEach(entity -> {
            ResourceLocation fileResourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, "spawnrates/" + entity.split(":")[1] + "_spawns.json");
            Map<String, SpawnInfo> entitySpawns = new HashMap<>();

            try {
                for (IResource iResource : InfernalExpansion.getDataResourceManager().getAllResources(fileResourceLocation)) {
                    try (Reader reader = new BufferedReader(new InputStreamReader(iResource.getInputStream(), StandardCharsets.UTF_8))) {
                        JsonObject jsonObject = JSONUtils.fromJson(GSON_INSTANCE, reader, JsonObject.class);

                        if (jsonObject != null) {
                            for (JsonElement entry : jsonObject.getAsJsonArray("biomes")) {

                                JsonObject spawnInfo = entry.getAsJsonObject().getAsJsonObject("spawn_info");

                                entitySpawns.put(entry.getAsJsonObject().get("biome").getAsString(),
                                    new SpawnInfo(
                                        spawnInfo.get("spawnrate").getAsInt(),
                                        spawnInfo.get("min_count").getAsInt(),
                                        spawnInfo.get("max_count").getAsInt(),
                                        Optional.ofNullable(entry.getAsJsonObject().get("creature_spawn_probability")).map(JsonElement::getAsFloat).orElse(0.0F),
                                        Optional.ofNullable(entry.getAsJsonObject().getAsJsonArray("spawn_info_changes")).map(changes ->
                                            new HashMap<ResourceLocation, SpawnInfo>() {{
                                                for (JsonElement change : changes) {
                                                    JsonObject changeSpawnInfo = change.getAsJsonObject().getAsJsonObject("spawn_info");

                                                    put(new ResourceLocation(change.getAsJsonObject().get("name").getAsString()),
                                                        new SpawnInfo(
                                                            Optional.ofNullable(changeSpawnInfo.get("spawnrate")).map(JsonElement::getAsInt).orElse(0),
                                                            Optional.ofNullable(changeSpawnInfo.get("min_count")).map(JsonElement::getAsInt).orElse(0),
                                                            Optional.ofNullable(changeSpawnInfo.get("max_count")).map(JsonElement::getAsInt).orElse(0),
                                                            0.0F, null
                                                        ));
                                                }
                                            }}
                                        ).orElse(null)
                                    )
                                );
                            }
                        }

                    } catch (RuntimeException | IOException exception) {
                        InfernalExpansion.LOGGER.error("Couldn't read spawn table list {} in data pack {}", fileResourceLocation, iResource.getPackName(), exception);

                    } finally {
                        IOUtils.closeQuietly(iResource);
                    }
                }
            } catch (IOException exception) {
                InfernalExpansion.LOGGER.error("Couldn't read spawn table from {}", fileResourceLocation, exception);
            }

            SPAWNRATES.put(entity, entitySpawns);
        });
    }

    public Map<String, Map<String, SpawnInfo>> getSpawnrates() {
        return SPAWNRATES;
    }

    public static class SpawnInfo {

        private final int spawnRate;
        private final int minCount;
        private final int maxCount;

        private final float creatureSpawnProbability;

        private final Map<ResourceLocation, SpawnInfo> changes;

        public SpawnInfo(int spawnRate, int minCount, int maxCount, float creatureSpawnProbability, @Nullable Map<ResourceLocation, SpawnInfo> changes) {
            this.spawnRate = spawnRate;
            this.minCount = minCount;
            this.maxCount = maxCount;
            this.creatureSpawnProbability = creatureSpawnProbability;
            this.changes = changes;
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

        public float getCreatureSpawnProbability() {
            return creatureSpawnProbability;
        }

        public Map<ResourceLocation, SpawnInfo> getChanges() {
            return changes;
        }
    }
}
