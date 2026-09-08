package com.srcfur.puppycraft.mixin;

import com.srcfur.puppycraft.datacomponent.PuppyCraftDataComponents;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import com.srcfur.puppycraft.item.diaperbag.DiaperBagData;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class DiaperBagCountMixin {

    @Shadow
    abstract void text(Font font, @Nullable String str, int x, int y, int color, boolean dropShadow);

    @Inject(at = @At("TAIL"), method = "itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V")
    void puppycraft$itemDecorations(Font font, ItemStack stack, int x, int y, String counttext, CallbackInfo ci){
        if(stack.isEmpty() || stack.getItem() != PuppyCraftItems.DiaperBag.get())
            return;
        DiaperBagData data = stack.get(PuppyCraftDataComponents.DiaperBagData.get());
        if(data == null)
            return;
        String amount = Integer.toString(data.diapercount());
        this.text(font, (String)amount, x + 19 - 2 - font.width(amount), y + 6 + 3, -1, true);
    }
}
