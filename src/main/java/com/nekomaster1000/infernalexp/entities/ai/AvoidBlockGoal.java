package com.nekomaster1000.infernalexp.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.tags.ITag;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class AvoidBlockGoal extends Goal {

    protected final SlimeEntity entity;
    protected Optional<BlockPos> avoidBlockPos;
    protected final int avoidDistance;
    protected final SlimeEntity.MoveHelperController controller;
    protected final ITag avoidBlocks;

    public AvoidBlockGoal(SlimeEntity entityIn, ITag.INamedTag<Block> blocksToAvoidIn, int avoidDistanceIn) {
        this(entityIn, blocksToAvoidIn, (p_200828_0_) -> {
            return true;
        }, avoidDistanceIn, EntityPredicates.CAN_AI_TARGET::test);
    }

    private AvoidBlockGoal(SlimeEntity entityIn, ITag.INamedTag<Block> blocksToAvoidIn, Predicate<LivingEntity> targetPredicate, int distance, Predicate<LivingEntity> p_i48859_9_) {
        this.entity = entityIn;
        this.avoidBlocks = blocksToAvoidIn;
        this.avoidDistance = distance;
        this.controller = (SlimeEntity.MoveHelperController) entityIn.getMoveHelper();
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        this.avoidBlockPos = BlockPos.getClosestMatchingPosition(this.entity.getPosition(), this.avoidDistance, 4, (pos) -> this.entity.world.getBlockState(pos).isIn(avoidBlocks));
        return avoidBlockPos.isPresent();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
    }

    @Override
    public boolean shouldContinueExecuting() {
        this.avoidBlockPos = BlockPos.getClosestMatchingPosition(this.entity.getPosition(), this.avoidDistance, 4, (pos) -> this.entity.world.getBlockState(pos).isIn(avoidBlocks));

        return this.avoidBlockPos.isPresent() && this.entity.getDistanceSq(this.avoidBlockPos.get().getX(),
            this.avoidBlockPos.get().getY(), this.avoidBlockPos.get().getZ()) <= (float) this.avoidDistance;
    }

    @Override
    public void resetTask() {
        this.avoidBlockPos = null;
    }

    @Override
    public void tick() {
        faceAway();
        this.controller.setSpeed(1.0);
    }

    private void faceAway() {
        double d0 = this.avoidBlockPos.get().getX() - this.entity.getPosX();
        double d2 = this.avoidBlockPos.get().getZ() - this.entity.getPosZ();
        double d1 = (this.avoidBlockPos.get().getY() + this.entity.getBoundingBox().maxY) / 2.0D - this.entity.getPosYEye();

        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
        float f1 = (float) (-(MathHelper.atan2(d1, d3) * (double) (180F / (float) Math.PI)));
        this.entity.rotationPitch = updateRotation(this.entity.rotationPitch, f1, 10.0F);
        this.entity.rotationYaw = updateRotation(this.entity.rotationYaw, f + 180.0F, 10.0F);

        this.controller.setDirection(this.entity.rotationYaw, false);
    }

    private float updateRotation(float angle, float targetAngle, float maxIncrease) {
        float f = MathHelper.wrapDegrees(targetAngle - angle);
        if (f > maxIncrease) {
            f = maxIncrease;
        }

        if (f < -maxIncrease) {
            f = -maxIncrease;
        }

        return angle + f;
    }

}
