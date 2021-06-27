package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IESoundEvents {

	// SOUNDS
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, InfernalExpansion.MOD_ID);

	// VOLINE
	public static final RegistryObject<SoundEvent> VOLINE_AMBIENT = add("entity.voline.ambient");
	public static final RegistryObject<SoundEvent> VOLINE_HURT = add("entity.voline.hurt");

	// SHROOMLOIN
	public static final RegistryObject<SoundEvent> SHROOMLOIN_AMBIENT = add("entity.shroomloin.ambient");
	public static final RegistryObject<SoundEvent> SHROOMLOIN_HURT = add("entity.shroomloin.hurt");
	public static final RegistryObject<SoundEvent> SHROOMLOIN_DEATH = add("entity.shroomloin.death");

	// WARPBEETLE
	public static final RegistryObject<SoundEvent> WARPBEETLE_AMBIENT = add("entity.warpbeetle.ambient");
	public static final RegistryObject<SoundEvent> WARPBEETLE_HURT = add("entity.warpbeetle.hurt");
	public static final RegistryObject<SoundEvent> WARPBEETLE_DEATH = add("entity.warpbeetle.death");

	// CEROBEETLE
	public static final RegistryObject<SoundEvent> CEROBEETLE_AMBIENT = add("entity.cerobeetle.ambient");
	public static final RegistryObject<SoundEvent> CEROBEETLE_HURT = add("entity.cerobeetle.hurt");
	public static final RegistryObject<SoundEvent> CEROBEETLE_DEATH = add("entity.cerobeetle.death");
	public static final RegistryObject<SoundEvent> CEROBEETLE_ROAR = add("entity.cerobeetle.roar");

	// EMBODY
	public static final RegistryObject<SoundEvent> EMBODY_AMBIENT = add("entity.embody.ambient");
	public static final RegistryObject<SoundEvent> EMBODY_HURT = add("entity.embody.hurt");
	public static final RegistryObject<SoundEvent> EMBODY_DEATH = add("entity.embody.death");

	// GIANT
	public static final RegistryObject<SoundEvent> BASALT_GIANT_AMBIENT = add("entity.basalt_giant.ambient");
	public static final RegistryObject<SoundEvent> BASALT_GIANT_HURT = add("entity.basalt_giant.hurt");
	public static final RegistryObject<SoundEvent> BASALT_GIANT_DEATH = add("entity.basalt_giant.death");

	// SKELETAL PIGLIN
	public static final RegistryObject<SoundEvent> SKELETAL_PIGLIN_AMBIENT = add("entity.skeletal_piglin.ambient");
	public static final RegistryObject<SoundEvent> SKELETAL_PIGLIN_HURT = add("entity.skeletal_piglin.hurt");
	public static final RegistryObject<SoundEvent> SKELETAL_PIGLIN_DEATH = add("entity.skeletal_piglin.death");

	// GLOWSQUITO
	public static final RegistryObject<SoundEvent> GLOWSQUITO_LOOP = add("entity.glowsquito.loop");
	public static final RegistryObject<SoundEvent> GLOWSQUITO_HURT = add("entity.glowsquito.hurt");
	public static final RegistryObject<SoundEvent> GLOWSQUITO_DEATH = add("entity.glowsquito.death");

	// BLINDSIGHT
	public static final RegistryObject<SoundEvent> BLINDSIGHT_AMBIENT = add("entity.blindsight.ambient");
	public static final RegistryObject<SoundEvent> BLINDSIGHT_HURT = add("entity.blindsight.hurt");
	public static final RegistryObject<SoundEvent> BLINDSIGHT_DEATH = add("entity.blindsight.death");

	// BLACKSTONE_DWARF
	public static final RegistryObject<SoundEvent> BLACKSTONE_DWARF_AMBIENT = add("entity.dwarf.ambient");
	public static final RegistryObject<SoundEvent> BLACKSTONE_DWARF_HURT = add("entity.dwarf.hurt");
	public static final RegistryObject<SoundEvent> BLACKSTONE_DWARF_DEATH = add("entity.dwarf.death");

    // GLOWSILK_MOTH
    public static final RegistryObject<SoundEvent> GLOWSILK_MOTH_AMBIENT = add("entity.glowsilk_moth.ambient");
    public static final RegistryObject<SoundEvent> GLOWSILK_MOTH_HURT = add("entity.glowsilk_moth.hurt");
    public static final RegistryObject<SoundEvent> GLOWSILK_MOTH_DEATH = add("entity.glowsilk_moth.death");

	// GLAW
	public static final RegistryObject<SoundEvent> GLAW_AMBIENT = add("entity.glaw.ambient");
	public static final RegistryObject<SoundEvent> GLAW_HURT = add("entity.glaw.hurt");
	public static final RegistryObject<SoundEvent> GLAW_DEATH = add("entity.glaw.death");

	// SMOLT
	public static final RegistryObject<SoundEvent> SMOLT_AMBIENT = add("entity.smolt.ambient");
	public static final RegistryObject<SoundEvent> SMOLT_HURT = add("entity.smolt.hurt");
	public static final RegistryObject<SoundEvent> SMOLT_DEATH = add("entity.smolt.death");

	// PYRNO
	public static final RegistryObject<SoundEvent> PYRNO_AMBIENT = add("entity.pyrno.ambient");
	public static final RegistryObject<SoundEvent> PYRNO_HURT = add("entity.pyrno.hurt");
	public static final RegistryObject<SoundEvent> PYRNO_DEATH = add("entity.pyrno.death");

	// GLOWSTONE CANYON
	public static final RegistryObject<SoundEvent> AMBIENT_GLOWSTONE_CANYON_LOOP = add("ambient.glowstone_canyon.loop");
	public static final RegistryObject<SoundEvent> AMBIENT_GLOWSTONE_CANYON_MOOD = add("ambient.glowstone_canyon.mood");
	public static final RegistryObject<SoundEvent> AMBIENT_GLOWSTONE_CANYON_ADDITIONS = add("ambient.glowstone_canyon.additions");
	public static final RegistryObject<SoundEvent> MUSIC_NETHER_GLOWSTONE_CANYON = add("music.nether.glowstone_canyon");

	// RECORD DISCS
	public static final RegistryObject<SoundEvent> MUSIC_DISC_SOUL_SPUNK = add("record.soul_spunk");
	public static final RegistryObject<SoundEvent> MUSIC_DISC_FLUSH = add("record.flush");
	
    // GLOWSTONE RECHARGE
    public static final RegistryObject<SoundEvent> GLOWSTONE_RECHARGE = add("block.glowstone.recharge");

	// DULLSTONE
	public static final RegistryObject<SoundEvent> DULLSTONE_BREAK = add("block.dullstone.break");
	public static final RegistryObject<SoundEvent> DULLSTONE_STEP = add("block.dullstone.step");
	public static final RegistryObject<SoundEvent> DULLSTONE_PLACE = add("block.dullstone.place");
	public static final RegistryObject<SoundEvent> DULLSTONE_HIT = add("block.dullstone.hit");
	public static final RegistryObject<SoundEvent> DULLSTONE_FALL = add("block.dullstone.fall");

    // SOUL STONE
    public static final RegistryObject<SoundEvent> SOUL_STONE_BREAK = add("block.soul_stone.break");

	// SOUND TYPES
	public static final SoundType DULLSTONE_TYPE = new ForgeSoundType(1.0F, 1.0F, () -> DULLSTONE_BREAK.get(), () -> DULLSTONE_STEP.get(), () -> DULLSTONE_PLACE.get(), () -> DULLSTONE_HIT.get(), () -> DULLSTONE_FALL.get());
	public static final SoundType DIMSTONE_TYPE = new ForgeSoundType(1.0F, 1.0F, () -> SoundEvents.BLOCK_GLASS_BREAK, () -> DULLSTONE_STEP.get(), () -> SoundEvents.BLOCK_GLASS_PLACE, () -> SoundEvents.BLOCK_GLASS_HIT, () -> SoundEvents.BLOCK_GLASS_FALL);
    public static final SoundType SOUL_STONE_TYPE = new ForgeSoundType(1.0F, 1.0F, () -> SOUL_STONE_BREAK.get(), () -> SoundEvents.BLOCK_SOUL_SOIL_STEP, () -> SoundEvents.BLOCK_SOUL_SOIL_PLACE, () -> SoundEvents.BLOCK_SOUL_SOIL_HIT, () -> SoundEvents.BLOCK_SOUL_SOIL_FALL);

    public static final RegistryObject<SoundEvent> add(String id) {
		ResourceLocation realId = new ResourceLocation(InfernalExpansion.MOD_ID, id);
		return SOUND_EVENTS.register(id, () -> new SoundEvent(realId));
	}

	public static void register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
		InfernalExpansion.LOGGER.info("Infernal Expansion: Sound Events Registered!");
	}
}
