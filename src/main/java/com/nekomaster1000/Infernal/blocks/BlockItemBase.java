package com.nekomaster1000.Infernal.blocks;

import com.nekomaster1000.Infernal.InfernalExpansion;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBase extends BlockItem {

    public BlockItemBase(Block block) {
        super(block, new Item.Properties().group(InfernalExpansion.TAB));
    }
}
