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

package org.infernalstudios.infernalexp.data.providers.lang;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.init.IEBiomes;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.mixin.common.CreativeModeTabAccessor;
import org.infernalstudios.infernalexp.mixin.common.LanguageProviderAccessor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public abstract class IELanguageProvider extends LanguageProvider {

    private final Map<String, String> baseData = new TreeMap<>();

    public IELanguageProvider(DataGenerator generator) {
        super(generator, InfernalExpansion.MOD_ID, "en_us");
    }

    protected IELanguageProvider(DataGenerator generator, String locale) {
        super(generator, InfernalExpansion.MOD_ID, locale);
    }

    /**
     * All LanguageProviders must have entries for all translations even if they are the same as the base language (en_us).
     * {@link #run(net.minecraft.data.HashCache)} will throw an error if any LanguageProvider is missing a translation that the base language has.
     * While this is tedious it ensures that every LanguageProvider has comprehensive list of all the translations, making it easy to see what is yet to be translated
     */
    @Override
    protected abstract void addTranslations();

    @Override
    public void run(@NotNull HashCache cache) throws IOException {
        LanguageProviderAccessor accessor = (LanguageProviderAccessor) this;
        Map<String, String> data = accessor.getData();

        addBaseTranslations();
        baseData.putAll(data);
        data.clear();

        addTranslations();
        boolean error = false;

        for (String key : baseData.keySet()) {
            if (!data.containsKey(key)) {
                InfernalExpansion.LOGGER.error("Missing translation for key: " + key + " in locale: " + accessor.getLocale());
                error = true;
            }
        }

        if (error)
            throw new IllegalStateException("Missing translations for " + accessor.getLocale() + ", see log for details");

        // Remove all translations that are the same as the base language
        data.values().removeIf(baseData::containsValue);

        if (!data.isEmpty())
            saveFile(cache, data, accessor.getGenerator().getOutputFolder().resolve("assets/" + accessor.getModId() + "/lang/" + accessor.getLocale() + ".json"));
    }

    /**
     * Replace default save method because I don't want to escape special unicode characters in the generated lang files
     */
    private void saveFile(HashCache cache, Map<String, String> lang, Path target) throws IOException {
        String data = ((LanguageProviderAccessor) this).getGson().toJson(lang);
        String hash = DataProvider.SHA1.hashUnencodedChars(data).toString();

        if (!Objects.equals(cache.getHash(target), hash) || !Files.exists(target)) {
            Files.createDirectories(target.getParent());

            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
                bufferedwriter.write(data);
            }
        }

        cache.putNew(target, hash);
    }
    protected void addBaseTranslations() {
        add(InfernalExpansion.TAB, "Infernal Expansion");

        // Glowstone
        add(Blocks.GLOWSTONE, "Glowstone");
        add(IEBlocks.DIMSTONE.get(), "Dimstone");
        add(IEBlocks.DULLSTONE.get(), "Dullstone");
        add(IEBlocks.SMOOTH_GLOWSTONE.get(), "Smooth Glowstone");
        add(IEBlocks.SMOOTH_DIMSTONE.get(), "Smooth Dimstone");
        add(IEBlocks.SMOOTH_DULLSTONE.get(), "Smooth Dullstone");
        add(IEBlocks.GLOWSTONE_BRICKS.get(), "Glowstone Bricks");
        add(IEBlocks.DIMSTONE_BRICKS.get(), "Dimstone Bricks");
        add(IEBlocks.DULLSTONE_BRICKS.get(), "Dullstone Bricks");
        add(IEBlocks.CRACKED_GLOWSTONE_BRICKS.get(), "Cracked Glowstone Bricks");
        add(IEBlocks.CRACKED_DIMSTONE_BRICKS.get(), "Cracked Dimstone Bricks");
        add(IEBlocks.CRACKED_DULLSTONE_BRICKS.get(), "Cracked Dullstone Bricks");
        add(IEBlocks.CHISELED_GLOWSTONE_BRICKS.get(), "Chiseled Glowstone Bricks");
        add(IEBlocks.CHISELED_DIMSTONE_BRICKS.get(), "Chiseled Dimstone Bricks");
        add(IEBlocks.CHISELED_DULLSTONE_BRICKS.get(), "Chiseled Dullstone Bricks");
        add(IEBlocks.SMOOTH_GLOWSTONE_SLAB.get(), "Smooth Glowstone Slab");
        add(IEBlocks.SMOOTH_GLOWSTONE_VERTICAL_SLAB.get(), "Smooth Glowstone Vertical Slab");
        add(IEBlocks.SMOOTH_GLOWSTONE_STAIRS.get(), "Smooth Glowstone Stairs");
        add(IEBlocks.SMOOTH_GLOWSTONE_BUTTON.get(), "Smooth Glowstone Button");
        add(IEBlocks.SMOOTH_GLOWSTONE_PRESSURE_PLATE.get(), "Smooth Glowstone Pressure Plate");
        add(IEBlocks.SMOOTH_DIMSTONE_SLAB.get(), "Smooth Dimstone Slab");
        add(IEBlocks.SMOOTH_DIMSTONE_VERTICAL_SLAB.get(), "Smooth Dimstone Vertical Slab");
        add(IEBlocks.SMOOTH_DIMSTONE_STAIRS.get(), "Smooth Dimstone Stairs");
        add(IEBlocks.SMOOTH_DIMSTONE_BUTTON.get(), "Smooth Dimstone Button");
        add(IEBlocks.SMOOTH_DULLSTONE_SLAB.get(), "Smooth Dullstone Slab");
        add(IEBlocks.SMOOTH_DULLSTONE_VERTICAL_SLAB.get(), "Smooth Dullstone Vertical Slab");
        add(IEBlocks.SMOOTH_DULLSTONE_STAIRS.get(), "Smooth Dullstone Stairs");
        add(IEBlocks.SMOOTH_DULLSTONE_BUTTON.get(), "Smooth Dullstone Button");
        add(IEBlocks.GLOWSTONE_BRICK_SLAB.get(), "Glowstone Brick Slab");
        add(IEBlocks.GLOWSTONE_BRICK_VERTICAL_SLAB.get(), "Glowstone Brick Vertical Slab");
        add(IEBlocks.GLOWSTONE_BRICK_STAIRS.get(), "Glowstone Brick Stairs");
        add(IEBlocks.GLOWSTONE_BRICK_WALL.get(), "Glowstone Brick Wall");
        add(IEBlocks.DIMSTONE_BRICK_SLAB.get(), "Dimstone Brick Slab");
        add(IEBlocks.DIMSTONE_BRICK_VERTICAL_SLAB.get(), "Dimstone Brick Vertical Slab");
        add(IEBlocks.DIMSTONE_BRICK_STAIRS.get(), "Dimstone Brick Stairs");
        add(IEBlocks.DIMSTONE_BRICK_WALL.get(), "Dimstone Brick Wall");
        add(IEBlocks.DULLSTONE_BRICK_SLAB.get(), "Dullstone Brick Slab");
        add(IEBlocks.DULLSTONE_BRICK_VERTICAL_SLAB.get(), "Dullstone Brick Vertical Slab");
        add(IEBlocks.DULLSTONE_BRICK_STAIRS.get(), "Dullstone Brick Stairs");
        add(IEBlocks.DULLSTONE_BRICK_WALL.get(), "Dullstone Brick Wall");

        // Luminous
        add(IEBlocks.LUMINOUS_WART_BLOCK.get(), "Luminous Wart Block");
        add(IEBlocks.LUMINOUS_STEM.get(), "Luminous Stem");
        add(IEBlocks.STRIPPED_LUMINOUS_STEM.get(), "Stripped Luminous Stem");
        add(IEBlocks.LUMINOUS_HYPHAE.get(), "Luminous Hyphae");
        add(IEBlocks.STRIPPED_LUMINOUS_HYPHAE.get(), "Stripped Luminous Hyphae");

        // Glowdust
        add(IEBlocks.GLOWDUST.get(), "Shimmer Sheet");
        add(IEBlocks.GLOWDUST_SAND.get(), "Shimmer Sand");
        add(IEBlocks.GLOWDUST_STONE.get(), "Shimmer Stone");
        add(IEBlocks.GLOWDUST_STONE_SLAB.get(), "Shimmer Stone Slab");
        add(IEBlocks.GLOWDUST_STONE_VERTICAL_SLAB.get(), "Shimmer Stone Vertical Slab");
        add(IEBlocks.GLOWDUST_STONE_STAIRS.get(), "Shimmer Stone Stairs");
        add(IEBlocks.GLOWDUST_STONE_WALL.get(), "Shimmer Stone Wall");
        add(IEBlocks.TRAPPED_GLOWDUST_SAND.get(), "Glimmer Gravel");
        add(IEBlocks.GLOWDUST_STONE_BRICKS.get(), "Shimmer Stone Bricks");
        add(IEBlocks.GLOWDUST_STONE_BRICK_SLAB.get(), "Shimmer Stone Brick Slab");
        add(IEBlocks.GLOWDUST_STONE_BRICK_VERTICAL_SLAB.get(), "Shimmer Stone Brick Vertical Slab");
        add(IEBlocks.GLOWDUST_STONE_BRICK_STAIRS.get(), "Shimmer Stone Brick Stairs");
        add(IEBlocks.GLOWDUST_STONE_BRICK_WALL.get(), "Shimmer Stone Brick Wall");
        add(IEBlocks.CRACKED_GLOWDUST_STONE_BRICKS.get(), "Cracked Shimmer Stone Bricks");
        add(IEBlocks.CHISELED_GLOWDUST_STONE_BRICKS.get(), "Chiseled Shimmer Stone Bricks");

        // Basalt
        add(IEBlocks.CRUMBLING_BLACKSTONE.get(), "Crumbling Blackstone");
        add(IEBlocks.SILT.get(), "Basalt Silt");
        add(IEBlocks.RUBBLE.get(), "Blackstone Rubble");
        add(IEBlocks.BASALT_COBBLED.get(), "Cobbled Basalt");
        add(IEBlocks.BASALT_COBBLED_SLAB.get(), "Cobbled Basalt Slab");
        add(IEBlocks.BASALT_COBBLED_VERTICAL_SLAB.get(), "Cobbled Basalt Vertical Slab");
        add(IEBlocks.BASALT_SLAB.get(), "Basalt Slab");
        add(IEBlocks.BASALT_VERTICAL_SLAB.get(), "Basalt Vertical Slab");
        add(IEBlocks.BASALT_STAIRS.get(), "Basalt Stairs");
        add(IEBlocks.BASALT_WALL.get(), "Basalt Wall");
        add(IEBlocks.BASALT_BUTTON.get(), "Basalt Button");
        add(IEBlocks.POLISHED_BASALT_PRESSURE_PLATE.get(), "Polished Basalt Pressure Plate");
        add(IEBlocks.POLISHED_BASALT_SLAB.get(), "Polished Basalt Slab");
        add(IEBlocks.POLISHED_BASALT_VERTICAL_SLAB.get(), "Polished Basalt Vertical Slab");
        add(IEBlocks.POLISHED_BASALT_TILES.get(), "Polished Basalt Tiles");
        add(IEBlocks.POLISHED_BASALT_TILES_SLAB.get(), "Polished Basalt Tiles Slab");
        add(IEBlocks.POLISHED_BASALT_TILES_VERTICAL_SLAB.get(), "Polished Basalt Tiles Vertical Slab");
        add(IEBlocks.BASALT_BRICKS.get(), "Basalt Bricks");
        add(IEBlocks.BASALT_BRICK_SLAB.get(), "Basalt Brick Slab");
        add(IEBlocks.BASALT_BRICK_VERTICAL_SLAB.get(), "Basalt Brick Vertical Slab");
        add(IEBlocks.BASALT_BRICK_STAIRS.get(), "Basalt Brick Stairs");
        add(IEBlocks.BASALT_BRICK_WALL.get(), "Basalt Brick Wall");
        add(IEBlocks.CRACKED_BASALT_BRICKS.get(), "Cracked Basalt Bricks");
        add(IEBlocks.CHISELED_BASALT_BRICKS.get(), "Chiseled Basalt Bricks");
        add(IEBlocks.MAGMATIC_CHISELED_BASALT_BRICKS.get(), "Magmatic Chiseled Basalt Bricks");
        add(IEBlocks.BASALT_IRON_ORE.get(), "Basalt Iron Ore");
        add(IEBlocks.BASALTIC_MAGMA.get(), "Basaltic Magma");

        // Soul Sand and Soil
        add(IEBlocks.SOUL_SAND_SLAB.get(), "Soul Sand Slab");
        add(IEBlocks.SOUL_SAND_VERTICAL_SLAB.get(), "Soul Sand Vertical Slab");
        add(IEBlocks.SOUL_SAND_STAIRS.get(), "Soul Sand Stairs");
        add(IEBlocks.SOUL_SOIL_SLAB.get(), "Soul Soil Slab");
        add(IEBlocks.SOUL_SOIL_VERTICAL_SLAB.get(), "Soul Soil Vertical Slab");
        add(IEBlocks.SOUL_SOIL_STAIRS.get(), "Soul Soil Stairs");
        add(IEBlocks.SOUL_STONE.get(), "Soul Stone");
        add(IEBlocks.SOUL_STONE_SLAB.get(), "Soul Stone Slab");
        add(IEBlocks.SOUL_STONE_VERTICAL_SLAB.get(), "Soul Stone Vertical Slab");
        add(IEBlocks.SOUL_STONE_STAIRS.get(), "Soul Stone Stairs");
        add(IEBlocks.SOUL_STONE_WALL.get(), "Soul Stone Wall");
        add(IEBlocks.SOUL_STONE_BRICKS.get(), "Soul Stone Bricks");
        add(IEBlocks.SOUL_STONE_BRICK_SLAB.get(), "Soul Stone Brick Slab");
        add(IEBlocks.SOUL_STONE_BRICK_VERTICAL_SLAB.get(), "Soul Stone Brick Vertical Slab");
        add(IEBlocks.SOUL_STONE_BRICK_STAIRS.get(), "Soul Stone Brick Stairs");
        add(IEBlocks.SOUL_STONE_BRICK_WALL.get(), "Soul Stone Brick Wall");
        add(IEBlocks.CRACKED_SOUL_STONE_BRICKS.get(), "Cracked Soul Stone Bricks");
        add(IEBlocks.CHISELED_SOUL_STONE_BRICKS.get(), "Chiseled Soul Stone Bricks");
        add(IEBlocks.CHARGED_CHISELED_SOUL_STONE_BRICKS.get(), "Charged Chiseled Soul Stone Bricks");
        add(IEBlocks.SOUL_SLATE.get(), "Soul Slate");
        add(IEBlocks.SOUL_SLATE_SLAB.get(), "Soul Slate Slab");
        add(IEBlocks.SOUL_SLATE_VERTICAL_SLAB.get(), "Soul Slate Vertical Slab");
        add(IEBlocks.SOUL_SLATE_STAIRS.get(), "Soul Slate Stairs");
        // Soul Slate Wall doesn't exist. Keeping the lang entry in case we want to add it later.
        add("block." + InfernalExpansion.MOD_ID + ".soul_slate_wall", "Soul Slate Wall");
        add(IEBlocks.SOUL_SLATE_BUTTON.get(), "Soul Slate Button");
        add(IEBlocks.SOUL_SLATE_PRESSURE_PLATE.get(), "Soul Slate Pressure Plate");
        add(IEBlocks.SOUL_SLATE_BRICKS.get(), "Soul Slate Bricks");
        add(IEBlocks.SOUL_SLATE_BRICK_SLAB.get(), "Soul Slate Brick Slab");
        add(IEBlocks.SOUL_SLATE_BRICK_VERTICAL_SLAB.get(), "Soul Slate Brick Vertical Slab");
        add(IEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), "Soul Slate Brick Stairs");
        add(IEBlocks.SOUL_SLATE_BRICK_WALL.get(), "Soul Slate Brick Wall");
        add(IEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), "Cracked Soul Slate Bricks");
        add(IEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), "Chiseled Soul Slate Bricks");
        add(IEBlocks.CHARGED_CHISELED_SOUL_SLATE_BRICKS.get(), "Charged Chiseled Soul Slate Bricks");

        // Misc Blocks
        add(Blocks.NETHER_SPROUTS, "Warped Sprouts");
        add(Blocks.NETHER_WART_BLOCK, "Crimson Wart Block");
        add(IEBlocks.CRIMSON_FUNGUS_CAP.get(), "Crimson Fungus Cap");
        add(IEBlocks.WARPED_FUNGUS_CAP.get(), "Warped Fungus Cap");
        add(IEBlocks.LUMINOUS_FUNGUS_CAP.get(), "Luminous Fungus Cap");
        add(IEBlocks.SHROOMLIGHT_FUNGUS.get(), "Shroomlight Tear");
        add(IEBlocks.BURIED_BONE.get(), "Buried Bone");
        add(IEBlocks.PLANTED_QUARTZ.get(), "Planted Quartz");
        add(IEBlocks.CRIMSON_NYLIUM_PATH.get(), "Crimson Nylium Path");
        add(IEBlocks.WARPED_NYLIUM_PATH.get(), "Warped Nylium Path");
        add(IEBlocks.SOUL_SOIL_PATH.get(), "Soul Soil Path");
        add(IEBlocks.GLOW_LANTERN.get(), "Glowlight Lantern");
        add(IEBlocks.GLOW_TORCH.get(), "Glowlight Torch");
        add(IEBlocks.GLOW_CAMPFIRE.get(), "Glowlight Campfire");
        add(IEBlocks.GLOW_FIRE.get(), "Glow Fire");
        add(IEBlocks.GLOW_GLASS.get(), "Glowlight Glass");
        add(IEBlocks.GLOW_GLASS_PANE.get(), "Glowlight Glass Pane");
        add(IEBlocks.QUARTZ_GLASS.get(), "Quartz Glass");
        add(IEBlocks.QUARTZ_GLASS_PANE.get(), "Quartz Glass Pane");
        add(IEBlocks.LUMINOUS_FUNGUS.get(), "Luminous Fungus");
        add(IEBlocks.DULLTHORNS.get(), "Dullthorns");
        add(IEBlocks.DULLTHORNS_BLOCK.get(), "Dullthorns Block");
        add(IEBlocks.GLOWSILK_COCOON.get(), "Glowsilk Cocoon");
        add(IEBlocks.CRIMSON_NYLIUM_CARPET.get(), "Crimson Nylium Carpet");
        add(IEBlocks.WARPED_NYLIUM_CARPET.get(), "Warped Nylium Carpet");

        // Items
        add(IEItems.GLOWCOAL.get(), "Glowcoke");
        add(IEItems.DULLROCKS.get(), "Dullrocks");
        add(IEItems.BLINDSIGHT_TONGUE.get(), "Blindsight Tongue");
        add(IEItems.MOTH_DUST.get(), "Moth Dust");
        add(IEItems.MOLTEN_GOLD_CLUSTER.get(), "Molten Gold Cluster");
        add(IEItems.GLOWSILK.get(), "Glowsilk");
        add(IEItems.SOUL_SALT_CLUMP.get(), "Soul Salt Clump");
        add(IEItems.INFERNAL_PAINTING.get(), "Infernal Painting");
        add(IEItems.STRIDER_BUCKET.get(), "Bucketed Strider");
        add(IEItems.MAGMA_CUBE_BUCKET.get(), "Bucketed Magma Cube");
        add(IEItems.VOLINE_BUCKET.get(), "Bucketed Voline");
        add(IEItems.BLINDSIGHT_TONGUE_STEW.get(), "Fungus & Tongue Stew");
        add(IEItems.CURED_JERKY.get(), "Cured Jerky");
        add(IEItems.SPIRIT_EYE.get(), "Spirit Eye");
        add(IEItems.RAW_HOGCHOP.get(), "Raw Hogchop");
        add(IEItems.COOKED_HOGCHOP.get(), "Cooked Hogchop");
        add(IEItems.ASCUS_BOMB.get(), "Ascus Bomb");
        add("item." + InfernalExpansion.MOD_ID + ".frostbitten_sword", "Frostbitten Netherite Sword");
        add("item." + InfernalExpansion.MOD_ID + ".frostbitten_pickaxe", "Frostbitten Netherite Pickaxe");
        add("item." + InfernalExpansion.MOD_ID + ".frostbitten_axe", "Frostbitten Netherite Axe");
        add("item." + InfernalExpansion.MOD_ID + ".frostbitten_shovel", "Frostbitten Netherite Shovel");
        add("item." + InfernalExpansion.MOD_ID + ".frostbitten_hoe", "Frostbitten Netherite Hoe");
        add(IEItems.GLOWSILK_BOW.get(), "Glowsilk Bow");
        add(IEItems.BLINDSIGHT_TONGUE_WHIP.get(), "Blindsight Tongue Whip");
        add(IEItems.KINETIC_TONGUE_WHIP.get(), "Kinetic Tongue Whip");

        // Spawn eggs
        add(IEItems.VOLINE_SPAWN_EGG.get(), "Voline Spawn Egg");
        add(IEItems.SHROOMLOIN_SPAWN_EGG.get(), "Shroomloin Spawn Egg");
        add(IEItems.WARPBEETLE_SPAWN_EGG.get(), "Warpbeetle Spawn Egg");
        add(IEItems.EMBODY_SPAWN_EGG.get(), "Embody Spawn Egg");
        add(IEItems.BASALT_GIANT_SPAWN_EGG.get(), "Basalt Giant Spawn Egg");
        add(IEItems.BLACKSTONE_DWARF_SPAWN_EGG.get(), "Blackstone Dwarf Spawn Egg");
        add(IEItems.GLOWSQUITO_SPAWN_EGG.get(), "Glowsquito Spawn Egg");
        add(IEItems.BLINDSIGHT_SPAWN_EGG.get(), "Blindsight Spawn Egg");
        add(IEItems.GLOWSILK_MOTH_SPAWN_EGG.get(), "Glowsilk Moth Spawn Egg");
        // Leaving around for even though they don't exist
        add("item." + InfernalExpansion.MOD_ID + ".cerobeetle_spawn_egg", "Cerobeetle Spawn Egg");
        add("item." + InfernalExpansion.MOD_ID + ".pyrno_spawn_egg", "Pyrno Spawn Egg");

        // Music discs
        addMusicDisc(IEItems.MUSIC_DISC_SOUL_SPUNK.get(), "Music Disc", "LudoCrypt - Soul Spunk");
        addMusicDisc(IEItems.MUSIC_DISC_FLUSH.get(), "Music Disc", "LudoCrypt - flush");

        // Entities
        add(IEEntityTypes.VOLINE.get(), "Voline");
        add(IEEntityTypes.SHROOMLOIN.get(), "Shroomloin");
        add(IEEntityTypes.WARPBEETLE.get(), "Warpbeetle");
        add(IEEntityTypes.EMBODY.get(), "Embody");
        add(IEEntityTypes.BASALT_GIANT.get(), "Basalt Giant");
        add(IEEntityTypes.BLACKSTONE_DWARF.get(), "Blackstone Dwarf");
        add(IEEntityTypes.GLOWSQUITO.get(), "Glowsquito");
        add(IEEntityTypes.BLINDSIGHT.get(), "Blindsight");
        add(IEEntityTypes.GLOWSILK_MOTH.get(), "Glowsilk Moth");
        add(IEEntityTypes.ASCUS_BOMB.get(), "Ascus Bomb");
        add(IEEntityTypes.THROWABLE_MAGMA_CREAM.get(), "Magma Cream");
        add(IEEntityTypes.THROWABLE_FIRE_CHARGE.get(), "Fire Charge");
        add(IEEntityTypes.THROWABLE_BRICK.get(), "Brick");
        add(IEEntityTypes.THROWABLE_NETHER_BRICK.get(), "Nether Brick");
        add(IEEntityTypes.INFERNAL_PAINTING.get(), "Infernal Painting");
        add("entity." + InfernalExpansion.MOD_ID + ".pyrno", "Pyrno");

        // Subtitles
        addEntitySubtitles(IEEntityTypes.VOLINE.get(), "Voline chatters", "Voline hurts", "Voline dies");
        addEntitySubtitles(IEEntityTypes.SHROOMLOIN.get(), "Shroomloin hisses", "Shroomloin hurts", "Shroomloin dies");
        addEntitySubtitles(IEEntityTypes.BASALT_GIANT.get(), "Basalt Giant grunts", "Basalt Giant hurts", "Basalt Giant dies");
        addEntitySubtitles(IEEntityTypes.BLACKSTONE_DWARF.get(), "Blackstone Dwarf grunts", "Blackstone Dwarf hurts", "Blackstone Dwarf dies");
        addEntitySubtitles(IEEntityTypes.BLINDSIGHT.get(), "Blindsight clicks tongue", "Blindsight hurts", "Blindsight dies");
        add("subtitles.entity." + ForgeRegistries.ENTITIES.getKey(IEEntityTypes.BLINDSIGHT.get()).getPath() + ".leap", "Blindsight leaps");
        addEntitySubtitles(IEEntityTypes.WARPBEETLE.get(), "Warpbeetle squeaks", "Warpbeetle hurts", "Warpbeetle dies");
        addEntitySubtitles(IEEntityTypes.EMBODY.get(), "Embody hisses", "Embody hurts", "Embody dies");
        addEntitySubtitles(IEEntityTypes.GLOWSILK_MOTH.get(), "Glowsilk Moth shimmers", "Glowsilk Moth hurts", "Glowsilk Moth dies");
        addEntitySubtitles(IEEntityTypes.GLOWSQUITO.get(), "Glowsquito buzzes", "Glowsquito hurts", "Glowsquito dies");

        // Biomes
        add(IEBiomes.GLOWSTONE_CANYON, "Glowstone Canyon");
        add("biome." + InfernalExpansion.MOD_ID + ".delta_shores", "Delta Shores");

        // Effects
        addEffect(IEEffects.LUMINOUS.get(), "Luminance");
        addEffect(IEEffects.INFECTION.get(), "Infection");

        // World Type
        add("generator." + InfernalExpansion.MOD_ID + ".compat_world_type", "IE Compatibility");

        // Config
        addConfig("button.mobInteractions", "Mob Interactions...");
        addConfig("button.mobSpawning", "Mob Spawning...");
        addConfig("button.miscellaneous", "Miscellaneous...");
        addConfig("button.worldGeneration", "World Generation...");
        addConfig("button.clientConfig", "Client Config...");
        addConfig("title", "Infernal Expansion Config");
        addConfig("title.mobInteractions", "Mob Interactions");
        addConfig("title.mob_spawning", "Mob Spawning");
        addConfig("title.miscellaneous", "Miscellaneous");
        addConfig("title.worldGeneration", "World Generation");
        addConfig("title.clientConfig", "Client Config");
        addConfig("subtitle.spawnable_biomes", "What biomes should mobs spawn in?");
        addConfig("option.piglinFearWarpbeetle", "Piglins fear Warpbeetles");
        addConfig("option.piglinFearEmbody", "Piglins fear Embodies");
        addConfig("option.piglinFearDwarf", "Piglins fear Blackstone Dwarves");
        addConfig("option.hoglinFearWarpbeetle", "Hoglins fear Warpbeetles");
        addConfig("option.hoglinFearEmbody", "Hoglins fear Embodies");
        addConfig("option.spiderAttackWarpbeetle", "Spiders attack Warpbeetles");
        addConfig("option.skeletonAttackPiglin", "Skeletons attack Piglins");
        addConfig("option.skeletonAttackBrute", "Skeletons attack Brutes");
        addConfig("option.skeletonAttackEmbody", "Skeletons attack Embodies");
        addConfig("option.skeletonAttackGiant", "Skeletons attack Giants");
        addConfig("option.piglinAttackSkeleton", "Piglins attack Skeletons");
        addConfig("option.piglinAttackVoline", "Piglins attack Voline");
        addConfig("option.bruteAttackSkeleton", "Brutes attack Skeletons");
        addConfig("option.bruteAttackVoline", "Brutes attack Voline");
        addConfig("option.ghastAttackEmbody", "Ghasts attack Embodies");
        addConfig("option.ghastAttackVoline", "Ghasts attack Voline");
        addConfig("option.ghastAttackSkeleton", "Ghasts attack Skeletons");
        addConfig("option.ghastAttackGlowsquito", "Ghasts attack Glowsquitos");
        addConfig("option.glowsquitoAttackDwarf", "Glowsquitos attack Blackstone Dwarves");
        addConfig("option.glowsquitoAttackLuminous", "Glowsquitos attack Luminous Entities");
        addConfig("option.dwarfAttackPiglin", "Blackstone Dwarves attack Piglins");
        addConfig("option.dwarfAttackZombiePiglin", "Blackstone Dwarves attack Zombified Piglins");
        addConfig("option.dwarfAttackSkeletalPiglin", "Blackstone Dwarves attack Skeletal Piglins");
        addConfig("option.dwarfAttackPlayer", "Blackstone Dwarves attack Players");
        addConfig("option.blindsightAttackGlowsquito", "Blindsights attack Glowsquitos");
        addConfig("option.blindsightAttackPlayer", "Blindsights attack Players");
        addConfig("option.giantAttackMagmaCube", "Basalt Giants attack Magma Cubes");
        addConfig("option.embodyAttackPiglin", "Embodies attack Piglins");
        addConfig("option.embodyAttackPlayer", "Embodies attack Players");
        addConfig("option.volineAttackFireResistance", "Voline attack Fire Resistance Entities");
        addConfig("option.volineAttackPlayer", "Voline attack Players");
        addConfig("option.volineAttackMagmaCube", "Voline attack Magma Cubes");
        addConfig("option.useHogchops", "Use Hogchops Drop");
        addConfig("option.glowsilkSpeed", "Glowsilk Moth Speed");
        addConfig("option.useThrowableBricks", "Use Throwable Bricks");
        addConfig("tooltip.piglinFearWarpbeetle", "Determines if Piglins will run away from Warpbeetles");
        addConfig("tooltip.piglinFearEmbody", "Determines if Piglins will run away from Embodies");
        addConfig("tooltip.piglinFearDwarf", "Determines if Piglins will run away from Blackstone Dwarves");
        addConfig("tooltip.hoglinFearWarpbeetle", "Determines if Hoglins will run away from Warpbeetles");
        addConfig("tooltip.hoglinFearEmbody", "Determines if Hoglins will run away from Embodies");
        addConfig("tooltip.spiderAttackWarpbeetle", "Determines if Spiders will attack Warpbeetles");
        addConfig("tooltip.skeletonAttackPiglin", "Determines if Skeletons will attack Piglins");
        addConfig("tooltip.skeletonAttackBrute", "Determines if Skeletons will attack Piglin Brutes");
        addConfig("tooltip.skeletonAttackEmbody", "Determines if Skeletons will attack Embodies");
        addConfig("tooltip.skeletonAttackGiant", "Determines if Skeletons will attack Basalt Giants");
        addConfig("tooltip.piglinAttackSkeleton", "Determines if Piglins will attack Skeletons");
        addConfig("tooltip.piglinAttackVoline", "Determines if Piglins will attack Voline");
        addConfig("tooltip.bruteAttackSkeleton", "Determines if Piglin Brutes will attack Skeletons");
        addConfig("tooltip.bruteAttackVoline", "Determines if Piglin Brutes will attack Voline");
        addConfig("tooltip.ghastAttackEmbody", "Determines if Ghasts will attack Embodies");
        addConfig("tooltip.ghastAttackVoline", "Determines if Ghasts will attack Voline");
        addConfig("tooltip.ghastAttackSkeleton", "Determines if Ghasts will attack Skeletons");
        addConfig("tooltip.ghastAttackGlowsquito", "Determines if Ghasts will attack Glowsquitos");
        addConfig("tooltip.glowsquitoAttackDwarf", "Determines if Glowsquitos will attack Blackstone Dwarves");
        addConfig("tooltip.glowsquitoAttackLuminous", "Determines if Glowsquitos will attack Entities with the Luminance Effect");
        addConfig("tooltip.dwarfAttackPiglin", "Determines if Blackstone Dwarves will attack Piglins");
        addConfig("tooltip.dwarfAttackZombiePiglin", "Determines if Blackstone Dwarves will attack Zombified Piglins");
        addConfig("tooltip.dwarfAttackSkeletalPiglin", "Determines if Blackstone Dwarves will attack Skeletal Piglins");
        addConfig("tooltip.dwarfAttackPlayer", "Determines if Blackstone Dwarves will attack Players");
        addConfig("tooltip.blindsightAttackGlowsquito", "Determines if Blindsights will attack Glowsquitos");
        addConfig("tooltip.blindsightAttackPlayer", "Determines if Blindsights will attack Players");
        addConfig("tooltip.giantAttackMagmaCube", "Determines if Basalt Giants will attack Magma Cubes");
        addConfig("tooltip.embodyAttackPiglin", "Determines if Embodies will attack Piglins");
        addConfig("tooltip.embodyAttackPlayer", "Determines if Embodies will attack Players");
        addConfig("tooltip.volineAttackFireResistance", "Determines if Voline will attack Entities with Fire Resistance");
        addConfig("tooltip.volineAttackPlayer", "Determines if Voline will attack Players");
        addConfig("tooltip.volineAttackMagmaCube", "Determines if Voline will attack small Magma Cubes");
        addConfig("tooltip.useHogchops", "Determines if Hogchops will replace Porkchops in Hoglin Drops");
        addConfig("tooltip.glowsilkSpeed", "Determines the speed at which Glowsilk Moths fly");
        addConfig("tooltip.useThrowableBricks", "Determines if bricks are throwable or not");
        addConfig("option.volineWastes.enable", "Voline in Wastes");
        addConfig("option.shroomloinCrimson.enable", "Shroomloin in Crimson");
        addConfig("option.volineCrimson.enable", "Voline in Crimson");
        addConfig("option.warpbeetleWarped.enable", "Warpbeetles in Warped");
        addConfig("option.giantDeltas.enable", "Giants in Deltas");
        addConfig("option.embodySSV.enable", "Embodies in SSV");
        addConfig("option.glowsilkGSC.enable", "Glowsilk Moths in GSC");
        addConfig("option.glowsilkDeltas.enable", "Glowsilk Moths in Deltas");
        addConfig("option.glowsilkCrimson.enable", "Glowsilk Moths in Crimson");
        addConfig("tooltip.volineWastes.enable", "Determines if Voline will spawn in Nether Wastes");
        addConfig("tooltip.shroomloinCrimson.enable", "Determines if Shroomloin will spawn in Crimson Forests");
        addConfig("tooltip.volineCrimson.enable", "Determines if Voline will spawn in Crimson Forests");
        addConfig("tooltip.warpbeetleWarped.enable", "Determines if Warpbeetles will spawn in Warped Forests");
        addConfig("tooltip.giantDeltas.enable", "Determines if Basalt Giants will spawn in Basalt Deltas");
        addConfig("tooltip.embodySSV.enable", "Determines if Embodies will spawn in Soul Sand Valleys");
        addConfig("tooltip.glowsilkGSC.enable", "Determines if Glowsilk Moths will spawn in Glowstone Canyons");
        addConfig("tooltip.glowsilkDeltas.enable", "Determines if Glowsilk Moths will spawn in Basalt Deltas");
        addConfig("tooltip.glowsilkCrimson.enable", "Determines if Glowsilk Moths will spawn in Crimson Forests");
        addConfig("option.biomesListIsWhitelist", "Whitelist or Blacklist");
        addConfig("option.biomesList", "Biome List");
        addConfig("tooltip.biomesListIsWhitelist", "Should the biome list below act as a whitelist (True/On), or a blacklist (False/Off). CHANGING THIS REQUIRES A GAME RESTART.");
        addConfig("tooltip.biomesList", "List of biomes to either whitelist or blacklist from nether generation. Split biomes with a comma. To include all nether biomes from all loaded mods leave this blank. CHANGING THIS REQUIRES A GAME RESTART.");
        addConfig("option.spawnrate", "Spawnrate");
        addConfig("tooltip.volineWastes.spawnrate", "Determines the rate at which Voline spawn in Nether Wastes");
        addConfig("tooltip.shroomloinCrimson.spawnrate", "Determines the rate at which Shroomloin spawn in Crimson Forests");
        addConfig("tooltip.volineCrimson.spawnrate", "Determines the rate at which Voline spawn in Crimson Forests");
        addConfig("tooltip.warpbeetleWarped.spawnrate", "Determines the rate at which Warpbeetles spawn in Warped Forests");
        addConfig("tooltip.giantDeltas.spawnrate", "Determines the rate at which Basalt Giants spawn in Basalt Deltas");
        addConfig("tooltip.embodySSV.spawnrate", "Determines the rate at which Embodies spawn in Soul Sand Valleys");
        addConfig("tooltip.glowsilkGSC.spawnrate", "Determines the rate at which Glowsilk Moths spawn in Glowstone Canyons");
        addConfig("tooltip.glowsilkDeltas.spawnrate", "Determines the rate at which Glowsilk Moths spawn in Basalt Deltas");
        addConfig("tooltip.glowsilkCrimson.spawnrate", "Determines the rate at which Glowsilk Moths spawn in Crimson Forests");
        addConfig("option.isShroomlightGrowable", "Shroomlight Growable");
        addConfig("option.shroomlightGrowChance", "Shroomlight Grow Chance");
        addConfig("option.luminousFungusActivateDistance", "Luminous Fungus Activate Distance");
        addConfig("tooltip.isShroomlightGrowable", "Determines if a Shroomlight Tear will grow when a Shroomlight is bonemealed (overrides Shroomlight Grow Chance)");
        addConfig("tooltip.shroomlightGrowChance", "Determines the chance a shroomlight tear will grow when a Shroomlight is bonemealed");
        addConfig("tooltip.luminousFungusActivateDistance", "Determines the distance an entity has to be from a Luminous Fungus for it to activate (larger values have performance impact)");
        addConfig("option.fireChargeExplosion", "Fire Charge Explosion");
        addConfig("option.jerkyEffectDuration", "Jerky Effect Duration");
        addConfig("option.jerkyEffectAmplifier", "Jerky Effect Amplifier");
        addConfig("tooltip.fireChargeExplosion", "Determines if thrown fire charges will explode on impact");
        addConfig("tooltip.jerkyEffectDuration", "Determines the duration in seconds of the effect that Cured Jerky gives");
        addConfig("tooltip.jerkyEffectAmplifier", "Determines the amplifier of the effect that Cured Jerky gives");
        addConfig("option.luminousRefreshDelay", "Luminous Effect Refresh Delay");
        addConfig("tooltip.luminousRefreshDelay", "Determines how often (in ticks) the luminous effect should update");
        addConfig("option.luminousFungusGivesEffect", "Luminous Fungus gives Luminance Effect");
        addConfig("tooltip.luminousFungusGivesEffect", "Determines whether Luminous Fungus gives the Luminance effect on collision with an entity");

        // Commands,
        add(InfernalExpansion.MOD_ID + ".commands.setdimensionspawn.success.single", "Set %s's spawnpoint to %s, %s, %s [%s] in %s");
        add(InfernalExpansion.MOD_ID + ".commands.setdimensionspawn.success.multiple", "Set %s players' spawnpoints to %s, %s, %s [%s] in %s");
    }

    protected void add(CreativeModeTab key, String name) {
        add("itemGroup." + ((CreativeModeTabAccessor) key).getLangId(), name);
    }

    @Override
    public void add(@NotNull Block key, @Nullable String name) {
        add(key.getDescriptionId(), name);
    }

    @Override
    public void add(@NotNull Item key, @Nullable String name) {
        add(key.getDescriptionId(), name);
    }

    @Override
    public void add(@NotNull EntityType<?> key, @Nullable String name) {
        add(key.getDescriptionId(), name);
    }

    protected void add(ResourceKey<Biome> key, String name) {
        add("biome." + key.location().getNamespace() + "." + key.location().getPath(), name);
    }

    protected void addMusicDisc(Item key, String name, String description) {
        add(key, name);
        add(key.getDescriptionId() + ".desc", description);
    }

    protected void addEntitySubtitles(EntityType<?> key, String ambient, String hurt, String death) {
        add("subtitles.entity." + ForgeRegistries.ENTITIES.getKey(key).getPath() + ".ambient", ambient);
        add("subtitles.entity." + ForgeRegistries.ENTITIES.getKey(key).getPath() + ".hurt", hurt);
        add("subtitles.entity." + ForgeRegistries.ENTITIES.getKey(key).getPath() + ".death", death);
    }

    protected void addEffect(MobEffect key, @Nullable String name) {
        add(key.getDescriptionId(), name);
        add("item.minecraft.potion.effect." + ForgeRegistries.MOB_EFFECTS.getKey(key).getPath(), name == null ? null : "Potion of " + name);
        add("item.minecraft.splash_potion.effect." + ForgeRegistries.MOB_EFFECTS.getKey(key).getPath(), name == null ? null : "Splash Potion of " + name);
        add("item.minecraft.lingering_potion.effect." + ForgeRegistries.MOB_EFFECTS.getKey(key).getPath(), name == null ? null : "Lingering Potion of " + name);
        add("item.minecraft.tipped_arrow.effect." + ForgeRegistries.MOB_EFFECTS.getKey(key).getPath(), name == null ? null : "Arrow of " + name);
    }

    protected void addConfig(String key, String name) {
        add(InfernalExpansion.MOD_ID + ".config." + key, name);
    }

    @Override
    public void add(@NotNull String key, @Nullable String value) {
        if (value == null) {
            if (!baseData.containsKey(key))
                throw new IllegalStateException("Translation name is null and base data does not contain the key " + key);

            value = baseData.get(key);
        }

        super.add(key, value);
    }

}
