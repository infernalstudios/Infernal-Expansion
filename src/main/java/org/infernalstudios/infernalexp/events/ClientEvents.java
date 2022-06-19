/*
 * Copyright 2022 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.tileentity.CampfireTileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.client.entity.render.BasaltGiantRenderer;
import org.infernalstudios.infernalexp.client.entity.render.BlackstoneDwarfRenderer;
import org.infernalstudios.infernalexp.client.entity.render.BlindsightRenderer;
import org.infernalstudios.infernalexp.client.entity.render.EmbodyRenderer;
import org.infernalstudios.infernalexp.client.entity.render.GlowsilkMothRenderer;
import org.infernalstudios.infernalexp.client.entity.render.GlowsquitoRenderer;
import org.infernalstudios.infernalexp.client.entity.render.InfernalPaintingRenderer;
import org.infernalstudios.infernalexp.client.entity.render.ShroomloinRenderer;
import org.infernalstudios.infernalexp.client.entity.render.VolineRenderer;
import org.infernalstudios.infernalexp.client.entity.render.WarpbeetleRenderer;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IETileEntityTypes;

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
		RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.THROWABLE_BRICK.get(), manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(IEEntityTypes.THROWABLE_NETHER_BRICK.get(), manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
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
        RenderTypeLookup.setRenderLayer(IEBlocks.PLANTED_QUARTZ.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(IEBlocks.GLOW_GLASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(IEBlocks.GLOW_GLASS_PANE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(IEBlocks.QUARTZ_GLASS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(IEBlocks.QUARTZ_GLASS_PANE.get(), RenderType.getCutout());
    }

}
