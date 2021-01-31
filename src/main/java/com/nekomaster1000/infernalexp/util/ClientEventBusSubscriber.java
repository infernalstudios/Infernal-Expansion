package com.nekomaster1000.infernalexp.util;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.render.BasaltGiantRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.BlackstoneDwarfRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.BlindsightRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.CerobeetleRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.EmbodyRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.GlowsquitoRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.PyrnoRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.ShroomloinRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.SkeletalPiglinRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.VolineRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.WarpbeetleRenderer;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import com.nekomaster1000.infernalexp.init.ModEntityTypes;
import com.nekomaster1000.infernalexp.init.ModTileEntityTypes;
import com.nekomaster1000.infernalexp.tileentities.renderer.GlowCampfireTileEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
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
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.VOLINE.get(), VolineRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SHROOMLOIN.get(), ShroomloinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WARPBEETLE.get(), WarpbeetleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.CEROBEETLE.get(), CerobeetleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.EMBODY.get(), EmbodyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BASALT_GIANT.get(), BasaltGiantRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BLACKSTONE_DWARF.get(), BlackstoneDwarfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GLOWSQUITO.get(), GlowsquitoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SKELETAL_PIGLIN.get(), SkeletalPiglinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.PYRNO.get(), PyrnoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BLINDSIGHT.get(), BlindsightRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ASCUS_BOMB.get(), manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));

        ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.GLOW_CAMPFIRE.get(), GlowCampfireTileEntityRenderer::new);

        RenderTypeLookup.setRenderLayer(ModBlocks.LUMINOUS_FUNGUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.DULLTHORNS.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_LUMINOUS_FUNGUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_DULLTHORNS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_SHROOMLIGHT_FUNGUS.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(ModBlocks.GLOW_TORCH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOW_WALL_TORCH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOW_CAMPFIRE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOW_LANTERN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GLOW_FIRE.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(ModBlocks.SHROOMLIGHT_FUNGUS.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(ModBlocks.BURIED_BONE.get(), RenderType.getCutout());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPostRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEggItem.initUnaddedEggs();
    }
}
