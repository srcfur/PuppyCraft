package com.srcfur.puppycraft.block.entity;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import com.srcfur.puppycraft.utility.BlockEntityHelper;
import com.srcfur.puppycraft.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class PuppyCraftBlockEntities {
    public static final BlockEntityHelper<DiaperBagEntity> DiaperBag = createHelper("diaperbag", DiaperBagEntity::new, List.of(PuppyCraftBlocks.DiaperBag));
    private static <R extends BlockEntity> BlockEntityHelper<R> createHelper(String name, BiFunction<BlockPos, BlockState, R> factory, List<BlockHelper<?>> blocks){
        return new BlockEntityHelper<>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, name), factory, blocks);
    }
}
