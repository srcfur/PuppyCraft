package com.srcfur.puppycraft.client;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.PuppyCraftCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class MaturityGui {
    private static final Identifier BRAIN_GUI = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "brain");
    public static void extractMaturity(GuiGraphicsExtractor graphics){
        int yoffset = Minecraft.getInstance().getWindow().getGuiScaledHeight() - 23;

        Player plr = Minecraft.getInstance().player;

        if( plr != null ){
            int xoffset = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - 12 + 109 * (plr.getMainArm() == HumanoidArm.RIGHT ? 1 : -1);
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BRAIN_GUI, xoffset, yoffset, 24, 24);
            graphics.centeredText(Minecraft.getInstance().font, String.valueOf((
                            PuppyCraftCommon.API.getPuppyPlayer(Minecraft.getInstance().player).getMaturity() / 1000)),
                    xoffset + 13, yoffset + 7, Color.WHITE.getRGB());
        }
    }
}
