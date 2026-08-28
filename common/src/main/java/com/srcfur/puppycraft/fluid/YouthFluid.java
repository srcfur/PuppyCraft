package com.srcfur.puppycraft.fluid;

import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public abstract class YouthFluid extends FlowingFluid {

    public static FluidModel.Unbaked getModel(){
        return new FluidModel.Unbaked(
                new Material(Identifier.withDefaultNamespace("block/water_still")),
                new Material(Identifier.withDefaultNamespace("block/water_flow")),
                new Material(Identifier.withDefaultNamespace("block/water_overlay")),
                BlockTintSources.constant(ARGB.opaque(0x98f8f0))
        );
    }

    @Override
    public Fluid getFlowing() {
        return PuppyCraftFluids.Flowing_Youth.get();
    }

    @Override
    public Fluid getSource() {
        return PuppyCraftFluids.Youth.get();
    }

    @Override
    protected boolean canConvertToSource(ServerLevel serverLevel) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {

    }

    @Override
    protected int getSlopeFindDistance(LevelReader levelReader) {
        return 4;
    }

    @Override
    protected int getDropOff(LevelReader levelReader) {
        return 1;
    }

    public boolean isSame(Fluid other) {
        return other == PuppyCraftFluids.Youth.get() || other == PuppyCraftFluids.Flowing_Youth.get();
    }

    @Override
    public Item getBucket() {
        return PuppyCraftItems.BucketOfYouth.get();
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return true;
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 3;
    }

    @Override
    protected float getExplosionResistance() {
        return 0;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        return PuppyCraftBlocks.Youth.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    public static class Source extends YouthFluid {
        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

    }
    public static class Flowing extends YouthFluid {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(new Property[]{FlowingFluid.LEVEL});
        }
        @Override
        public boolean isSource(@NotNull FluidState fluidState) {
            return false;
        }

        public int getAmount(FluidState fluidState) {
            return (Integer)fluidState.getValue(FlowingFluid.LEVEL);
        }

    }
}
