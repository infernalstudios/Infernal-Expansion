package com.nekomaster1000.infernalexp.events;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.client.entity.render.BasaltGiantRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.BlackstoneDwarfRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.BlindsightRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.EmbodyRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.GlowsilkMothRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.GlowsquitoRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.InfernalPaintingRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.ShroomloinRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.VolineRenderer;
import com.nekomaster1000.infernalexp.client.entity.render.WarpbeetleRenderer;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.init.IEEntityTypes;
import com.nekomaster1000.infernalexp.init.IETileEntityTypes;
import com.nekomaster1000.infernalexp.util.ModSpawnEggItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.tileentity.CampfireTileEntityRenderer;
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
public class ClientEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.VOLINE.get(), VolineRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.SHROOMLOIN.get(), ShroomloinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.WARPBEETLE.get(), WarpbeetleRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.CEROBEETLE.get(), CerobeetleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.EMBODY.get(), EmbodyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.BASALT_GIANT.get(), BasaltGiantRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.BLACKSTONE_DWARF.get(), BlackstoneDwarfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.GLOWSQUITO.get(), GlowsquitoRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.PYRNO.get(), PyrnoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.BLINDSIGHT.get(), BlindsightRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.GLOWSILK_MOTH.get(), GlowsilkMothRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.ASCUS_BOMB.get(), manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.THROWABLE_MAGMA_CREAM.get(), manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.THROWABLE_FIRE_CHARGE.get(), manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.INFERNAL_PAINTING.get(), InfernalPaintingRenderer::new);

		ClientRegistry.bindTileEntityRenderer(IETileEntityTypes.GLOW_CAMPFIRE.get(), CampfireTileEntityRenderer::new);

		RenderTypeLookup.setRenderLayer(IEBlocks.LUMINOUS_FUNGUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(IEBlocks.DULLTHORNS.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(IEBlocks.POTTED_LUMINOUS_FUNGUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(IEBlocks.POTTED_DULLTHORNS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(IEBlocks.POTTED_SHROOMLIGHT_FUNGUS.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(IEBlocks.GLOW_TORCH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(IEBlocks.GLOW_TORCH_WALL.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(IEBlocks.GLOW_CAMPFIRE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(IEBlocks.GLOW_LANTERN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(IEBlocks.GLOW_FIRE.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(IEBlocks.SHROOMLIGHT_FUNGUS.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(IEBlocks.BURIED_BONE.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(IEBlocks.GLOW_GLASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(IEBlocks.GLOW_GLASS_PANE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(IEBlocks.QUARTZ_GLASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(IEBlocks.QUARTZ_GLASS_PANE.get(), RenderType.getCutout());
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPostRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEggItem.initUnaddedEggs();
    }
}
