package com.srcfur.puppycraft.platform;

import com.srcfur.puppycraft.api.PuppyPlayer;
import com.srcfur.puppycraft.attachment.PuppyCraftAttachments;
import net.minecraft.world.entity.player.Player;

public class PuppyPlayerNeoForge extends PuppyPlayer {
    public PuppyPlayerNeoForge(Player plr) {
        super(plr);
    }

    @Override
    public void setMaturity(int maturity) {
        getPlayer().setData(PuppyCraftAttachments.Maturity.get(), maturity);
    }

    @Override
    public int getMaturity() {
        return getPlayer().getData(PuppyCraftAttachments.Maturity.get());
    }
}
