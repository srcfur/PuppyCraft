package com.srcfur.puppycraft.item.diaper;

import com.srcfur.badhygiene.BadHygieneCommon;
import com.srcfur.badhygiene.component.BadHygieneDataComponents;
import com.srcfur.puppycraft.PuppyCraftCommon;
import com.srcfur.puppycraft.api.PuppyCraftAPI;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class DiaperItem extends Item implements TrinketCallback {
    private static long lastDiaperChangeRequestTick = 0;
    public String DiaperTexture;
    public DiaperFamilies Family;
    public DiaperItem(String texture, DiaperFamilies family, Properties properties) {
        super(properties);
        DiaperTexture = texture;
        Family = family;
    }

    //We do the below so that the normal soiling decay can take place!
    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        TrinketCallback.super.tick(stack, slot, entity);
        if(!entity.level().isClientSide()){
            inventoryTick(stack, (ServerLevel) entity.level(), entity, null);
        };
        if(!(entity instanceof Player))return;
        Player plr = (Player) entity;
        if(plr.level().getGameTime() % 20 == 0 && stack.getOrDefault(BadHygieneDataComponents.Soiled, false)){
            PuppyCraftCommon.API.getPuppyPlayer(plr).impactMaturity(-10);
            BadHygieneCommon.API.getHygienePlayer(plr).impactHygiene(-1);
            if(plr.level().isClientSide() && plr == Minecraft.getInstance().player && plr.level().getGameTime() - lastDiaperChangeRequestTick > 20 * 60 * 3){
                lastDiaperChangeRequestTick = plr.level().getGameTime();
                plr.sendSystemMessage(Component.literal("I should probably change my ").append(stack.getDisplayName()));
            }
        }
    }
}
