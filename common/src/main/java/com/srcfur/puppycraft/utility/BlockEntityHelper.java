package com.srcfur.puppycraft.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class BlockEntityHelper<T extends BlockEntity> extends GenericHelper<BlockEntityProperties<T>> {
    public BlockEntityHelper(Identifier identifier, BiFunction<BlockPos, BlockState, T> factory, List<BlockHelper<?>> blocks) {
        super(identifier, ()->{
            ArrayList<Block> retBlocks = new ArrayList<>();
            blocks.forEach(raw -> retBlocks.add(raw.get()));
            return new BlockEntityProperties<>(factory, retBlocks);
        });
    }
    private BlockEntityType<T> entity;

    public BlockEntityType<T> getType(){
        return entity;
    }

    @Override
    public void register(Register<BlockEntityProperties<T>> registerHandler) {
        super.register(registerHandler);
        entity = get().get();
    }
}
