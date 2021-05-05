package com.nekomaster1000.infernalexp.mixin.client;

import com.nekomaster1000.infernalexp.init.IEItems;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.Hand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class MixinPlayerRenderer {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/item/UseAction;"), method = "func_241741_a_(Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/renderer/entity/model/BipedModel$ArmPose;", cancellable = true)
	private static void renderWhipInfernalExpansion(AbstractClientPlayerEntity playerEntity, Hand hand, CallbackInfoReturnable<BipedModel.ArmPose> cir) {
		if (playerEntity.getHeldItem(hand).isItemEqual(IEItems.BLINDSIGHT_TONGUE_WHIP.get().getDefaultInstance())) {
			cir.setReturnValue(BipedModel.ArmPose.THROW_SPEAR);
		}
	}
}
