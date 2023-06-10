package io.ticticboom.mods.machinesjs.blockentity;

import dev.latvian.mods.kubejs.BuilderBase;
import dev.latvian.mods.kubejs.RegistryObjectBuilderTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;

public abstract  class BlockEntityTypeBuilder extends BuilderBase<BlockEntityType<?>> {
    protected List<Block> validBlocks;

    public BlockEntityTypeBuilder(ResourceLocation i) {
        super(i);
    }

    @Override
    public RegistryObjectBuilderTypes getRegistryType() {
        return RegistryObjectBuilderTypes.BLOCK_ENTITY_TYPE;
    }

    public BlockEntityTypeBuilder withValidBlocks(Block... validBlocks) {
        this.validBlocks = List.of(validBlocks);
        return this;
    }
}
