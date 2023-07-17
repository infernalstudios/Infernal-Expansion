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
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.init.IEBiomes;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEffects;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEItems;

public class NlNlLanguageProvider extends IELanguageProvider {

    public NlNlLanguageProvider(DataGenerator generator) {
        super(generator, "nl_nl");
    }

    @Override
    protected void addTranslations() {
        add(InfernalExpansion.TAB, "Vurige Uitbreiding");
        add(Blocks.GLOWSTONE, "Gloeisteen");
        add(IEBlocks.DIMSTONE.get(), "Schemersteen");
        add(IEBlocks.DULLSTONE.get(), "Dofsteen");
        add(IEBlocks.SMOOTH_GLOWSTONE.get(), "Glad Gloeisteen");
        add(IEBlocks.SMOOTH_DIMSTONE.get(), "Glad Schemersteen");
        add(IEBlocks.SMOOTH_DULLSTONE.get(), "Glad Dofsteen");
        add(IEBlocks.GLOWSTONE_BRICKS.get(), "Gloeistenen Blokstenen");
        add(IEBlocks.DIMSTONE_BRICKS.get(), "Schemerstenen Blokstenen");
        add(IEBlocks.DULLSTONE_BRICKS.get(), "Dofstenen Blokstenen");
        add(IEBlocks.CRACKED_GLOWSTONE_BRICKS.get(), "Gebarsten Gloeistenen Blokstenen");
        add(IEBlocks.CRACKED_DIMSTONE_BRICKS.get(), "Gebarsten Schemerstenen Blokstenen");
        add(IEBlocks.CRACKED_DULLSTONE_BRICKS.get(), "Gebarsten Dofstenen Blokstenen");
        add(IEBlocks.CHISELED_GLOWSTONE_BRICKS.get(), "Gebeiteld Gloeisteen");
        add(IEBlocks.CHISELED_DIMSTONE_BRICKS.get(), "Gebeiteld Schemersteen");
        add(IEBlocks.CHISELED_DULLSTONE_BRICKS.get(), "Gebeiteld Dofsteen");
        add(IEBlocks.SMOOTH_GLOWSTONE_SLAB.get(), "Gladde Gloeistenen Plaat");
        add(IEBlocks.SMOOTH_GLOWSTONE_VERTICAL_SLAB.get(), null);
        add(IEBlocks.SMOOTH_GLOWSTONE_STAIRS.get(), "Gladde Gloeistenen Trap");
        add(IEBlocks.SMOOTH_GLOWSTONE_BUTTON.get(), null);
        add(IEBlocks.SMOOTH_GLOWSTONE_PRESSURE_PLATE.get(), null);
        add(IEBlocks.SMOOTH_DIMSTONE_SLAB.get(), "Gladde Schemerstenen Plaat");
        add(IEBlocks.SMOOTH_DIMSTONE_VERTICAL_SLAB.get(), null);
        add(IEBlocks.SMOOTH_DIMSTONE_STAIRS.get(), "Gladde Schemerstenen Trap");
        add(IEBlocks.SMOOTH_DIMSTONE_BUTTON.get(), null);
        add(IEBlocks.SMOOTH_DULLSTONE_SLAB.get(), "Gladde Dofstenen Plaat");
        add(IEBlocks.SMOOTH_DULLSTONE_VERTICAL_SLAB.get(), null);
        add(IEBlocks.SMOOTH_DULLSTONE_STAIRS.get(), "Gladde Dofstenen Trap");
        add(IEBlocks.SMOOTH_DULLSTONE_BUTTON.get(), null);
        add(IEBlocks.GLOWSTONE_BRICK_SLAB.get(), "Gloeistenen Blokstenen Plaat");
        add(IEBlocks.GLOWSTONE_BRICK_VERTICAL_SLAB.get(), null);
        add(IEBlocks.GLOWSTONE_BRICK_STAIRS.get(), "Gloeistenen Blokstenen Trap");
        add(IEBlocks.GLOWSTONE_BRICK_WALL.get(), null);
        add(IEBlocks.DIMSTONE_BRICK_SLAB.get(), "Schemerstenen Blokstenen Plaat");
        add(IEBlocks.DIMSTONE_BRICK_VERTICAL_SLAB.get(), null);
        add(IEBlocks.DIMSTONE_BRICK_STAIRS.get(), "Schemerstenen Blokstenen Trap");
        add(IEBlocks.DIMSTONE_BRICK_WALL.get(), null);
        add(IEBlocks.DULLSTONE_BRICK_SLAB.get(), "Dofstenen Blokstenen Plaat");
        add(IEBlocks.DULLSTONE_BRICK_VERTICAL_SLAB.get(), null);
        add(IEBlocks.DULLSTONE_BRICK_STAIRS.get(), "Dofstenen Blokstenen Trap");
        add(IEBlocks.DULLSTONE_BRICK_WALL.get(), null);
        add(IEBlocks.LUMINOUS_WART_BLOCK.get(), null);
        add(IEBlocks.GLOWDUST.get(), "Gloeistof");
        add(IEBlocks.GLOWDUST_SAND.get(), "Gloeistof Zand");
        add(IEBlocks.GLOWDUST_STONE.get(), "Gloeistof Steen");
        add(IEBlocks.GLOWDUST_STONE_SLAB.get(), null);
        add(IEBlocks.GLOWDUST_STONE_VERTICAL_SLAB.get(), null);
        add(IEBlocks.GLOWDUST_STONE_STAIRS.get(), null);
        add(IEBlocks.GLOWDUST_STONE_WALL.get(), null);
        add(IEBlocks.TRAPPED_GLOWDUST_SAND.get(), null);
        add(IEBlocks.GLOW_FIRE.get(), null);
        add(IEBlocks.GLOWDUST_STONE_BRICKS.get(), null);
        add(IEBlocks.GLOWDUST_STONE_BRICK_SLAB.get(), null);
        add(IEBlocks.GLOWDUST_STONE_BRICK_VERTICAL_SLAB.get(), null);
        add(IEBlocks.GLOWDUST_STONE_BRICK_STAIRS.get(), null);
        add(IEBlocks.GLOWDUST_STONE_BRICK_WALL.get(), null);
        add(IEBlocks.CRACKED_GLOWDUST_STONE_BRICKS.get(), null);
        add(IEBlocks.CHISELED_GLOWDUST_STONE_BRICKS.get(), null);
        add(IEBlocks.CRUMBLING_BLACKSTONE.get(), "Afbrokkelend Zwartsteen");
        add(IEBlocks.SILT.get(), "Basalt Slib");
        add(IEBlocks.RUBBLE.get(), "Zwartsteen Puin");
        add(IEBlocks.DULLTHORNS_BLOCK.get(), null);
        add(IEBlocks.BASALT_COBBLED.get(), null);
        add(IEBlocks.BASALT_COBBLED_SLAB.get(), null);
        add(IEBlocks.BASALT_COBBLED_VERTICAL_SLAB.get(), null);
        add(IEBlocks.BASALT_WALL.get(), null);
        add(IEBlocks.BASALT_STAIRS.get(), null);
        add(IEBlocks.BASALT_BUTTON.get(), null);
        add(IEBlocks.BASALT_SLAB.get(), null);
        add(IEBlocks.BASALT_VERTICAL_SLAB.get(), null);
        add(IEBlocks.POLISHED_BASALT_PRESSURE_PLATE.get(), null);
        add(IEBlocks.POLISHED_BASALT_SLAB.get(), null);
        add(IEBlocks.POLISHED_BASALT_VERTICAL_SLAB.get(), null);
        add(IEBlocks.POLISHED_BASALT_TILES_SLAB.get(), null);
        add(IEBlocks.POLISHED_BASALT_TILES_VERTICAL_SLAB.get(), null);
        add(IEBlocks.POLISHED_BASALT_TILES.get(), null);
        add(IEBlocks.BASALT_BRICKS.get(), null);
        add(IEBlocks.BASALT_BRICK_WALL.get(), null);
        add(IEBlocks.BASALT_BRICK_STAIRS.get(), null);
        add(IEBlocks.BASALT_BRICK_SLAB.get(), null);
        add(IEBlocks.BASALT_BRICK_VERTICAL_SLAB.get(), null);
        add(IEBlocks.CRACKED_BASALT_BRICKS.get(), null);
        add(IEBlocks.CHISELED_BASALT_BRICKS.get(), null);
        add(IEBlocks.MAGMATIC_CHISELED_BASALT_BRICKS.get(), null);
        add(IEBlocks.BASALT_IRON_ORE.get(), null);
        add(IEBlocks.BASALTIC_MAGMA.get(), null);
        add(IEBlocks.SOUL_SAND_STAIRS.get(), null);
        add(IEBlocks.SOUL_SAND_SLAB.get(), null);
        add(IEBlocks.SOUL_SAND_VERTICAL_SLAB.get(), null);
        add(IEBlocks.SOUL_SOIL_STAIRS.get(), null);
        add(IEBlocks.SOUL_SOIL_SLAB.get(), null);
        add(IEBlocks.SOUL_SOIL_VERTICAL_SLAB.get(), null);
        add(IEBlocks.SOUL_STONE.get(), null);
        add(IEBlocks.SOUL_STONE_SLAB.get(), null);
        add(IEBlocks.SOUL_STONE_VERTICAL_SLAB.get(), null);
        add(IEBlocks.SOUL_STONE_STAIRS.get(), null);
        add(IEBlocks.SOUL_STONE_WALL.get(), null);
        add(IEBlocks.SOUL_STONE_BRICKS.get(), null);
        add(IEBlocks.SOUL_STONE_BRICK_WALL.get(), null);
        add(IEBlocks.SOUL_STONE_BRICK_STAIRS.get(), null);
        add(IEBlocks.SOUL_STONE_BRICK_SLAB.get(), null);
        add(IEBlocks.SOUL_STONE_BRICK_VERTICAL_SLAB.get(), null);
        add(IEBlocks.CRACKED_SOUL_STONE_BRICKS.get(), null);
        add(IEBlocks.CHISELED_SOUL_STONE_BRICKS.get(), null);
        add(IEBlocks.CHARGED_CHISELED_SOUL_STONE_BRICKS.get(), null);
        add(IEBlocks.SOUL_SLATE.get(), null);
        add("block." + InfernalExpansion.MOD_ID + ".soul_slate_wall", null);
        add(IEBlocks.SOUL_SLATE_STAIRS.get(), null);
        add(IEBlocks.SOUL_SLATE_BUTTON.get(), null);
        add(IEBlocks.SOUL_SLATE_SLAB.get(), null);
        add(IEBlocks.SOUL_SLATE_VERTICAL_SLAB.get(), null);
        add(IEBlocks.SOUL_SLATE_PRESSURE_PLATE.get(), null);
        add(IEBlocks.SOUL_SLATE_BRICKS.get(), null);
        add(IEBlocks.SOUL_SLATE_BRICK_WALL.get(), null);
        add(IEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), null);
        add(IEBlocks.SOUL_SLATE_BRICK_SLAB.get(), null);
        add(IEBlocks.SOUL_SLATE_BRICK_VERTICAL_SLAB.get(), null);
        add(IEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), null);
        add(IEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), null);
        add(IEBlocks.CHARGED_CHISELED_SOUL_SLATE_BRICKS.get(), null);
        add(IEBlocks.CRIMSON_FUNGUS_CAP.get(), null);
        add(IEBlocks.WARPED_FUNGUS_CAP.get(), null);
        add(IEBlocks.LUMINOUS_FUNGUS_CAP.get(), null);
        add(IEBlocks.SHROOMLIGHT_FUNGUS.get(), null);
        add(IEBlocks.BURIED_BONE.get(), null);
        add(IEBlocks.PLANTED_QUARTZ.get(), null);
        add(IEBlocks.WARPED_NYLIUM_PATH.get(), null);
        add(IEBlocks.CRIMSON_NYLIUM_PATH.get(), null);
        add(IEBlocks.GLOW_LANTERN.get(), "Gloeilichtlantaarn");
        add(IEBlocks.GLOW_TORCH.get(), "Gloeilichtfakkel");
        add(IEBlocks.GLOW_CAMPFIRE.get(), "Gloeilichtkampvuur");
        add(IEBlocks.GLOW_GLASS.get(), null);
        add(IEBlocks.GLOW_GLASS_PANE.get(), null);
        add(IEBlocks.LUMINOUS_FUNGUS.get(), "Lichtgevende Zwam");
        add(IEBlocks.DULLTHORNS.get(), "Dofdoornen");
        add(IEBlocks.GLOWSILK_COCOON.get(), null);
        add(IEBlocks.WARPED_NYLIUM_CARPET.get(), null);
        add(IEBlocks.CRIMSON_NYLIUM_CARPET.get(), null);
        add(IEBlocks.SOUL_SOIL_PATH.get(), null);
        add(IEBlocks.QUARTZ_GLASS.get(), null);
        add(IEBlocks.QUARTZ_GLASS_PANE.get(), null);
        add(Blocks.NETHER_SPROUTS, null);
        add(Blocks.NETHER_WART_BLOCK, null);
        add(IEItems.GLOWCOAL.get(), "Gloeikool");
        add(IEItems.DULLROCKS.get(), "Dofkeien");
        add(IEItems.BLINDSIGHT_TONGUE.get(), null);
        add(IEItems.MOTH_DUST.get(), null);
        add(IEItems.MOLTEN_GOLD_CLUSTER.get(), null);
        add(IEItems.GLOWSILK.get(), null);
        add(IEItems.SOUL_SALT_CLUMP.get(), null);
        add(IEItems.INFERNAL_PAINTING.get(), null);
        add(IEItems.STRIDER_BUCKET.get(), null);
        add(IEItems.MAGMA_CUBE_BUCKET.get(), null);
        add(IEItems.VOLINE_BUCKET.get(), null);
        add(IEItems.BLINDSIGHT_TONGUE_STEW.get(), null);
        add(IEItems.CURED_JERKY.get(), null);
        add(IEItems.RAW_HOGCHOP.get(), null);
        add(IEItems.COOKED_HOGCHOP.get(), null);
        add(IEItems.ASCUS_BOMB.get(), null);
        add("item." + InfernalExpansion.MOD_ID + ".frostbitten_sword", null);
        add("item." + InfernalExpansion.MOD_ID + ".frostbitten_pickaxe", null);
        add("item." + InfernalExpansion.MOD_ID + ".frostbitten_axe", null);
        add("item." + InfernalExpansion.MOD_ID + ".frostbitten_shovel", null);
        add("item." + InfernalExpansion.MOD_ID + ".frostbitten_hoe", null);
        add(IEItems.GLOWSILK_BOW.get(), null);
        add(IEItems.BLINDSIGHT_TONGUE_WHIP.get(), null);
        add(IEItems.VOLINE_SPAWN_EGG.get(), "Vulkauwspawnei");
        add(IEItems.SHROOMLOIN_SPAWN_EGG.get(), "Knalzwamspawnei");
        add(IEItems.WARPBEETLE_SPAWN_EGG.get(), "Vervormde Keverspawnei");
        add(IEItems.EMBODY_SPAWN_EGG.get(), "Belichamingspawnei");
        add(IEItems.BASALT_GIANT_SPAWN_EGG.get(), "Basalt Reusspawnei");
        add(IEItems.BLACKSTONE_DWARF_SPAWN_EGG.get(), null);
        add(IEItems.GLOWSQUITO_SPAWN_EGG.get(), null);
        add(IEItems.BLINDSIGHT_SPAWN_EGG.get(), null);
        add(IEItems.GLOWSILK_MOTH_SPAWN_EGG.get(), null);
        add("item." + InfernalExpansion.MOD_ID + ".cerobeetle_spawn_egg", null);
        add("item." + InfernalExpansion.MOD_ID + ".pyrno_spawn_egg", null);
        addMusicDisc(IEItems.MUSIC_DISC_SOUL_SPUNK.get(), null, null);
        addMusicDisc(IEItems.MUSIC_DISC_FLUSH.get(), null, null);
        add(IEEntityTypes.VOLINE.get(), "Vulkauw");
        add(IEEntityTypes.SHROOMLOIN.get(), "Knalzwam");
        add(IEEntityTypes.WARPBEETLE.get(), "Vervormde Kever");
        add(IEEntityTypes.EMBODY.get(), "Belichaming");
        add(IEEntityTypes.BASALT_GIANT.get(), "Basalt Reus");
        add(IEEntityTypes.BLACKSTONE_DWARF.get(), null);
        add(IEEntityTypes.GLOWSQUITO.get(), null);
        add(IEEntityTypes.BLINDSIGHT.get(), null);
        add(IEEntityTypes.GLOWSILK_MOTH.get(), null);
        add(IEEntityTypes.ASCUS_BOMB.get(), null);
        add(IEEntityTypes.THROWABLE_MAGMA_CREAM.get(), null);
        add(IEEntityTypes.THROWABLE_FIRE_CHARGE.get(), null);
        add(IEEntityTypes.THROWABLE_BRICK.get(), null);
        add(IEEntityTypes.THROWABLE_NETHER_BRICK.get(), null);
        add(IEEntityTypes.INFERNAL_PAINTING.get(), null);
        add("entity." + InfernalExpansion.MOD_ID + ".pyrno", null);
        add(IEBiomes.GLOWSTONE_CANYON, null);
        add("biome." + InfernalExpansion.MOD_ID + ".delta_shores", null);
        addEffect(IEEffects.LUMINOUS.get(), null);
        addEffect(IEEffects.INFECTION.get(), null);
        add("generator." + InfernalExpansion.MOD_ID + ".compat_world_type", null);
        addConfig("button.mobInteractions", null);
        addConfig("button.mobSpawning", null);
        addConfig("button.miscellaneous", null);
        addConfig("button.worldGeneration", null);
        addConfig("button.clientConfig", null);
        addConfig("title", null);
        addConfig("title.mobInteractions", null);
        addConfig("title.mob_spawning", null);
        addConfig("title.miscellaneous", null);
        addConfig("title.worldGeneration", null);
        addConfig("title.clientConfig", null);
        addConfig("subtitle.spawnable_biomes", null);
        addConfig("option.piglinFearWarpbeetle", null);
        addConfig("option.piglinFearEmbody", null);
        addConfig("option.piglinFearDwarf", null);
        addConfig("option.hoglinFearWarpbeetle", null);
        addConfig("option.hoglinFearEmbody", null);
        addConfig("option.spiderAttackWarpbeetle", null);
        addConfig("option.skeletonAttackPiglin", null);
        addConfig("option.skeletonAttackBrute", null);
        addConfig("option.skeletonAttackEmbody", null);
        addConfig("option.skeletonAttackGiant", null);
        addConfig("option.piglinAttackSkeleton", null);
        addConfig("option.piglinAttackVoline", null);
        addConfig("option.bruteAttackSkeleton", null);
        addConfig("option.bruteAttackVoline", null);
        addConfig("option.ghastAttackEmbody", null);
        addConfig("option.ghastAttackVoline", null);
        addConfig("option.ghastAttackSkeleton", null);
        addConfig("option.ghastAttackGlowsquito", null);
        addConfig("option.glowsquitoAttackDwarf", null);
        addConfig("option.glowsquitoAttackLuminous", null);
        addConfig("option.dwarfAttackPiglin", null);
        addConfig("option.dwarfAttackZombiePiglin", null);
        addConfig("option.dwarfAttackSkeletalPiglin", null);
        addConfig("option.dwarfAttackPlayer", null);
        addConfig("option.blindsightAttackGlowsquito", null);
        addConfig("option.blindsightAttackPlayer", null);
        addConfig("option.giantAttackMagmaCube", null);
        addConfig("option.embodyAttackPiglin", null);
        addConfig("option.embodyAttackPlayer", null);
        addConfig("option.volineAttackFireResistance", null);
        addConfig("option.volineAttackPlayer", null);
        addConfig("option.volineAttackMagmaCube", null);
        addConfig("option.useHogchops", null);
        addConfig("option.glowsilkSpeed", null);
        addConfig("option.useThrowableBricks", null);
        addConfig("tooltip.piglinFearWarpbeetle", null);
        addConfig("tooltip.piglinFearEmbody", null);
        addConfig("tooltip.piglinFearDwarf", null);
        addConfig("tooltip.hoglinFearWarpbeetle", null);
        addConfig("tooltip.hoglinFearEmbody", null);
        addConfig("tooltip.spiderAttackWarpbeetle", null);
        addConfig("tooltip.skeletonAttackPiglin", null);
        addConfig("tooltip.skeletonAttackBrute", null);
        addConfig("tooltip.skeletonAttackEmbody", null);
        addConfig("tooltip.skeletonAttackGiant", null);
        addConfig("tooltip.piglinAttackSkeleton", null);
        addConfig("tooltip.piglinAttackVoline", null);
        addConfig("tooltip.bruteAttackSkeleton", null);
        addConfig("tooltip.bruteAttackVoline", null);
        addConfig("tooltip.ghastAttackEmbody", null);
        addConfig("tooltip.ghastAttackVoline", null);
        addConfig("tooltip.ghastAttackSkeleton", null);
        addConfig("tooltip.ghastAttackGlowsquito", null);
        addConfig("tooltip.glowsquitoAttackDwarf", null);
        addConfig("tooltip.glowsquitoAttackLuminous", null);
        addConfig("tooltip.dwarfAttackPiglin", null);
        addConfig("tooltip.dwarfAttackZombiePiglin", null);
        addConfig("tooltip.dwarfAttackSkeletalPiglin", null);
        addConfig("tooltip.dwarfAttackPlayer", null);
        addConfig("tooltip.blindsightAttackGlowsquito", null);
        addConfig("tooltip.blindsightAttackPlayer", null);
        addConfig("tooltip.giantAttackMagmaCube", null);
        addConfig("tooltip.embodyAttackPiglin", null);
        addConfig("tooltip.embodyAttackPlayer", null);
        addConfig("tooltip.volineAttackFireResistance", null);
        addConfig("tooltip.volineAttackPlayer", null);
        addConfig("tooltip.volineAttackMagmaCube", null);
        addConfig("tooltip.useHogchops", null);
        addConfig("tooltip.glowsilkSpeed", null);
        addConfig("tooltip.useThrowableBricks", null);
        addConfig("option.volineWastes.enable", null);
        addConfig("option.shroomloinCrimson.enable", null);
        addConfig("option.volineCrimson.enable", null);
        addConfig("option.warpbeetleWarped.enable", null);
        addConfig("option.giantDeltas.enable", null);
        addConfig("option.embodySSV.enable", null);
        addConfig("option.glowsilkGSC.enable", null);
        addConfig("option.glowsilkDeltas.enable", null);
        addConfig("option.glowsilkCrimson.enable", null);
        addConfig("tooltip.volineWastes.enable", null);
        addConfig("tooltip.shroomloinCrimson.enable", null);
        addConfig("tooltip.volineCrimson.enable", null);
        addConfig("tooltip.warpbeetleWarped.enable", null);
        addConfig("tooltip.giantDeltas.enable", null);
        addConfig("tooltip.embodySSV.enable", null);
        addConfig("tooltip.glowsilkGSC.enable", null);
        addConfig("tooltip.glowsilkDeltas.enable", null);
        addConfig("tooltip.glowsilkCrimson.enable", null);
        addConfig("option.biomesListIsWhitelist", null);
        addConfig("option.biomesList", null);
        addConfig("tooltip.biomesListIsWhitelist", null);
        addConfig("tooltip.biomesList", null);
        addConfig("option.spawnrate", null);
        addConfig("tooltip.volineWastes.spawnrate", null);
        addConfig("tooltip.shroomloinCrimson.spawnrate", null);
        addConfig("tooltip.volineCrimson.spawnrate", null);
        addConfig("tooltip.warpbeetleWarped.spawnrate", null);
        addConfig("tooltip.giantDeltas.spawnrate", null);
        addConfig("tooltip.embodySSV.spawnrate", null);
        addConfig("tooltip.glowsilkGSC.spawnrate", null);
        addConfig("tooltip.glowsilkDeltas.spawnrate", null);
        addConfig("tooltip.glowsilkCrimson.spawnrate", null);
        addConfig("option.isShroomlightGrowable", null);
        addConfig("option.shroomlightGrowChance", null);
        addConfig("option.luminousFungusActivateDistance", null);
        addConfig("tooltip.isShroomlightGrowable", null);
        addConfig("tooltip.shroomlightGrowChance", null);
        addConfig("tooltip.luminousFungusActivateDistance", null);
        addConfig("option.fireChargeExplosion", null);
        addConfig("option.jerkyEffectDuration", null);
        addConfig("option.jerkyEffectAmplifier", null);
        addConfig("tooltip.fireChargeExplosion", null);
        addConfig("tooltip.jerkyEffectDuration", null);
        addConfig("tooltip.jerkyEffectAmplifier", null);
        addConfig("option.luminousRefreshDelay", null);
        addConfig("tooltip.luminousRefreshDelay", null);
        addConfig("option.luminousFungusGivesEffect", null);
        addConfig("tooltip.luminousFungusGivesEffect", null);
        add(InfernalExpansion.MOD_ID + ".commands.setdimensionspawn.success.single", null);
        add(InfernalExpansion.MOD_ID + ".commands.setdimensionspawn.success.multiple", null);
        add(IEBlocks.LUMINOUS_STEM.get(), null);
        add(IEBlocks.STRIPPED_LUMINOUS_STEM.get(), null);
        add(IEBlocks.LUMINOUS_HYPHAE.get(), null);
        add(IEBlocks.STRIPPED_LUMINOUS_HYPHAE.get(), null);
        add(IEItems.SPIRIT_EYE.get(), null);
        add(IEItems.KINETIC_TONGUE_WHIP.get(), null);
        addEntitySubtitles(IEEntityTypes.VOLINE.get(), null, null, null);
        addEntitySubtitles(IEEntityTypes.SHROOMLOIN.get(), null, null, null);
        addEntitySubtitles(IEEntityTypes.BASALT_GIANT.get(), null, null, null);
        addEntitySubtitles(IEEntityTypes.BLINDSIGHT.get(), null, null, null);
        add("subtitles.entity." + ForgeRegistries.ENTITIES.getKey(IEEntityTypes.BLINDSIGHT.get()).getPath() + ".leap", null);
        addEntitySubtitles(IEEntityTypes.WARPBEETLE.get(), null, null, null);
        addEntitySubtitles(IEEntityTypes.EMBODY.get(), null, null, null);
        addEntitySubtitles(IEEntityTypes.GLOWSILK_MOTH.get(), null, null, null);
        addEntitySubtitles(IEEntityTypes.GLOWSQUITO.get(), null, null, null);
        addEntitySubtitles(IEEntityTypes.BLACKSTONE_DWARF.get(), null, null, null);
    }

}
