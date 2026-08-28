package com.srcfur.puppycraft.utility;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.Optional;
import java.util.function.Supplier;

public class ItemHelper<T extends Item> extends GenericHelper<T> {
    public ItemHelper(Identifier identifier, Supplier<T> itemSupplier) {
        super(identifier, itemSupplier);
    }
}
