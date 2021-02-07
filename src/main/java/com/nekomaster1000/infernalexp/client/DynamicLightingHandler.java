package com.nekomaster1000.infernalexp.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.init.ModEffects;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DynamicLightingHandler {
    private static final Minecraft MinecraftInstance = Minecraft.getInstance();
    public static final Map<BlockPos, LightData> LIGHT_SOURCES = new ConcurrentHashMap<>();

    public static void tick(LivingEntity entity) {
        if (entity != null && MinecraftInstance.player != null && MinecraftInstance.player.ticksExisted % InfernalExpansionConfig.luminousRefreshRate == 0) {
            if (shouldGlow(entity)) {
                LIGHT_SOURCES.put(entity.getPosition(), new LightData());                
            }
            if (entity == MinecraftInstance.player) {
                LIGHT_SOURCES.forEach((pos, data) -> {
                    if (data.time == 0) {
                        data.shouldKeep = false;
                    }
                    if (data.time == 20 || !data.shouldKeep) {
                        MinecraftInstance.world.getChunkProvider().getLightManager().checkBlock(pos);                        
                    }
                    data.time -= InfernalExpansionConfig.luminousRefreshRate;
                });
                LIGHT_SOURCES.entrySet().removeIf(entry -> !entry.getValue().shouldKeep);
            }
        }
    }

    public static boolean shouldGlow(LivingEntity entity) {
        EffectInstance effect = entity.getActivePotionEffect(ModEffects.LUMINOUS.get());
        if (effect != null) {
            return effect.getPotion() == ModEffects.LUMINOUS.get();
        }            
        return false;
    }
    
    public static class LightData {
        public boolean shouldKeep = true;
        public int time = 20;
    }
}
