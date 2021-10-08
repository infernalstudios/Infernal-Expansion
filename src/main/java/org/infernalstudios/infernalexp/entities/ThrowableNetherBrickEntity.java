package org.infernalstudios.infernalexp.entities;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.infernalstudios.infernalexp.init.IEEntityTypes;

@MethodsReturnNonnullByDefault
public class ThrowableNetherBrickEntity extends ThrowableBrickEntity {

    public ThrowableNetherBrickEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public ThrowableNetherBrickEntity(Level world, LivingEntity livingEntity) {
        super(IEEntityTypes.THROWABLE_NETHER_BRICK.get(), livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.NETHER_BRICK;
    }

    @Override
    protected double getSpeedMultiplier() {
        return 0.69D;
    }

    @Override
    protected float getDamage() {
        return 3.0F;
    }
}
