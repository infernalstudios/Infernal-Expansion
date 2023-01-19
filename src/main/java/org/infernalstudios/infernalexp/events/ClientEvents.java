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

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.client.entity.model.BasaltGiantModel;
import org.infernalstudios.infernalexp.client.entity.model.BlackstoneDwarfModel;
import org.infernalstudios.infernalexp.client.entity.model.BlindsightModel;
import org.infernalstudios.infernalexp.client.entity.model.EmbodyModel;
import org.infernalstudios.infernalexp.client.entity.model.GlowsilkMothModel;
import org.infernalstudios.infernalexp.client.entity.model.GlowsquitoModel;
import org.infernalstudios.infernalexp.client.entity.model.ShroomloinModel;
import org.infernalstudios.infernalexp.client.entity.model.VolineModel;
import org.infernalstudios.infernalexp.client.entity.model.WarpbeetleModel;
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
import org.infernalstudios.infernalexp.init.IEBlockEntityTypes;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEItems;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(IEEntityTypes.VOLINE.get(), VolineRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.SHROOMLOIN.get(), ShroomloinRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.WARPBEETLE.get(), WarpbeetleRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.EMBODY.get(), EmbodyRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.BASALT_GIANT.get(), BasaltGiantRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.BLACKSTONE_DWARF.get(), BlackstoneDwarfRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.GLOWSQUITO.get(), GlowsquitoRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.BLINDSIGHT.get(), BlindsightRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.GLOWSILK_MOTH.get(), GlowsilkMothRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.ASCUS_BOMB.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.THROWABLE_MAGMA_CREAM.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.THROWABLE_FIRE_CHARGE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.THROWABLE_BRICK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.THROWABLE_NETHER_BRICK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(IEEntityTypes.INFERNAL_PAINTING.get(), InfernalPaintingRenderer::new);

        event.registerBlockEntityRenderer(IEBlockEntityTypes.GLOW_CAMPFIRE.get(), CampfireRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(VolineModel.LAYER_LOCATION, VolineModel::createBodyLayer);
        event.registerLayerDefinition(ShroomloinModel.LAYER_LOCATION, ShroomloinModel::createBodyLayer);
        event.registerLayerDefinition(WarpbeetleModel.LAYER_LOCATION, WarpbeetleModel::createBodyLayer);
        event.registerLayerDefinition(EmbodyModel.LAYER_LOCATION, EmbodyModel::createBodyLayer);
        event.registerLayerDefinition(BasaltGiantModel.LAYER_LOCATION, BasaltGiantModel::createBodyLayer);
        event.registerLayerDefinition(BlackstoneDwarfModel.LAYER_LOCATION, BlackstoneDwarfModel::createBodyLayer);
        event.registerLayerDefinition(GlowsquitoModel.LAYER_LOCATION, GlowsquitoModel::createBodyLayer);
        event.registerLayerDefinition(BlindsightModel.LAYER_LOCATION, BlindsightModel::createBodyLayer);
        event.registerLayerDefinition(GlowsilkMothModel.LAYER_LOCATION, GlowsilkMothModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.LUMINOUS_FUNGUS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.DULLTHORNS.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(IEBlocks.POTTED_LUMINOUS_FUNGUS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.POTTED_DULLTHORNS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.POTTED_SHROOMLIGHT_FUNGUS.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(IEBlocks.GLOW_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.GLOW_TORCH_WALL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.GLOW_CAMPFIRE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.GLOW_LANTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.GLOW_FIRE.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(IEBlocks.SHROOMLIGHT_FUNGUS.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(IEBlocks.BURIED_BONE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.PLANTED_QUARTZ.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(IEBlocks.GLOW_GLASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.GLOW_GLASS_PANE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.QUARTZ_GLASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IEBlocks.QUARTZ_GLASS_PANE.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public void glowsilkBowFOVModifier(FOVModifierEvent event) {
        if (event.getEntity().isUsingItem() && event.getEntity().getUseItem().is(IEItems.GLOWSILK_BOW.get())) {
            float fovModifier = event.getEntity().getTicksUsingItem() / 20.0F;

            if (fovModifier > 1.0F)
                fovModifier = 1.0F;

            else
                fovModifier *= fovModifier;

            event.setNewfov(event.getFov() * (1.0F - (fovModifier * 0.15F)));
        }
    }

}
