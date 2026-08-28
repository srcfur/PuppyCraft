package com.srcfur.puppycraft.attachment;

import com.mojang.serialization.Codec;
import com.srcfur.puppycraft.Constants;
import com.srcfur.puppycraft.PuppyCraftCommon;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod(Constants.MOD_ID)
public class PuppyCraftAttachments {
    public PuppyCraftAttachments(IEventBus bus){
        Attachments.register(bus);
    }
    public static final DeferredRegister<AttachmentType<?>> Attachments = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Constants.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> Maturity = Attachments.register("maturity", ()->
            AttachmentType.<Integer>builder(()-> PuppyCraftCommon.DefaultMaturity).sync(ByteBufCodecs.INT).serialize(Codec.INT.fieldOf("maturity")).build());
}
