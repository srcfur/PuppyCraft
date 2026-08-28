package com.srcfur.puppycraft.item.diaper;

import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.GeoItemRenderer;
import com.geckolib.util.GeckoLibUtil;
import com.llamalad7.mixinextras.lib.apache.commons.mutable.MutableObject;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.include.com.google.common.base.Supplier;
import org.spongepowered.include.com.google.common.base.Suppliers;

import java.util.function.Consumer;

public class DiaperItem extends Item  implements TrinketCallback {
    public String DiaperTexture;
    public DiaperItem(String texture, Properties properties) {
        properties.equippable(EquipmentSlot.LEGS);
        super(properties);
        DiaperTexture = texture;
    }

}
