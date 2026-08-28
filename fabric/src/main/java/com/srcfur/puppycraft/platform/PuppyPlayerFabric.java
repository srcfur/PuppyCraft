package com.srcfur.puppycraft.platform;

import com.srcfur.puppycraft.PuppyCraftCommon;
import com.srcfur.puppycraft.api.PuppyPlayer;
import com.srcfur.puppycraft.attachment.PuppyCraftAttachments;
import net.minecraft.world.entity.player.Player;

public class PuppyPlayerFabric extends PuppyPlayer {
    public PuppyPlayerFabric(Player plr) {
        super(plr);
    }

    @Override
    public void setMaturity(int maturity) {
        getPlayer().setAttached(PuppyCraftAttachments.Maturity, maturity);
    }

    @Override
    public int getMaturity() {
        return getPlayer().getAttachedOrGet(PuppyCraftAttachments.Maturity, ()-> PuppyCraftCommon.DefaultMaturity);
    }
}
