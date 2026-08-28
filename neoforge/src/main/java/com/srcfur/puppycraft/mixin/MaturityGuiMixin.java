package com.srcfur.puppycraft.mixin;

import com.srcfur.puppycraft.client.MaturityGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MaturityGuiMixin {
    @Inject(method = "extractHealthLevel", at=@At("HEAD"))
    public void extractMaturity(GuiGraphicsExtractor graphics, CallbackInfo info){
        MaturityGui.extractMaturity(graphics);
    }
}
