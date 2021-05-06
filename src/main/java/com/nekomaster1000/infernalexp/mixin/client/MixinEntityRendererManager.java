package com.nekomaster1000.infernalexp.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nekomaster1000.infernalexp.access.FireTypeAccess;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(EntityRendererManager.class)
public class MixinEntityRendererManager {

	@ModifyVariable(method = "renderFire", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/model/RenderMaterial;getSprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0), name = "textureatlassprite")
	private TextureAtlasSprite IE_renderCustomFires0(TextureAtlasSprite original, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Entity entityIn) {
		return ((FireTypeAccess) entityIn).getFireType().getSupplier().get().getAssociatedSprite0().getSprite();
	}

	@ModifyVariable(method = "renderFire", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/model/RenderMaterial;getSprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1), name = "textureatlassprite1")
	private TextureAtlasSprite IE_renderCustomFires1(TextureAtlasSprite original, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Entity entityIn) {
		return ((FireTypeAccess) entityIn).getFireType().getSupplier().get().getAssociatedSprite1().getSprite();
	}

}
