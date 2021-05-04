package com.nekomaster1000.infernalexp.client;

import com.nekomaster1000.infernalexp.config.gui.screens.ConfigScreen;
import com.nekomaster1000.infernalexp.init.IEItems;
import com.nekomaster1000.infernalexp.items.IWhipItem
    ;
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

		ItemModelsProperties.registerProperty(IEItems.GLOWSILK_BOW.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
			if (livingEntity == null) {
				return 0.0F;
			} else {
				return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
			}
		});
		ItemModelsProperties.registerProperty(IEItems.GLOWSILK_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);

		ItemModelsProperties.registerProperty(IEItems.BLINDSIGHT_TONGUE_WHIP.get(), new ResourceLocation("attack_frame"), (itemStack, clientWorld, livingEntity) -> 
			livingEntity == null || livingEntity.getHeldItemMainhand() != itemStack ?
				0 : (int) (((IWhipItem) itemStack.getItem()).getTicksSinceAttack() / 6.0F)
		);

		ItemModelsProperties.registerProperty(IEItems.BLINDSIGHT_TONGUE_WHIP.get(), new ResourceLocation("attacking"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && (((IWhipItem) itemStack.getItem()).getAttacking() || ((IWhipItem) itemStack.getItem()).getCharging()) && livingEntity.getHeldItemMainhand() == itemStack ? 1.0F : 0.0F);
	}
}
