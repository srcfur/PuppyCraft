package com.srcfur.puppycraft.block;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;

/// LITERALLY EXISTS JUST FOR THE PUBLIC LIQUID BLOCK ON VANILLA!
public class TransientLiquidBlock extends LiquidBlock {
    public TransientLiquidBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }
}
