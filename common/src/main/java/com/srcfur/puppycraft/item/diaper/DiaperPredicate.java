package com.srcfur.puppycraft.item.diaper;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DiaperPredicate implements TrinketsApi.TrinketPredicate {
    @Override
    public boolean test(ItemStack itemStack, TrinketSlotAccess trinketSlotAccess, LivingEntity livingEntity) {
        return itemStack.getItem() instanceof DiaperItem;
    }
}
