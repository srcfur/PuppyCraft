package com.srcfur.puppycraft.attachment;

import com.mojang.serialization.Codec;
import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.PuppyCraftCommon;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

public class PuppyCraftAttachments {
    public static AttachmentType<Integer> Maturity;
    public static void initialize(){
        Maturity = AttachmentRegistry.create(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "maturity"),
                builder->builder.initializer(()-> PuppyCraftCommon.DefaultMaturity)
                        .syncWith(ByteBufCodecs.INT, AttachmentSyncPredicate.all())
                        .persistent(Codec.INT));
    }
}
