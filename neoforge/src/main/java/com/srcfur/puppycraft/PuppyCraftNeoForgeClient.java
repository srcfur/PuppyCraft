package com.srcfur.puppycraft;

import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.GeoItemRenderer;
import com.srcfur.puppycraft.item.PuppyCraftItems;
import com.srcfur.puppycraft.item.diaper.DiaperItem;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.include.com.google.common.base.Supplier;
import org.spongepowered.include.com.google.common.base.Suppliers;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class PuppyCraftNeoForgeClient {
    public PuppyCraftNeoForgeClient(IEventBus bus){

    }
}
