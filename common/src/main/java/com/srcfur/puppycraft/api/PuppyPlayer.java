package com.srcfur.puppycraft.api;

import com.srcfur.badhygiene.BadHygieneCommon;
import com.srcfur.badhygiene.api.AbstractHygienePlayer;
import com.srcfur.badhygiene.component.BadHygieneDataComponents;
import com.srcfur.puppycraft.item.diaper.DiaperItem;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class PuppyPlayer {
    private Player player;
    public PuppyPlayer(Player plr){
        player = plr;
    }
    public AbstractHygienePlayer getHygiene(){
        return BadHygieneCommon.API.getHygienePlayer(player);
    }
    public Optional<ItemStack> getDiaper() {
        Player plr = player;
        for(TrinketSlotAccess slot :
                TrinketsApi.getAttachment(player).allEquipped(true)){
            if(slot.get().getItem() instanceof DiaperItem)
                return Optional.of(slot.get());
        }
        return Optional.empty();
    }
    public boolean peeSelf(){
        if(getDiaper().isEmpty()) return false;
        ItemStack diaper = getDiaper().get();
        diaper.setDamageValue(Math.min(diaper.getDamageValue() + getHygiene().getBladder(), diaper.getMaxDamage()));
        if(diaper.getDamageValue() == diaper.getMaxDamage())
            player.sendSystemMessage(Component.empty().append(diaper.getDisplayName()).append(Component.literal(" is full...")));
        else
            return false;
        return true;
    }
    public boolean poopSelf(){
        if(getDiaper().isEmpty()) return false;
        ItemStack diaper = getDiaper().get();
        diaper.set(BadHygieneDataComponents.Soiled, true);
        getHygiene().impactHygiene(-1000);
        for(Player player1 : player.level().players()){
            if(player1.position().distanceTo(player.position()) > 8) continue;
            player1.sendSystemMessage(Component.empty().append(player.getDisplayName()).append(" soils their ").append(diaper.getDisplayName()));
        }
        return true;
    }
}
