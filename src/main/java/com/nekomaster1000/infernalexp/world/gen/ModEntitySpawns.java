package com.nekomaster1000.infernalexp.world.gen;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.BasaltGiantEntity;
import com.nekomaster1000.infernalexp.entities.BlackstoneDwarfEntity;
import com.nekomaster1000.infernalexp.entities.BlindsightEntity;
import com.nekomaster1000.infernalexp.entities.EmbodyEntity;
import com.nekomaster1000.infernalexp.entities.GlowsilkMothEntity;
import com.nekomaster1000.infernalexp.entities.GlowsquitoEntity;
import com.nekomaster1000.infernalexp.entities.ShroomloinEntity;
import com.nekomaster1000.infernalexp.entities.VolineEntity;
import com.nekomaster1000.infernalexp.entities.WarpbeetleEntity;
import com.nekomaster1000.infernalexp.init.IEEntityTypes;

import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

//import net.minecraft.entity.monster.MonsterEntity;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntitySpawns {

    @SubscribeEvent
    public static void spawnEntities(FMLLoadCompleteEvent event) {
        GlobalEntityTypeAttributes.put(IEEntityTypes.VOLINE.get(), VolineEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(IEEntityTypes.SHROOMLOIN.get(), ShroomloinEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(IEEntityTypes.WARPBEETLE.get(), WarpbeetleEntity.setCustomAttributes().create());
        //GlobalEntityTypeAttributes.put(IEEntityTypes.CEROBEETLE.get(), CerobeetleEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(IEEntityTypes.EMBODY.get(), EmbodyEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(IEEntityTypes.BASALT_GIANT.get(), BasaltGiantEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(IEEntityTypes.BLACKSTONE_DWARF.get(), BlackstoneDwarfEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(IEEntityTypes.GLOWSQUITO.get(), GlowsquitoEntity.setCustomAttributes().create());
        //GlobalEntityTypeAttributes.put(IEEntityTypes.PYRNO.get(), PyrnoEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(IEEntityTypes.BLINDSIGHT.get(), BlindsightEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(IEEntityTypes.GLOWSILK_MOTH.get(), GlowsilkMothEntity.setCustomAttributes().create());
        }
    }
