package com.nekomaster1000.Infernal.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class SandstoneBlock extends Block {
    public SandstoneBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK)
                // Add a . for block property options
                .setRequiresTool().hardnessAndResistance(0.8F)
                .sound(SoundType.STONE)
                //.harvestLevel(1)
                //.harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
        );
    }
}
