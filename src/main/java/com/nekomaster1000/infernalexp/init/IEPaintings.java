package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IEPaintings {
	public static DeferredRegister<PaintingType> PAINTING_TYPES = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, InfernalExpansion.MOD_ID);

	public static RegistryObject<PaintingType> THE_FALLEN_ONES = PAINTING_TYPES.register("the_fallen_ones", () -> new PaintingType(48, 64));
	public static RegistryObject<PaintingType> CHILLING_ISLES = PAINTING_TYPES.register("chilling_isles", () -> new PaintingType(48, 48));
	public static RegistryObject<PaintingType> VOLINE = PAINTING_TYPES.register("voline", () -> new PaintingType(16, 16));
	public static RegistryObject<PaintingType> DA_SALT = PAINTING_TYPES.register("da_salt", () -> new PaintingType(64, 32));
    public static RegistryObject<PaintingType> GLOWSTONE_CANYON_PAINTING = PAINTING_TYPES.register("glowstone_canyon_painting", () -> new PaintingType(64, 64));
    public static RegistryObject<PaintingType> SOUL_BY_SOUL = PAINTING_TYPES.register("soul_by_soul", () -> new PaintingType(80, 48));
    public static RegistryObject<PaintingType> GARRY = PAINTING_TYPES.register("pineapple_under_the_lava_sea", () -> new PaintingType(64, 64));
    public static RegistryObject<PaintingType> SHROOM_DUALITY = PAINTING_TYPES.register("shroom_duality", () -> new PaintingType(16, 16));
    public static RegistryObject<PaintingType> PIGS_GREED = PAINTING_TYPES.register("pigs_greed", () -> new PaintingType(16, 16));
    public static RegistryObject<PaintingType> SOUL_HOLE = PAINTING_TYPES.register("soul_hole", () -> new PaintingType(16, 16));
    public static RegistryObject<PaintingType> GLOW_LIKE_HELLY = PAINTING_TYPES.register("glow_like_helly", () -> new PaintingType(16, 16));


	public static void register(IEventBus eventBus) {
		PAINTING_TYPES.register(eventBus);
		InfernalExpansion.LOGGER.info("Infernal Expansion: Painting Types Registered!");
	}
}
