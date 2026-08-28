package com.srcfur.puppycraft.utility;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public class DataComponentHelper<T> extends GenericHelper<DataComponentType<T>> {
    public DataComponentHelper(Identifier identifier, Supplier<DataComponentType<T>> itemSupplier) {
        super(identifier, itemSupplier);
    }
}
