package com.nekomaster1000.infernalexp.client;

import com.nekomaster1000.infernalexp.config.gui.screens.ConfigScreen;
import com.nekomaster1000.infernalexp.init.IEItems;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class InfernalExpansionClient {
    public static void init() {
		// Register GUI Factories
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, screen) -> new ConfigScreen());

		MinecraftForge.EVENT_BUS.addListener((LivingUpdateEvent event) -> DynamicLightingHandler.tick(event.getEntityLiving()));

		ItemModelsProperties.registerProperty(IEItems.GLOWSILK_BOW.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
			if (p_239429_2_ == null) {
				return 0.0F;
			} else {
				return p_239429_2_.getActiveItemStack() != p_239429_0_ ? 0.0F : (float) (p_239429_0_.getUseDuration() - p_239429_2_.getItemInUseCount()) / 20.0F;
			}
		});
		ItemModelsProperties.registerProperty(IEItems.GLOWSILK_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);

		ItemModelsProperties.registerProperty(IEItems.BLINDSIGHT_TONGUE_WHIP.get(), new ResourceLocation("attacking"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);
    }
}
