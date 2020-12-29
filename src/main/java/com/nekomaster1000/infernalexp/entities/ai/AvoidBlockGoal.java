package com.nekomaster1000.infernalexp.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class AvoidBlockGoal extends Goal {

    protected final SlimeEntity entity;
    protected Vector3d avoidBlockVector;
    protected Vector3d runAwayVector;
    protected final float avoidDistance;
    protected float desiredDirection;
    protected final SlimeEntity.MoveHelperController controller;
    protected final Block avoidBlock;

    public AvoidBlockGoal(SlimeEntity entityIn, Block blockToAvoidIn, float avoidDistanceIn) {
        this(entityIn, blockToAvoidIn, (p_200828_0_) -> {
            return true;
        }, avoidDistanceIn, EntityPredicates.CAN_AI_TARGET::test);
    }

    private AvoidBlockGoal(SlimeEntity entityIn, Block blockToAvoidIn, Predicate<LivingEntity> targetPredicate, float distance, Predicate<LivingEntity> p_i48859_9_) {
        this.entity = entityIn;
        this.avoidBlock = blockToAvoidIn;
        this.avoidDistance = distance;
        this.controller = (SlimeEntity.MoveHelperController) entityIn.getMoveHelper();
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        //this.builtTargetSelector = (new EntityPredicate()).setDistance((double)distance).setCustomPredicate(p_i48859_9_.and(targetPredicate));
    }

    @Override
    public boolean shouldExecute() {
        Optional<BlockPos> avoidBlockPos = BlockPos.getClosestMatchingPosition(this.entity.getPosition(), 8, 4, (pos) -> this.entity.world.getBlockState(pos).equals(this.avoidBlock.getDefaultState()));

        if (avoidBlockPos.isPresent()) {
            this.avoidBlockVector = new Vector3d(avoidBlockPos.get().getX(), avoidBlockPos.get().getY(), avoidBlockPos.get().getZ());
            //this.runAwayVector = this.avoidBlockVector.crossProduct(this.entity.getPositionVec().inverse());

            this.runAwayVector = RandomPositionGeneratorTest.findRandomTargetBlockAwayFrom(this.entity, 16, 7, this.avoidBlockVector);

            if (this.runAwayVector == null) {
                return false;
            } else if (this.avoidBlockVector.distanceTo(this.runAwayVector) < this.avoidBlockVector.distanceTo(this.entity.getPositionVec())) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return avoidBlockVector.isWithinDistanceOf(this.entity.getPositionVec(), avoidDistance);
    }

    @Override
    public void resetTask() {
        this.avoidBlockVector = null;
    }

    @Override
    public void tick() {
        this.updateClosestBlock();
        this.faceAwayFromBlock();
        System.out.println(this.runAwayVector);
        this.controller.setSpeed(1.0);
    }

    private void faceAwayFromBlock(){
        double d0 = this.runAwayVector.getX() - this.entity.getPosX();
        double d2 = this.runAwayVector.getZ() - this.entity.getPosZ();
        double d1 = (this.runAwayVector.getY() / 2.0D - this.entity.getPosYEye());

        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        this.desiredDirection = (float)(-(MathHelper.atan2(d1, d3) * (double)(180F / (float)Math.PI)));

        this.controller.setDirection(this.desiredDirection,false);
    }

    private void updateClosestBlock(){
        Optional<BlockPos> avoidBlockPos = BlockPos.getClosestMatchingPosition(this.entity.getPosition(), 8, 4, (pos) -> this.entity.world.getBlockState(pos).equals(this.avoidBlock.getDefaultState()));

        if (avoidBlockPos.isPresent()) {
            this.avoidBlockVector = new Vector3d(avoidBlockPos.get().getX(), avoidBlockPos.get().getY(), avoidBlockPos.get().getZ());

            do {
                this.runAwayVector = RandomPositionGeneratorTest.findRandomTargetBlockAwayFrom(this.entity, 16, 7, this.avoidBlockVector);
            } while (runAwayVector == null);
        }

    }
}
