package com.srcfur.puppycraft.fluid;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.utility.FluidHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class PuppyCraftFluids {

    public static final FluidHelper<YouthFluid> Flowing_Youth = createHelper("flowing_youth", YouthFluid.Flowing::new);
    public static final FluidHelper<YouthFluid> Youth = createHelper("youth", YouthFluid.Source::new);

    private static <T extends Fluid> FluidHelper<T> createHelper(String name, Supplier<T> supp){
        return new FluidHelper<>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, name), supp);
    }
}
