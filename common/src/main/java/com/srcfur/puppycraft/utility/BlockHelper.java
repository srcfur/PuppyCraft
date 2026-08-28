package com.srcfur.puppycraft.utility;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BlockHelper<T extends Block> extends GenericHelper<T> {
    public BlockHelper(Identifier identifier, Supplier<T> itemSupplier) {
        super(identifier, itemSupplier);
    }
}
