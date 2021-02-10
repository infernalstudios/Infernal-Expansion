package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.init.IEEntityTypes;
import com.nekomaster1000.infernalexp.init.IEItems;
import com.nekomaster1000.infernalexp.init.IEParticleTypes;
import com.nekomaster1000.infernalexp.init.IEPotions;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class AscusBombEntity extends ProjectileItemEntity implements IRendersAsItem {

    public AscusBombEntity(EntityType<? extends AscusBombEntity> typeIn, World worldIn) {
        super(typeIn, worldIn);
    }

    public AscusBombEntity(World world, LivingEntity livingEntity) {
        super(IEEntityTypes.ASCUS_BOMB.get(), livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return IEItems.ASCUS_BOMB.get();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);

        if (!this.world.isRemote) {
            this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 0.5F);
            this.spawnExplosionCloud();
            this.remove();
            this.spawnLingeringCloud();
        }

    }

    private void spawnExplosionCloud(){
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY() + 0.6, this.getPosZ());
        areaeffectcloudentity.setRadius(0.1F);
        areaeffectcloudentity.setWaitTime(0);
        areaeffectcloudentity.setDuration(10);
        areaeffectcloudentity.setRadiusPerTick(0);
        areaeffectcloudentity.setParticleData(ParticleTypes.EXPLOSION);

        this.world.addEntity(areaeffectcloudentity);
    }

    private void spawnLingeringCloud() {
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 8);
        areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
        areaeffectcloudentity.setPotion(IEPotions.INFECTION.get());
        areaeffectcloudentity.setParticleData(IEParticleTypes.INFECTION.get());

        this.world.addEntity(areaeffectcloudentity);
    }
}
