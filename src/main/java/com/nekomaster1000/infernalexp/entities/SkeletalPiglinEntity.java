package com.nekomaster1000.infernalexp.entities;

import com.nekomaster1000.infernalexp.init.IEBiomes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class SkeletalPiglinEntity extends MonsterEntity implements IAngerable {

    private static final RangedInteger RANGED_INT = TickRangeConverter.convertRange(20, 39);
    private int angerTime;
    private UUID angerTarget;
    protected final Random rand = new Random();

    public SkeletalPiglinEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    // ATTRIBUTES
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    protected boolean shouldBurnInDay() {
        return true;
    }

    // BEHAVIOR
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 0.6D));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, 0.5d));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new AvoidEntityGoal(this, EmbodyEntity.class, 16.0F, 0.85D, 1.1D));
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void livingTick() {
        if (this.isAlive()) {
            boolean flag = this.shouldBurnInDay() && this.isInDaylight();
            if (flag) {
                ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.HEAD);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isDamageable()) {
                        itemstack.setDamage(itemstack.getDamage() + this.rand.nextInt(2));
                        if (itemstack.getDamage() >= itemstack.getMaxDamage()) {
                            this.sendBreakAnimation(EquipmentSlotType.HEAD);
                            this.setItemStackToSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    this.setFire(8);
                }
            }
        }

        super.livingTick();
    }

    // EXPERIENCE POINTS

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 2 + this.world.rand.nextInt(2);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SKELETON_STEP;
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

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        float f = difficultyIn.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);

        if (world.getBiome(this.getPosition()).getRegistryName().equals(IEBiomes.GLOWSTONE_CANYON.get().getRegistryName())) {
            getShovelType(Items.GOLDEN_SHOVEL, Items.IRON_SHOVEL, Items.STONE_SHOVEL);
        } else if (world.getBiome(this.getPosition()).getRegistryName().equals(IEBiomes.DELTA_SHORES.get().getRegistryName())) {
            getShovelType(Items.IRON_SHOVEL, Items.STONE_SHOVEL, Items.GOLDEN_SHOVEL);
        } else if (world.getBiome(this.getPosition()).getRegistryName().equals(Biomes.SOUL_SAND_VALLEY.getLocation())) {
            getShovelType(Items.STONE_SHOVEL, Items.GOLDEN_SHOVEL, Items.IRON_SHOVEL);
        }

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    private void getShovelType(Item primaryShovel, Item secondaryShovel, Item tertiaryShovel) {
        int shovelChance = this.rand.nextInt(10);

        if (shovelChance <= 2) {
            shovelChance = this.rand.nextInt(10);
            if (shovelChance <= 7) {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(primaryShovel));
            } else if (shovelChance <= 8) {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(secondaryShovel));
            } else {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(tertiaryShovel));
            }
        }
    }
}
