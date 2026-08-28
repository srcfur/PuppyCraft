package com.srcfur.puppycraft;

import com.srcfur.puppycraft.fluid.PuppyCraftFluids;
import com.srcfur.puppycraft.fluid.YouthFluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class PuppyCraftNeoForgeClient {
    public PuppyCraftNeoForgeClient(IEventBus bus){
        bus.addListener(PuppyCraftNeoForgeClient::onRegisterFluidModels);
    }
    static void onRegisterFluidModels(RegisterFluidModelsEvent event){
        event.register(YouthFluid.getModel(), PuppyCraftFluids.Youth.get(), PuppyCraftFluids.Flowing_Youth.get());
    }
}
