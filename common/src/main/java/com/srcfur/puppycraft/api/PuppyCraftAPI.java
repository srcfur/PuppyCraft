package com.srcfur.puppycraft.api;

import net.minecraft.world.entity.player.Player;

public abstract class PuppyCraftAPI {
    public abstract PuppyPlayer getPuppyPlayer(Player plr);
    public final int MATURITY_BABY = 0;
    public final int MATURITY_TODDLER = 3000;
    public final int MATURITY_KID = 7000;
    public final int MATURITY_TEEN = 13000;
    public final int MATURITY_ADULT = 18000;
}
