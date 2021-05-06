package com.nekomaster1000.infernalexp.items;

import com.nekomaster1000.infernalexp.entities.InfernalPaintingEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.HangingEntityItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

public class InfernalPaintingItem extends HangingEntityItem {

	public InfernalPaintingItem(Properties properties) {
		super(EntityType.PAINTING, properties);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		BlockPos blockPos = context.getPos().offset(context.getFace());

		if (context.getPlayer() == null || !this.canPlace(context.getPlayer(), context.getFace(), context.getItem(), blockPos)) {
			return ActionResultType.FAIL;
		} else {
			InfernalPaintingEntity paintingEntity = new InfernalPaintingEntity(context.getWorld(), blockPos, context.getFace());

			CompoundNBT compoundNBT = context.getItem().getTag();
			if (compoundNBT != null) {
				EntityType.applyItemNBT(context.getWorld(), context.getPlayer(), paintingEntity, compoundNBT);
			}

			if (paintingEntity.onValidSurface()) {
				if (!context.getWorld().isRemote()) {
					paintingEntity.playPlaceSound();
					context.getWorld().addEntity(paintingEntity);
				}

				context.getItem().shrink(1);
				return ActionResultType.func_233537_a_(context.getWorld().isRemote());
			} else {
				return ActionResultType.CONSUME;
			}
		}
	}
}
