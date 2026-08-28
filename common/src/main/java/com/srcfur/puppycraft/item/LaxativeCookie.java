package com.srcfur.puppycraft.item;

import com.srcfur.badhygiene.BadHygieneCommon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LaxativeCookie extends Item {
    public LaxativeCookie(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if(entity instanceof Player){
            Player plr = (Player) entity;
            BadHygieneCommon.API.getHygienePlayer(plr).setBowels(100);
            BadHygieneCommon.API.getHygienePlayer(plr).setBladder(100);
        }
        return super.finishUsingItem(itemStack, level, entity);
    }
}
