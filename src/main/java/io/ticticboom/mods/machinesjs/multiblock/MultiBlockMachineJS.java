package io.ticticboom.mods.machinesjs.multiblock;

import dev.latvian.mods.kubejs.RegistryObjectBuilderTypes;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.custom.BasicBlockJS;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MultiBlockMachineJS extends BasicBlockJS implements EntityBlock {
    private final Builder builder;

    public MultiBlockMachineJS(Builder builder) {
        super(builder);
        this.builder = builder;
    }
    @FunctionalInterface
    public interface TickProcessor {
        void tick(Level level, BlockPos pos, BlockState state, MultiBlockMachineJS machine);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return builder.mbBuilder.get().create(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (level, pos, state, blockEntity) -> {
            if (blockEntity instanceof MultiBlockMachineBlockEntity) {
                ((MultiBlockMachineBlockEntity)blockEntity).tick();
            }
        };
    }

    public static class Builder extends BlockBuilder {
        protected MultiBlockMachineBlockEntity.Builder mbBuilder;
        public Builder(ResourceLocation i) {
            super(i);
        }

        @Override
        public Block createObject() {
            var res = new MultiBlockMachineJS(this);
            mbBuilder.withValidBlocks(res);
            return res;
        }

        @Override
        public void createAdditionalObjects() {
            super.createAdditionalObjects();
            RegistryObjectBuilderTypes.BLOCK_ENTITY_TYPE.addBuilder(mbBuilder);
        }

        public Builder multiBlock(Consumer<MultiBlockMachineBlockEntity.Builder> mb) {
            this.mbBuilder = new MultiBlockMachineBlockEntity.Builder(this.id);
            mb.accept(this.mbBuilder);
            return this;
        }
    }
}
