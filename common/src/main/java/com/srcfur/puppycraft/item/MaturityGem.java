package com.srcfur.puppycraft.item;

import com.srcfur.puppycraft.PuppyCraftCommon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class MaturityGem extends Item {
    public MaturityGem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(PuppyCraftCommon.API.getPuppyPlayer(player).getMaturity() == PuppyCraftCommon.MaximumMaturity){
            if(level.isClientSide()){
                player.sendOverlayMessage(Component.literal("I feel pretty mature as it is..."));
            }
            return InteractionResult.FAIL;
        }
        player.getItemInHand(hand).setCount(0);
        PuppyCraftCommon.API.getPuppyPlayer(player).impactMaturity(1000);
        return InteractionResult.CONSUME;
    }
}
