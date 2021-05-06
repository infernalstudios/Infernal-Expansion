package com.nekomaster1000.infernalexp.items;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.AscusBombEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AscusBombItem extends Item {
    public AscusBombItem() {
        super(new Item.Properties().maxStackSize(1).group(InfernalExpansion.TAB));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote) {
            AscusBombEntity ascusBombEntity = new AscusBombEntity(worldIn, playerIn);
            ascusBombEntity.setItem(itemStack);
			ascusBombEntity.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20, 0.5f, 1);
			worldIn.addEntity(ascusBombEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));

        if (!playerIn.abilities.isCreativeMode) {
            itemStack.shrink(1);
        }

        return ActionResult.func_233538_a_(itemStack, worldIn.isRemote());
    }
}
