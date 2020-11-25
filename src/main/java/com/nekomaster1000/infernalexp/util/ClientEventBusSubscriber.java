package com.nekomaster1000.infernalexp.util;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.render.*;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import com.nekomaster1000.infernalexp.init.ModEntityType;
import com.nekomaster1000.infernalexp.init.ModTileEntityTypes;
import com.nekomaster1000.infernalexp.tileentities.renderer.GlowCampfireTileEntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.VOLINE.get(), VolineRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.SHROOMLOIN.get(), ShroomloinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.WARPBEETLE.get(), WarpbeetleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.CEROBEETLE.get(), CerobeetleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.EMBODY.get(), EmbodyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.BASALT_GIANT.get(), BasaltGiantRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.BASALT_TITAN.get(), BasaltTitanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.GLOWSQUITO.get(), GlowsquitoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.SKELETAL_PIGLIN.get(), SkeletalPiglinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.PYRNO.get(), PyrnoRenderer::new);

        ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.GLOW_CAMPFIRE_TILE_ENTITY.get(), GlowCampfireTileEntityRenderer::new);

        RenderTypeLookup.setRenderLayer(ModBlocks.LUMINOUS_FUNGUS.get(), RenderType.getCutout());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPostRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEggItem.initUnaddedEggs();
    }
}
