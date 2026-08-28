package com.srcfur.puppycraft.block;

import com.srcfur.badhygiene.BadHygieneCommon;
import com.srcfur.badhygiene.api.AbstractHygienePlayer;
import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.badhygiene.effect.BadHygieneEffects;
import com.srcfur.puppycraft.PuppyCraftCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PuppyPadBlock extends CarpetBlock {
    public enum PuppyPadUseState implements StringRepresentable {
        DRY("dry"),
        LIGHT("light"),
        WET("wet"),
        SOAKED("soaked");
        PuppyPadUseState(String s){
            name = s;
        }
        final String name;
        @Override
        public String getSerializedName() {
            return name;
        }
    }
    public static final EnumProperty<PuppyPadUseState> USESTATE = EnumProperty.create("pad_usage", PuppyPadUseState.class);
    public PuppyPadBlock(Properties p_152915_) {
        super(p_152915_);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(USESTATE));
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        AbstractHygienePlayer hygienePlayer = BadHygieneCommon.API.getHygienePlayer(player);
        if(player.isCrouching() && !isPuppyPadSoiled(pos, level) && (player.isCreative() || hygienePlayer.getBladderFullness() > 0.1)){
            if(pos.distSqr(player.blockPosition()) < 1){
                urinateOnPad(pos, level);
                hygienePlayer.setBladder(0);
                //Won't drop but won't go past gained continence either!
                hygienePlayer.setContinence(Math.max(hygienePlayer.getContinence() + 3, Math.max(PuppyCraftCommon.MaximumGainedContinence, hygienePlayer.getContinence())));
                player.addEffect(new MobEffectInstance(Holder.direct(BadHygieneEffects.Incontinence), 6000, 0));
                return InteractionResult.CONSUME;
            }
            else
            {
                if(level.isClientSide()){
                    player.sendSystemMessage(Component.translatable("puppycraft.puppy_pad.out_of_reach"));
                }
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockState rootpad = level.getBlockState(pos);
        if(rootpad.getValue(USESTATE).ordinal() > PuppyPadUseState.DRY.ordinal()){
            //Data collection!
            ArrayList<BlockPos> connected = new ArrayList<>();
            connected.add(pos);
            getConnectedPuppyPads(connected, pos, level);
            connected.sort(Comparator.comparingInt(a -> (int) a.distSqr(pos)));

            //start at one to skip the root!
            for(int i = 1; i < connected.size(); i++){
                BlockState neighbor = level.getBlockState(connected.get(i));
                if(neighbor.getValue(USESTATE).ordinal() + 1 < rootpad.getValue(USESTATE).ordinal()) {
                    level.setBlockAndUpdate(connected.get(i), neighbor.setValue(USESTATE, PuppyPadUseState.values()[neighbor.getValue(USESTATE).ordinal() + 1]));
                    level.setBlockAndUpdate(pos, rootpad.setValue(USESTATE, PuppyPadUseState.values()[rootpad.getValue(USESTATE).ordinal() - 1]));
                    break;
                }
            }
        }
        super.randomTick(state, level, pos, random);
    }

    private void getConnectedPuppyPads(ArrayList<BlockPos> validPoses, BlockPos pos, Level level) {
        ArrayList<BlockPos> toCheck = new ArrayList<>();
        toCheck.add(pos.north()); toCheck.add(pos.east()); toCheck.add(pos.south()); toCheck.add(pos.west());
        while(!toCheck.isEmpty()){
            BlockPos checkpos = toCheck.getFirst();
            toCheck.removeFirst();
            if(level.getBlockState(checkpos).getBlock() == PuppyCraftBlocks.PuppyPad.get() && !validPoses.contains(checkpos) && validPoses.getFirst().distSqr(checkpos) < 5f){
                validPoses.add(checkpos);
                //Recursive part (fuck ass)
                getConnectedPuppyPads(validPoses, checkpos, level);
            }
        }
    }

    private boolean isPuppyPadSoiled(BlockPos pos, Level level) {
        //Data collection!
        ArrayList<BlockPos> connected = new ArrayList<>();
        connected.add(pos);
        getConnectedPuppyPads(connected, pos, level);

        boolean canBeUsed = false;
        for(int i = 1; i < connected.size(); i++){
            canBeUsed = canBeUsed || level.getBlockState(connected.get(i)).getValue(USESTATE) != PuppyPadUseState.SOAKED;
        }
        return !canBeUsed;
    }

    private void urinateOnPad(BlockPos pos, Level level){
        //Data collection!
        ArrayList<BlockPos> connected = new ArrayList<>();
        connected.add(pos);
        getConnectedPuppyPads(connected, pos, level);
        connected.sort(Comparator.comparingInt(a -> (int) a.distSqr(pos)));

        boolean hasPeed = false;
        while(!hasPeed && !connected.isEmpty()){
            BlockPos peeSpot = connected.getFirst();
            connected.removeFirst();
            hasPeed = level.getBlockState(peeSpot).getValue(USESTATE) != PuppyPadUseState.SOAKED;
            if(hasPeed){
                level.setBlockAndUpdate(peeSpot, level.getBlockState(peeSpot).setValue(USESTATE, PuppyPadUseState.values()[level.getBlockState(peeSpot).getValue(USESTATE).ordinal() + 1]));
            }
        }
    }
}
