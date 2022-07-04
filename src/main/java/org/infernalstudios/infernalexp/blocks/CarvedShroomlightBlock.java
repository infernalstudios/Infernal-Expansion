package org.infernalstudios.infernalexp.blocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import org.infernalstudios.infernalexp.entities.BlackstoneDwarfEntity;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEntityTypes;

import javax.annotation.Nullable;

public class CarvedShroomlightBlock extends HorizontalDirectionalBlock implements Wearable {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    @Nullable
    private BlockPattern blackstoneDwarfBase;
    @Nullable
    private BlockPattern blackstoneDwarfFull;

    public CarvedShroomlightBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState currentState, boolean p_51391_) {
        if (!currentState.is(state.getBlock())) {
            this.trySpawnGolem(level, pos);
        }
    }

    public boolean canSpawnGolem(LevelReader level, BlockPos pos) {
        return this.getOrCreateBlackstoneDwarfBase().find(level, pos) != null;
    }

    private void trySpawnGolem(Level level, BlockPos pos) {
        BlockPattern.BlockPatternMatch patternMatch = this.getOrCreateBlackstoneDwarfFull().find(level, pos);
        if (patternMatch != null) {
            for (int j = 0; j < this.getOrCreateBlackstoneDwarfFull().getWidth(); ++j) {
                for (int k = 0; k < this.getOrCreateBlackstoneDwarfFull().getHeight(); ++k) {
                    BlockInWorld blockinworld2 = patternMatch.getBlock(j, k, 0);
                    level.setBlock(blockinworld2.getPos(), Blocks.AIR.defaultBlockState(), 2);
                    level.levelEvent(2001, blockinworld2.getPos(), Block.getId(blockinworld2.getState()));
                }
            }

            BlockPos blockpos = patternMatch.getBlock(1, 2, 0).getPos();
            BlackstoneDwarfEntity blackstoneDwarf = IEEntityTypes.BLACKSTONE_DWARF.get().create(level);
            //blackstoneDwarf.setPlayerCreated(true);
            blackstoneDwarf.moveTo((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.05D, (double) blockpos.getZ() + 0.5D, 0.0F, 0.0F);
            level.addFreshEntity(blackstoneDwarf);

            for (ServerPlayer serverplayer1 : level.getEntitiesOfClass(ServerPlayer.class, blackstoneDwarf.getBoundingBox().inflate(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer1, blackstoneDwarf);
            }

            for (int i1 = 0; i1 < this.getOrCreateBlackstoneDwarfFull().getWidth(); ++i1) {
                for (int j1 = 0; j1 < this.getOrCreateBlackstoneDwarfFull().getHeight(); ++j1) {
                    BlockInWorld blockinworld1 = patternMatch.getBlock(i1, j1, 0);
                    level.blockUpdated(blockinworld1.getPos(), Blocks.AIR);
                }
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition) {
        stateDefinition.add(FACING);
    }

    private BlockPattern getOrCreateBlackstoneDwarfBase() {
        if (this.blackstoneDwarfBase == null) {
            this.blackstoneDwarfBase = BlockPatternBuilder.start().aisle("~ ~", "###", "~#~").where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.GILDED_BLACKSTONE))).where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR))).build();
        }

        return this.blackstoneDwarfBase;
    }

    private BlockPattern getOrCreateBlackstoneDwarfFull() {
        if (this.blackstoneDwarfFull == null) {
            this.blackstoneDwarfFull = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(IEBlocks.CARVED_SHROOMLIGHT.get()))).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.GILDED_BLACKSTONE))).where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR))).build();
        }

        return this.blackstoneDwarfFull;
    }
}
