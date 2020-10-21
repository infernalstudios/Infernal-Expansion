package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.WarpbeetleEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof PiglinEntity || event.getEntity() instanceof HoglinEntity) {
            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4, new AvoidEntityGoal<>((CreatureEntity) event.getEntity(), WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));
        }
    }

    //Mob Spawning in pre-existing
    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        if (event.getName().toString().equals("minecraft:nether_wastes")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(ModEntityType.VOLINE.get(), 50, 1, 3));
        } else if (event.getName().toString().equals("minecraft:warped_forest")) {
            event.getSpawns().withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityType.WARPBEETLE.get(), 5000, 1, 1));
        } else if (event.getName().toString().equals("minecraft:soul_sand_valley")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(ModEntityType.EMBODY.get(), 20, 1, 5));
        }
    }
}
