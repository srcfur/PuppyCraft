package com.srcfur.puppycraft.utility;


import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class FluidHelper<T extends Fluid> extends GenericHelper<T> {
    public FluidHelper(Identifier identifier, Supplier<T> itemSupplier) {
        super(identifier, itemSupplier);
    }
}
