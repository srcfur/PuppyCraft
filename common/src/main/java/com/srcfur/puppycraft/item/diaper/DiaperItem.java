package com.srcfur.puppycraft.item.diaper;

import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public class DiaperItem extends Item implements TrinketCallback {
    public String DiaperTexture;
    public DiaperFamilies Family;
    public DiaperItem(String texture, DiaperFamilies family, Properties properties) {
        super(properties);
        DiaperTexture = texture;
        Family = family;
    }

}
