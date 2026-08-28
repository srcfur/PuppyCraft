package com.srcfur.puppycraft.platform;

import com.srcfur.puppycraft.api.PuppyCraftAPI;
import com.srcfur.puppycraft.api.PuppyPlayer;
import net.minecraft.world.entity.player.Player;

public class PuppyCraftAPINeoForge extends PuppyCraftAPI {
    @Override
    public PuppyPlayer getPuppyPlayer(Player plr) {
        return new PuppyPlayerNeoForge(plr);
    }
}