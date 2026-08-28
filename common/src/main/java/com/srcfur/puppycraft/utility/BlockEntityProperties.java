package com.srcfur.puppycraft.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class BlockEntityProperties<T extends BlockEntity> {
    public BiFunction<BlockPos, BlockState, T> supplier;
    public List<Block> blocks;
    private Optional<Supplier<BlockEntityType<T>>> handler = Optional.empty();
    public BlockEntityProperties(BiFunction<BlockPos, BlockState, T> supp, List<Block> blocks){
        this.blocks = blocks;
        supplier = supp;
    }
    public void handle(Supplier<BlockEntityType<T>> handle){
        handler = Optional.of(handle);
    }
    public BlockEntityType<T> get(){
        if(handler.isEmpty())
            throw new RuntimeException("Nothing to handle block entity type!");
        return handler.get().get();
    }
}