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
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.UUID;

public class BasaltGiantEntity extends CreatureEntity implements IEntityAdditionalSpawnData, IAngerable {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.BASALT, Items.POLISHED_BASALT);
    private static final RangedInteger RANGED_INT = TickRangeConverter.convertRange(20, 39);
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
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 30.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.40D);
    }

    //BEHAVIOUR
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(1, new MoveTowardsTargetGoal(this, 0.6D, 32.0F));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.6D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
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
    protected SoundEvent getAmbientSound() { return RegistryHandler.voline_ambient; }
    @Override
    protected SoundEvent getDeathSound() { return RegistryHandler.voline_hurt; }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return RegistryHandler.voline_hurt;
    }
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
