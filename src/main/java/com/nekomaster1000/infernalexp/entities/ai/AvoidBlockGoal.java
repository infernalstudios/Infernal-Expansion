package com.nekomaster1000.infernalexp.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class AvoidBlockGoal extends Goal {

    protected final SlimeEntity entity;
    private final double farSpeed;
    private final double nearSpeed;
    protected Vector3d avoidBlockVector;
    protected final float avoidDistance;
    protected Path path;
    protected final PathNavigator navigation;
    protected final Block avoidBlock;

    public AvoidBlockGoal(SlimeEntity entityIn, Block blockToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
        this(entityIn, blockToAvoidIn, (p_200828_0_) -> {
            return true;
        }, avoidDistanceIn, farSpeedIn, nearSpeedIn, EntityPredicates.CAN_AI_TARGET::test);
    }

    private AvoidBlockGoal(SlimeEntity entityIn, Block blockToAvoidIn, Predicate<LivingEntity> targetPredicate, float distance, double nearSpeedIn, double farSpeedIn, Predicate<LivingEntity> p_i48859_9_) {
        this.entity = entityIn;
        this.avoidBlock = blockToAvoidIn;
        this.avoidDistance = distance;
        this.farSpeed = nearSpeedIn;
        this.nearSpeed = farSpeedIn;
        this.navigation = entityIn.getNavigator();
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        //this.builtTargetSelector = (new EntityPredicate()).setDistance((double)distance).setCustomPredicate(p_i48859_9_.and(targetPredicate));
    }

    @Override
    public boolean shouldExecute() {
        Optional<BlockPos> avoidBlockPos = BlockPos.getClosestMatchingPosition(this.entity.getPosition(), 8, 4, (pos) -> this.entity.world.getBlockState(pos).equals(this.avoidBlock.getDefaultState()));

        if (avoidBlockPos.isPresent()) {
            this.avoidBlockVector = new Vector3d(avoidBlockPos.get().getX(), avoidBlockPos.get().getY(), avoidBlockPos.get().getZ());
            Vector3d runAwayVector = RandomPositionGeneratorTest.findRandomTargetBlockAwayFrom(this.entity, 16, 7, this.avoidBlockVector);

            if (runAwayVector == null) {
                return false;
            } else if (this.avoidBlockVector.distanceTo(runAwayVector) < this.avoidBlockVector.distanceTo(this.entity.getPositionVec())) {
                return false;
            } else {
                System.out.println(runAwayVector.toString());

                this.path = this.navigation.getPathToPos(runAwayVector.getX(), runAwayVector.getY(), runAwayVector.getZ(), 0);

                return this.path != null;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.navigation.noPath();
    }

    @Override
    public void startExecuting() {
        this.navigation.setPath(this.path, this.farSpeed);
    }

    @Override
    public void resetTask() {
        this.avoidBlockVector = null;
    }

    @Override
    public void tick() {
        if (this.entity.getDistanceSq(this.avoidBlockVector) < 49.0D) {
            this.entity.getNavigator().setSpeed(this.nearSpeed);
        } else {
            this.entity.getNavigator().setSpeed(this.farSpeed);
        }
    }
}
