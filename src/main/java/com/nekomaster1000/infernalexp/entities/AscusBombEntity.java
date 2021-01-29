package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.init.ModEffects;
import com.nekomaster1000.infernalexp.init.ModEntityTypes;
import com.nekomaster1000.infernalexp.init.ModItems;
import com.nekomaster1000.infernalexp.init.ModParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class AscusBombEntity extends ProjectileItemEntity implements IRendersAsItem {

    public AscusBombEntity(EntityType<? extends AscusBombEntity> typeIn, World worldIn) {
        super(typeIn, worldIn);
    }

    public AscusBombEntity(World world, LivingEntity livingEntity) {
        super(ModEntityTypes.ASCUS_BOMB.get(), livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.ASCUS_BOMB.get();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);

        for (int i = 0; i < 16; i++) {
            this.world.addParticle(ModParticleTypes.INFECTION.get(), this.getPosXRandom(5), this.getPosYRandom(), this.getPosZRandom(5), 0, 0, 0);
        }

        if (!this.world.isRemote) {
            Entity entity = result.getType() == RayTraceResult.Type.ENTITY ? ((EntityRayTraceResult) result).getEntity() : null;

            AxisAlignedBB axisAlignedBB = this.getBoundingBox().grow(4, 2, 4);
            List<LivingEntity> livingEntities = this.world.getEntitiesWithinAABB(LivingEntity.class, axisAlignedBB);

            if (!livingEntities.isEmpty()) {
                for (LivingEntity livingEntity : livingEntities) {
                    double distanceSq = this.getDistanceSq(livingEntity);

                    if (distanceSq < 16) {
                        double durationMultiplier = 1 - Math.sqrt(distanceSq) / 4;

                        if (livingEntity == entity) {
                            durationMultiplier = 1;
                        }

                        int duration = (int) (durationMultiplier * 600 + 0.5);

                        livingEntity.addPotionEffect(new EffectInstance(ModEffects.INFECTION.get(), duration, 0, false, false));
                    }
                }
            }
        }
    }
}
