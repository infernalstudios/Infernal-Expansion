package com.nekomaster1000.infernalexp.mixin.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nekomaster1000.infernalexp.init.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.math.Tuple3d;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;
import java.util.Iterator;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer {

    @Shadow
    @Final
    private RenderTypeBuffers renderTypeTextures;

    @Shadow
    protected abstract void renderEntity(Entity entityIn, double camX, double camY, double camZ, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn);

    @Shadow protected abstract boolean isRenderEntityOutlines();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;renderEntity(Lnet/minecraft/entity/Entity;DDDFLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;)V", shift = At.Shift.BEFORE), method = "updateCameraAndRender", locals = LocalCapture.CAPTURE_FAILHARD)
    private void onUpdateAndCameraRender(MatrixStack matrixStackIn, float partialTicks, long finishTimeNano, boolean drawBlockOutline, ActiveRenderInfo activeRenderInfoIn, GameRenderer gameRendererIn, LightTexture lightmapIn, Matrix4f projectionIn, CallbackInfo ci, IProfiler iprofiler, Vector3d vector3d, double d0, double d1, double d2, Matrix4f matrix4f, boolean flag, ClippingHelper clippinghelper, float f, boolean flag1, int i, int j, long k, long l, long i1, long j1, long k1, long l1, boolean flag2, IRenderTypeBuffer.Impl irendertypebuffer$impl, Iterator var39, Entity entity, IRenderTypeBuffer irendertypebuffer) {
        if (this.isRenderEntityOutlines() && entity instanceof LivingEntity && ((LivingEntity) entity).isPotionActive(ModEffects.LUMINOUS.get())) {
            changeIRenderTypeBuffer(irendertypebuffer);
        }

    }

    @ModifyVariable(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;renderEntity(Lnet/minecraft/entity/Entity;DDDFLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;)V", shift = At.Shift.BEFORE), method = "updateCameraAndRender", name = "irendertypebuffer")
    private IRenderTypeBuffer changeIRenderTypeBuffer(IRenderTypeBuffer irendertypebuffer){
        OutlineLayerBuffer outlinelayerbuffer = this.renderTypeTextures.getOutlineBufferSource();
        irendertypebuffer = outlinelayerbuffer;
        int i2 = 16755200;
        int j2 = 255;
        int k2 = i2 >> 16 & 255;
        int l2 = i2 >> 8 & 255;
        int i3 = i2 & 255;
        outlinelayerbuffer.setColor(k2, l2, i3, 255);
        return irendertypebuffer;
    }
}

