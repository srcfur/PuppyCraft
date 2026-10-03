package com.srcfur.puppycraft.mixin;

import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ReallyStupidGamePacketHijackMixin {
    @Shadow
    public abstract boolean hasClientLoaded();
    @Shadow
    public ServerPlayer player;
    @Shadow
    public Vec3 awaitingPositionFromClient;

    @Inject(at=@At("HEAD"), cancellable = true, method = "handlePlayerCommand")
    private void PC$handlePlayerCommand(ServerboundPlayerCommandPacket packet, CallbackInfo ci){
        if(hasClientLoaded()){
            if(packet.getAction() == ServerboundPlayerCommandPacket.Action.STOP_SLEEPING){
                ci.cancel();
                player.resetLastActionTime();
                player.stopSleepInBed(true, true);
                awaitingPositionFromClient = player.position();
            }
        }
    }
}
