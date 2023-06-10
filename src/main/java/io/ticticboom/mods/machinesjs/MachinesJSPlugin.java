package io.ticticboom.mods.machinesjs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.RegistryObjectBuilderTypes;
import dev.latvian.mods.kubejs.block.custom.BasicBlockJS;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import io.ticticboom.mods.machinesjs.multiblock.BlockInWorld;
import io.ticticboom.mods.machinesjs.multiblock.MultiBlockMachineBlockEntity;
import io.ticticboom.mods.machinesjs.multiblock.MultiBlockMachineJS;

public class MachinesJSPlugin extends KubeJSPlugin {
    @Override
    public void init() {
        RegistryObjectBuilderTypes.BLOCK.addType("multiblock_machine", MultiBlockMachineJS.Builder.class, MultiBlockMachineJS.Builder::new);
    RegistryObjectBuilderTypes.BLOCK_ENTITY_TYPE.addType("multiblock_machine", MultiBlockMachineBlockEntity.Builder.class, MultiBlockMachineBlockEntity.Builder::new);
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("BlockInWorld", BlockInWorld.class);
    }
}
