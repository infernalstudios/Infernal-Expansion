package com.nekomaster1000.infernalexp.world.gen;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.*;
import com.nekomaster1000.infernalexp.init.ModEntityTypes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

//import net.minecraft.entity.monster.MonsterEntity;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntitySpawns {

    @SubscribeEvent
    public static void spawnEntities(FMLLoadCompleteEvent event) {
        GlobalEntityTypeAttributes.put(ModEntityTypes.VOLINE.get(), VolineEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.SHROOMLOIN.get(), ShroomloinEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.WARPBEETLE.get(), WarpbeetleEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.CEROBEETLE.get(), CerobeetleEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.EMBODY.get(), EmbodyEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.BASALT_GIANT.get(), BasaltGiantEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.BLACKSTONE_DWARF.get(), BlackstoneDwarfEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.GLOWSQUITO.get(), GlowsquitoEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.SKELETAL_PIGLIN.get(), SkeletalPiglinEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.PYRNO.get(), PyrnoEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ModEntityTypes.BLINDSIGHT.get(), BlindsightEntity.setCustomAttributes().create());
        }
    }
