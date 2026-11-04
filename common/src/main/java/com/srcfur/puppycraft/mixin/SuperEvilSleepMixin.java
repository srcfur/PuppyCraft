package com.srcfur.puppycraft.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.SleepStatus;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(ServerLevel.class)
public abstract class SuperEvilSleepMixin extends Level {
    protected SuperEvilSleepMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }
    @Shadow
    private SleepStatus sleepStatus;
    @Shadow
    private MinecraftServer server;
    @Shadow
    private List<ServerPlayer> players;

    @Shadow
    abstract GameRules getGameRules();
    @Shadow
    abstract void wakeUpAllPlayers();
    @Shadow
    abstract void resetWeatherCycle();

    @Inject(at=@At("HEAD"), method = "tick")
    private void PC$tick(CallbackInfo info){
        int percentage = this.getGameRules().get(GameRules.PLAYERS_SLEEPING_PERCENTAGE);
        if (this.sleepStatus.areEnoughSleeping(percentage) && this.sleepStatus.areEnoughDeepSleeping(percentage, this.players)) {
            Optional<Holder<WorldClock>> defaultClock = this.dimensionType().defaultClock();
            if (this.getGameRules().get(GameRules.ADVANCE_TIME) && defaultClock.isPresent()) {
                this.server.clockManager().moveToTimeMarker(defaultClock.get(), ClockTimeMarkers.WAKE_UP_FROM_SLEEP);
            }

            this.wakeUpAllPlayers();
            if (this.getGameRules().get(GameRules.ADVANCE_WEATHER) && this.isRaining()) {
                this.resetWeatherCycle();
            }
        }
    }
}
