package com.srcfur.puppycraft.mixin;

import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.PuppyCraftCommon;
import com.srcfur.puppycraft.client.MaturityGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Gui.class)
public class MaturityGuiMixin {
    @Inject(method = "extractPlayerHealth", at=@At("HEAD"))
    public void extractMaturity(GuiGraphicsExtractor graphics, CallbackInfo info){
        MaturityGui.extractMaturity(graphics);
    }
}
