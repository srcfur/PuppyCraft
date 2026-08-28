package com.srcfur.puppycraft;

import com.srcfur.puppycraft.fluid.PuppyCraftFluids;
import com.srcfur.puppycraft.fluid.YouthFluid;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

@Environment(EnvType.CLIENT)
public class PuppyCraftClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FluidRenderingRegistry.register(PuppyCraftFluids.Youth.get(), PuppyCraftFluids.Flowing_Youth.get(), YouthFluid.getModel());
    }
}
