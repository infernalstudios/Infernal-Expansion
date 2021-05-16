package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.entities.BlindsightEntity;
import com.nekomaster1000.infernalexp.init.IEEffects;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DullthornsBlockBlock extends Block {

	public DullthornsBlockBlock(Properties properties) {
		super(properties);

	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if (!worldIn.isRemote()) {
			if (entityIn instanceof LivingEntity && entityIn.isAlive() && !(entityIn instanceof BlindsightEntity)) {
				LivingEntity livingEntity = (LivingEntity) entityIn;
				livingEntity.addPotionEffect(new EffectInstance(IEEffects.LUMINOUS.get(), 300, 0));
			}
			entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
		}
	}

}
