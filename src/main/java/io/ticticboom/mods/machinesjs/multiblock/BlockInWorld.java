package io.ticticboom.mods.machinesjs.multiblock;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockInWorld {
    public static MultiBlockPattern.MultiBlockKeyPredicate block(Block i) {
        return (level, pos) -> level.getBlockState(pos).getBlock() == i;
    }
}
