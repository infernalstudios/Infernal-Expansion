package com.nekomaster1000.infernalexp.events;

import com.nekomaster1000.infernalexp.entities.WarpbeetleEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public void entityJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof PiglinEntity || event.getEntity() instanceof HoglinEntity) {
            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4, new AvoidEntityGoal<>((CreatureEntity) event.getEntity(), WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));
        }
    }

}
