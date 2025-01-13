package org.infernalstudios.infernalexp.blocks;

import it.crystalnest.soul_fire_d.api.block.CustomFireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import org.infernalstudios.infernalexp.init.IETags;

public class GlowFireBlock extends CustomFireBlock {
    public GlowFireBlock(ResourceLocation fireType) {
        super(fireType, IETags.Blocks.GLOW_FIRE_BASE_BLOCKS, MaterialColor.FIRE);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        // Override to remove smoke and add glow sparkle particles?
    }
}
