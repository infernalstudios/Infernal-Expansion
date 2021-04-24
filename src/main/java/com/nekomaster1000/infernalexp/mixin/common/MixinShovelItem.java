package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.blocks.GlowCampfireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class MixinShovelItem {

    @Inject(at = @At("HEAD"), method = "onItemUse", cancellable = true)
    private void IE_onItemUse(ItemUseContext context, CallbackInfoReturnable<ActionResultType> cir) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof GlowCampfireBlock && state.get(GlowCampfireBlock.LIT)) {
            if (!world.isRemote()) {
                world.playEvent(null, 1009, pos, 0);
            }
            GlowCampfireBlock.extinguish(world, pos, state);
            world.setBlockState(pos, state.with(GlowCampfireBlock.LIT, false));
            cir.setReturnValue(ActionResultType.SUCCESS);
        }
    }

}
