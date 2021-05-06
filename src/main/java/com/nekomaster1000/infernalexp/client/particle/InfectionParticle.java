package com.nekomaster1000.infernalexp.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class InfectionParticle extends SpriteTexturedParticle {
    private static final Random random = new Random();
    private final IAnimatedSprite spriteWithAge;

    protected InfectionParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ, IAnimatedSprite spriteWithAge) {
        super(world, x, y, z, 0.5 - Math.random(), motionY, 0.5 - Math.random());

        this.spriteWithAge = spriteWithAge;
        this.motionY *= -0.1;

        if (motionX == 0 && motionY == 0) {
            this.motionX *= 0.1;
            this.motionZ *= 0.1;
        }

        this.particleScale *= 0.75;
        this.maxAge = (int) (16 / (Math.random() * 0.8 + 0.2));
        this.canCollide = false;
        this.selectSpriteWithAge(spriteWithAge);

        this.particleRed = 0.5f;
        this.particleGreen = (float) Math.random() * 0.1f;
        this.particleBlue = (float) Math.random() * 0.1f;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.selectSpriteWithAge(this.spriteWithAge);
            this.motionY -= 0.001;
            this.move(this.motionX, this.motionY, this.motionZ);

            if (this.posY == this.prevPosY) {
                this.motionX *= 1.1;
                this.motionZ *= 1.1;
            }

            this.motionX *= 0.96;
            this.motionY *= 0.96;
            this.motionZ *= 0.96;

            if (this.onGround) {
                this.motionX *= 0.7;
                this.motionZ *= 0.7;
            }

            this.particleRed = 0.7f + (0.2f * MathHelper.sin(this.age / 10f));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            InfectionParticle infectionParticle = new InfectionParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
//            infectionParticle.setColor(0.8f, 0, 0);

            return infectionParticle;
        }
    }
}
