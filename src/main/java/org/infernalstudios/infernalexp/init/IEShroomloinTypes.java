package org.infernalstudios.infernalexp.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.util.ShroomloinType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.infernalstudios.infernalexp.InfernalExpansion.MOD_ID;

public final class IEShroomloinTypes {
    // Make sure to change the initial size of this ArrayList when adding new shroomloin variants
    private static final List<ShroomloinType> SHROOMLOIN_TYPES = new ArrayList<>(34);

    public static final ShroomloinType BLACK_PUFF =               registerWithIEDirectory("black_puff",          "byg",           "black_puff");
    public static final ShroomloinType BLOOD =                    registerWithIEDirectory("blood_mushroom",      "undergarden",   "blood");
    public static final ShroomloinType BLUE =                     registerWithIEDirectory("blue_mushroom",       "shroomed",      "blue_mushroom");
    public static final ShroomloinType BLUE_GLOWSHROOM =          registerWithIEDirectory("blue_glowshroom",     "byg",           "blue_glowshroom");
    public static final ShroomloinType BROWN =                    registerWithIEDirectory(Items.BROWN_MUSHROOM,  MOD_ID,          "brown");
    public static final ShroomloinType BULBIS_ANOMALY =           registerWithIEDirectory("bulbis_anomaly",      "byg",           "bulbis_anomaly");
    public static final ShroomloinType CRIMSON =                  registerWithIEDirectory(Items.CRIMSON_FUNGUS,  MOD_ID,          "crimson");
    public static final ShroomloinType DEATH_CAP =                registerWithIEDirectory("death_cap",           "byg",           "death_cap");
    public static final ShroomloinType EMBUR =                    registerWithIEDirectory("embur_wart",          "byg",           "embur_wart");
    public static final ShroomloinType FAIRY_RING =               registerWithIEDirectory("fairy_ring_mushroom", "habitat",       "fairy_ring_mushroom");
    public static final ShroomloinType FUNGAL_IMPARIUS =          registerWithIEDirectory("fungal_imparius",     "byg",           "fungal_imparius");
    public static final ShroomloinType GLOW_SHROOM_QUARK =        registerWithIEDirectory("glow_shroom",          "quark",         "glow_shroom");
    public static final ShroomloinType GLOWSHROOM_BOP =           registerWithIEDirectory("glowshroom",          "biomesoplenty", "glowshroom");
    public static final ShroomloinType GLOWSHROOM_DARKER_DEPTHS = registerWithIEDirectory("glowshroom",          "darkerdepths",  "glowshroom");
    public static final ShroomloinType GREEN_MUSHROOM =           registerWithIEDirectory("green_mushroom",      "byg",           "green_mushroom");
    public static final ShroomloinType IMPARIUS_MUSHROOM =        registerWithIEDirectory("imparius_mushroom",   "byg",           "imparius_mushroom");
    public static final ShroomloinType INDIGO =                   registerWithIEDirectory("indigo_mushroom",     "undergarden",   "indigo_mushroom");
    public static final ShroomloinType INK =                      registerWithIEDirectory("ink_mushroom",        "undergarden",   "ink_mushroom");
    public static final ShroomloinType LUMINOUS =                 registerWithIEDirectory("luminous_fungus",     MOD_ID,          "luminous");
    public static final ShroomloinType MASS =                     registerWithIEDirectory("lightcap",            "twist",         "lightcap");
    public static final ShroomloinType ORANGE =                   registerWithIEDirectory("orange_mushroom",     "shroomed",      "orange_mushroom");
    public static final ShroomloinType PIZZA =                    registerWithIEDirectory((Item) null,           MOD_ID,          "pizza"); // Cast to Item required due to ambiguous call
    public static final ShroomloinType POISE =                    registerWithIEDirectory("poise_bush",          "endergetic",    "poise_bush");
    public static final ShroomloinType PURPLE =                   registerWithIEDirectory("purple_mushroom",     "shroomed",      "purple_mushroom");
    public static final ShroomloinType PURPLE_GLOWSHROOM =        registerWithIEDirectory("purple_glowshroom",   "byg",           "purple_glowshroom");
    public static final ShroomloinType RED =                      registerWithIEDirectory(Items.RED_MUSHROOM,    MOD_ID,          "red");
    public static final ShroomloinType SHULKREN_FUNGUS =          registerWithIEDirectory("shulkren_fungus",     "byg",           "shulkren_fungus");
    public static final ShroomloinType SOUL_SHROOM =              registerWithIEDirectory("soul_shroom",         "byg",           "soul_shroom");
    public static final ShroomloinType SYTHIAN_FUNGUS =           registerWithIEDirectory("sythian_fungus",      "byg",           "sythian_fungus");
    public static final ShroomloinType TOADSTOOL =                registerWithIEDirectory("toadstool",           "biomesoplenty", "toadstool");
    public static final ShroomloinType VEIL =                     registerWithIEDirectory("veil_mushroom",       "undergarden",   "veil_mushroom");
    public static final ShroomloinType WARPED =                   registerWithIEDirectory(Items.WARPED_FUNGUS,   MOD_ID,          "warped");
    public static final ShroomloinType WEEPING_MILKCAP =          registerWithIEDirectory("weeping_milkcap",     "byg",           "weeping_milkcap");
    public static final ShroomloinType WOOD_BLEWIT =              registerWithIEDirectory("wood_blewit",         "byg",           "wood_blewit");

    private static ShroomloinType registerWithIEDirectory(String itemName, String namespace, String name) {
        return registerWithIEDirectory(() -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace, itemName)), namespace, name);
    }

    private static ShroomloinType registerWithIEDirectory(@Nullable Item item, String namespace, String name) {
        return registerWithIEDirectory(() -> item, namespace, name);
    }

    private static ShroomloinType registerWithIEDirectory(@Nullable Supplier<Item> item, String namespace, String name) {
        if (name == "glowshroom") {
            return register(new ShroomloinType(item, new ResourceLocation(namespace, name), new ResourceLocation(MOD_ID, "textures/entity/shroomloin/glowshroom_" + namespace + ".png"), new ResourceLocation(MOD_ID, "textures/entity/shroomloin/glowshroom_" + namespace + "_glow.png")));
        }
        return register(new ShroomloinType(item, new ResourceLocation(namespace, name), new ResourceLocation(MOD_ID, "textures/entity/shroomloin/" + name + ".png"), new ResourceLocation(MOD_ID, "textures/entity/shroomloin/" + name + "_glow.png")));
    }

    private static ShroomloinType register(ShroomloinType shroomloinType) {
        SHROOMLOIN_TYPES.add(shroomloinType);
        return shroomloinType;
    }

    public static void registerAll() {
        for (ShroomloinType shroomloinType : SHROOMLOIN_TYPES) {
            ShroomloinType.registerShroomloinType(shroomloinType);
        }
    }
}
