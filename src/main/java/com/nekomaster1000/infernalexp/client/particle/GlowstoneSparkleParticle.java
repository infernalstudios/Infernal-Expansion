package com.nekomaster1000.infernalexp.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlowstoneSparkleParticle extends SpriteTexturedParticle {

    private GlowstoneSparkleParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z);
        this.motionX = (motionX + rand.nextFloat() - 0.5) / 3;
        this.motionY = (motionY + rand.nextFloat()) / 5;
        this.motionZ = (motionZ + rand.nextFloat() - 0.5) / 3;
        this.particleScale *= 0.75F;
        this.maxAge = 60 + this.rand.nextInt(12);
    }

    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            if (this.motionX > 0.1) this.motionX *= 0.9;
            if (this.motionY > 0.1) this.motionY *= 0.9;
            if (this.motionZ > 0.1) this.motionZ *= 0.9;
        }
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public int getBrightnessForRender(float partialTick) {
        return 15728880;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            GlowstoneSparkleParticle glowstoneSparkleParticle = new GlowstoneSparkleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            glowstoneSparkleParticle.selectSpriteRandomly(this.spriteSet);
            glowstoneSparkleParticle.multiplyParticleScaleBy(0.5F);
            return glowstoneSparkleParticle;
        }
    }
}
