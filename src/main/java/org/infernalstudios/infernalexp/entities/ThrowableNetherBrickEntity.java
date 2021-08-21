package org.infernalstudios.infernalexp.entities;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.infernalstudios.infernalexp.init.IEEntityTypes;

@MethodsReturnNonnullByDefault
public class ThrowableNetherBrickEntity extends ThrowableBrickEntity {

    public ThrowableNetherBrickEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ThrowableNetherBrickEntity(World world, LivingEntity livingEntity) {
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
