package io.ticticboom.mods.machinesjs.multiblock;

import dev.latvian.mods.kubejs.RegistryObjectBuilderTypes;
import io.ticticboom.mods.machinesjs.blockentity.BlockEntityTypeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class MultiBlockMachineBlockEntity extends BlockEntity {
    protected final MultiBlockPattern pattern;
    protected final MultiBlockMachineJS.TickProcessor process;

    public MultiBlockMachineBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_, MultiBlockPattern pattern, MultiBlockMachineJS.TickProcessor process) {
        super(p_155228_, p_155229_, p_155230_);
        this.pattern = pattern;
        this.process = process;
    }

    public void tick() {
        if (this.level.isClientSide) {
            return;
        }
        if (this.pattern.matches(level, getBlockPos())) {
            this.process.tick(level, getBlockPos(), getBlockState(), (MultiBlockMachineJS) getBlockState().getBlock());
        }
    }
    public static class Builder extends BlockEntityTypeBuilder {
        public MultiBlockPattern pattern;
        public MultiBlockMachineJS.TickProcessor processor;
        public Builder(ResourceLocation i) {
            super(i);
        }

        @Override
        public BlockEntityType<?> createObject() {
            return BlockEntityType.Builder.of((pos, state) -> new MultiBlockMachineBlockEntity(this.get(), pos, state, this.pattern, this.processor), this.validBlocks.toArray(Block[]::new)).build(null);
        }

        public Builder machinePattern(Consumer<MultiBlockPattern.Builder> b) {
            var patternBuilder = new MultiBlockPattern.Builder();
            b.accept(patternBuilder);
            pattern = patternBuilder.build();
            return this;
        }

        public Builder machineProcess(MultiBlockMachineJS.TickProcessor p) {
            this.processor = p;
            return this;
        }
    }
}