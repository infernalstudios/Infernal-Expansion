package com.nekomaster1000.infernalexp.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.nekomaster1000.infernalexp.access.FireTypeAccess;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OverlayRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

@Mixin(OverlayRenderer.class)
public class MixinOverlayRenderer {

	@ModifyVariable(method = "renderFire", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/model/RenderMaterial;getSprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0), name = "textureatlassprite")
	private static TextureAtlasSprite IE_renderCustomFiresOverlay(TextureAtlasSprite original, Minecraft minecraftIn, MatrixStack matrixStackIn) {
		return ((FireTypeAccess) minecraftIn.player).getFireType().getSupplier().get().getAssociatedSprite1().getSprite();
	}

}
