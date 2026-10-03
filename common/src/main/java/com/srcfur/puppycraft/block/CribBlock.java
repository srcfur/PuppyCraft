package com.srcfur.puppycraft.block;

import com.srcfur.puppycraft.block.entity.CribEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CribBlock extends BedBlock {
    public CribBlock(DyeColor color, Properties properties) {
        super(color, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new CribEntity(worldPosition, blockState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide())
            return InteractionResult.SUCCESS_SERVER;
        if(state.getValue(OCCUPIED)){
            player.sendOverlayMessage(Component.literal("Crib is occupied by another bab!"));
            return InteractionResult.SUCCESS_SERVER;
        }
        PutEntityToSleep(player, getBlockType(state) == DoubleBlockCombiner.BlockType.FIRST ? pos : pos.offset(state.getValue(FACING).getUnitVec3i()));
        return InteractionResult.SUCCESS;
        //return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if(state.getValue(PART) == BedPart.FOOT){
            return List.of();
        }
        return super.getDrops(state, params);
    }

    private static void PutEntityToSleep(LivingEntity entity, BlockPos position){
        entity.startSleeping(position);
        if (entity.level() instanceof ServerLevel) {
            ((ServerLevel)entity.level()).updateSleepingPlayerList();
        }
        /*
        entity.setPose(Pose.SLEEPING);
        entity.setPos((double)position.getX() + (double)0.5F, (double)position.getY() + (double)0.6875F, (double)position.getZ() + (double)0.5F);
        entity.setDeltaMovement(Vec3.ZERO);
        entity.setSleepingPos(position);
        entity.needsSync = true;

         */
    }
}
