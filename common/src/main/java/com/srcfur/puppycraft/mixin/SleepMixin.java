package com.srcfur.puppycraft.mixin;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.block.CribBlock;
import com.srcfur.puppycraft.block.PuppyCraftBlocks;
import net.minecraft.Optionull;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Player.class)
public abstract class SleepMixin extends LivingEntity {
    protected SleepMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(at=@At("HEAD"), method="stopSleepInBed", cancellable = true)
    private void PC$stopSleepingInBed(boolean force, boolean update, CallbackInfo ci){
        Optional<BlockPos> bedPos = getSleepingPos();
        if(bedPos.isEmpty()){
            Constants.LOG.warn("No bed found when checking for stop sleep?");
            return;
        }
        BlockState block = this.level().getBlockState(bedPos.get());
        if(!(block.getBlock() instanceof CribBlock) || force) return;
        ci.cancel();
    }
}
