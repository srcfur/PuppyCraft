package com.srcfur.puppycraft.mixin;

import com.srcfur.puppycraft.PuppyCraftNeoForge;
import com.srcfur.puppycraft.fluid.PuppyCraftFluids;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CommonHooks.class)
public class FluidMixin {
    @Inject(at= @At("HEAD"), method="getVanillaFluidType", cancellable = true)
    private static void getModdedFluidTypes(Fluid fluid, CallbackInfoReturnable<FluidType> cir){
        if(fluid == PuppyCraftFluids.Youth.get() || fluid == PuppyCraftFluids.Flowing_Youth.get())
            cir.setReturnValue(PuppyCraftNeoForge.Youth_FluidType);
    }
}
