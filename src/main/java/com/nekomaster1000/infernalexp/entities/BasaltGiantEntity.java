package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.UUID;

import static net.minecraft.util.math.MathHelper.cos;
import static net.minecraft.util.math.MathHelper.sin;

public class BasaltGiantEntity extends CreatureEntity implements IEntityAdditionalSpawnData, IAngerable {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.BASALT, Items.POLISHED_BASALT);
    private static final RangedInteger RANGED_INT = TickRangeConverter.convertRange(20, 39);
    private int attackTimer;
    private int angerTime;
    private UUID angerTarget;

    // Constant values for entity scaling
    private static final float BASE_ENTITY_HEIGHT = 5.0F;
    private static final float MIN_ENTITY_HEIGHT = 4.0F;
    private static final float MAX_ENTITY_HEIGHT = 6.0F;

    private static final DataParameter<Float> SIZE_SCALAR = EntityDataManager.createKey(BasaltGiantEntity.class, DataSerializers.FLOAT);

    public BasaltGiantEntity(EntityType<? extends BasaltGiantEntity> type, World worldIn) {
        super(type, worldIn);

        // Get a random size scale value resulting in a height between the MIN and MAX values specified above
        float size = rand.nextFloat();
        size /= BASE_ENTITY_HEIGHT / (MAX_ENTITY_HEIGHT - MIN_ENTITY_HEIGHT);
        size += MIN_ENTITY_HEIGHT / BASE_ENTITY_HEIGHT;

        this.dataManager.set(SIZE_SCALAR, size);
    }

    public BasaltGiantEntity(EntityType<? extends BasaltGiantEntity> type, World worldIn, float sizeScalar) {
        super(type, worldIn);
        if (sizeScalar != 1)
            this.dataManager.set(SIZE_SCALAR, sizeScalar);
    }

    @Override
    protected void registerData() {
        super.registerData();

        this.dataManager.register(SIZE_SCALAR, 1.0F);
    }

    //ATTRIBUTES
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 40.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 3.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 30.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.40D);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTimer = 10;
            this.playSound(RegistryHandler.basalt_giant_hurt, 1.0F, 1.0F);
        }else {
            super.handleStatusUpdate(id);
        }

    }

    public void livingTick() {
        super.livingTick();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte)4);
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.rand.nextInt((int)f) : f;
        float f2 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f1);
        if (flag) {
            ((LivingEntity)entityIn).applyKnockback(f2 * 0.5F, (double)MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F))));
            entityIn.setMotion(entityIn.getMotion().mul(1.0D, 2.5D, 1.0D));
            this.setMotion(this.getMotion().mul(0.6D, 1.0D, 0.6D));
            this.applyEnchantments(this, entityIn);
        }

        this.playSound(RegistryHandler.basalt_giant_hurt, 1.0F, 1.0F);
        return flag;
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    //BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 0.6D, 32.0F));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, MagmaCubeEntity.class, true, false));
    }

    public float getSizeScalar() {
        return this.dataManager.get(SIZE_SCALAR);
    }


    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeFloat(getSizeScalar());
    }

    @Override
    public void readSpawnData(PacketBuffer buffer) {
        this.dataManager.set(SIZE_SCALAR, buffer.readFloat());
    }

    //EXP POINTS
    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 2 + this.world.rand.nextInt(2);
    }

    //SOUNDS
    @Override
    protected SoundEvent getAmbientSound() { return RegistryHandler.basalt_giant_ambient; }
    @Override
    protected SoundEvent getDeathSound() { return RegistryHandler.basalt_giant_death; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return RegistryHandler.basalt_giant_hurt; }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    public boolean isImmuneToFire() {
        return true;
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngerTime(int time) {
        this.angerTime = time;
    }

    @Nullable
    @Override
    public UUID getAngerTarget() {
        return this.angerTarget;
    }

    @Override
    public void setAngerTarget(@Nullable UUID target) {
        this.angerTarget = target;
    }

    @Override
    public void func_230258_H__() {
        this.setAngerTime(RANGED_INT.getRandomWithinRange(this.rand));
    }
}
