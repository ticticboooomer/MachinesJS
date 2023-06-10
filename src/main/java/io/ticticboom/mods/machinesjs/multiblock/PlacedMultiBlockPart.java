package io.ticticboom.mods.machinesjs.multiblock;

import net.minecraft.core.BlockPos;

public record PlacedMultiBlockPart(
        BlockPos relativePos,
        MultiBlockPattern.MultiBlockKeyPredicate predicate
) {

}
