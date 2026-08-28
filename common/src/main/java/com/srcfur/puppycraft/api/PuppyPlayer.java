package com.srcfur.puppycraft.api;

import com.srcfur.badhygiene.BadHygieneCommon;
import com.srcfur.badhygiene.api.AbstractHygienePlayer;
import com.srcfur.badhygiene.component.BadHygieneDataComponents;
import com.srcfur.badhygiene.effect.BadHygieneEffects;
import com.srcfur.puppycraft.PuppyCraftCommon;
import com.srcfur.puppycraft.item.diaper.DiaperItem;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public abstract class PuppyPlayer {
    private Player player;
    public PuppyPlayer(Player plr){
        player = plr;
    }

    public abstract void setMaturity(int maturity);
    public abstract int getMaturity();

    public Player getPlayer(){
        return player;
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
        impactMaturity(-75); //Base maturity loss relatively low
        if(getDiaper().isEmpty()) return false;
        impactMaturity(-175); //Big babies obviously use their diapers :P
        ItemStack diaper = getDiaper().get();
        diaper.setDamageValue(Math.min(diaper.getDamageValue() + getHygiene().getBladder(), diaper.getMaxDamage()));
        if(diaper.getDamageValue() == diaper.getMaxDamage())
            player.sendSystemMessage(Component.empty().append(diaper.getDisplayName()).append(Component.literal(" is full...")));
        else
            return true;
        return false;
    }
    public boolean poopSelf(){
        impactMaturity(-500); //Pooping yourself is kinda baby
        if(getDiaper().isEmpty()) return false;
        impactMaturity(-1000); //Big babies would fill their diapers!
        ItemStack diaper = getDiaper().get();
        diaper.set(BadHygieneDataComponents.Soiled, true);
        getHygiene().impactHygiene(-1000);
        for(Player player1 : player.level().players()){
            if(player1.position().distanceTo(player.position()) > 8) continue;
            player1.sendSystemMessage(Component.empty().append(player.getDisplayName()).append(" soils their ").append(diaper.getDisplayName()));
        }
        return true;
    }
    public void impactMaturity(int score){
        setMaturity(Math.clamp(getMaturity() + score, PuppyCraftCommon.MinimumMaturity, PuppyCraftCommon.MaximumMaturity));
        if(score > 0 || getMaturity() > 10000)return;
        int inconduration = 1200;
        inconduration += (10 - getMaturity() / 1000) * 1200;
        player.addEffect(new MobEffectInstance(BadHygieneEffects.Incontinence, inconduration));
    }
}
