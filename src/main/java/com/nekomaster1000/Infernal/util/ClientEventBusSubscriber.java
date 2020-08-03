package com.nekomaster1000.Infernal.util;

import com.nekomaster1000.Infernal.InfernalExpansion;
import com.nekomaster1000.Infernal.client.entity.render.GlowsquitoRenderer;
import com.nekomaster1000.Infernal.client.entity.render.PyrnoRenderer;
import com.nekomaster1000.Infernal.client.entity.render.VolineRenderer;
import com.nekomaster1000.Infernal.client.entity.render.WarpbeetleRenderer;
import com.nekomaster1000.Infernal.init.ModEntityType;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.GLOWSQUITO.get(), GlowsquitoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.VOLINE.get(), VolineRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.PYRNO.get(), PyrnoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.WARPBEETLE.get(), WarpbeetleRenderer::new);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPostRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEggItem.initUnaddedEggs();
    }
}
