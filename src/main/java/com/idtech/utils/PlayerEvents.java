package com.idtech.utils;

import com.idtech.BaseMod;
import com.idtech.item.custom.LaserDrillItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

// thank you, DireWolf20! https://github.com/Direwolf20-MC/JustDireThings/blob/main/src/main/java/com/direwolf20/justdirethings/client/events/PlayerEvents.java
@Mod.EventBusSubscriber(modid = BaseMod.MODID)
public class PlayerEvents {

    private static BlockPos destroyPos = BlockPos.ZERO;
    private static int gameTicksMining = 0;

    @SubscribeEvent
    public static void leftClickBlockEvent(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof LaserDrillItem laserDrillItem){
            doExtraCrumblings(event, itemStack, Set.of(event.getPos()));
        }
    }

    @SubscribeEvent
    public static void useItemEvent(LivingEntityUseItemEvent event){
        ItemStack itemStack = event.getItem();

        if (itemStack.getItem() instanceof LaserDrillItem laserDrillItem){
            if (laserDrillItem.getBlockMiningPos() != null){
                doExtraCrumblings(event, itemStack, Set.of(laserDrillItem.getBlockMiningPos()));
            }
        }

    }

    private static void doExtraCrumblings(PlayerInteractEvent.LeftClickBlock event, ItemStack itemStack, Set<BlockPos> blocksToBreak) {
        Player player = event.getEntity();
        Level level = player.level();
        BlockPos blockPos = event.getPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (event.getAction() == PlayerInteractEvent.LeftClickBlock.Action.START) { //Client and Server
            if (level.isClientSide) {
                gameTicksMining = 0;
                destroyPos = blockPos;
            }
        }
        if (event.getAction() == PlayerInteractEvent.LeftClickBlock.Action.STOP || event.getAction() == PlayerInteractEvent.LeftClickBlock.Action.ABORT) { //Server Only
            cancelBreaks(level, blockState, blockPos, player, itemStack, blocksToBreak);
        }
        if (event.getAction() == PlayerInteractEvent.LeftClickBlock.Action.CLIENT_HOLD) { //Client Only
            if (blockPos.equals(destroyPos)) {
                gameTicksMining++;
            } else {
                gameTicksMining = 0;
                destroyPos = blockPos;
            }
            incrementDestroyProgress(level, blockState, blockPos, player, itemStack, blocksToBreak);
        }
    }

    private static void doExtraCrumblings(LivingEntityUseItemEvent event, ItemStack itemStack, Set<BlockPos> blocksToBreak) {
        if (event.getEntity() instanceof Player player) {
            Level level = player.level();
            BlockPos blockPos = blocksToBreak.stream().toList().get(0);
            BlockState blockState = level.getBlockState(blockPos);
            if (event.getDuration() == 0) {
                if (level.isClientSide) {
                    gameTicksMining = 0;
                    destroyPos = blockPos;
                }
            }
            if (event.getDuration() >= 0) {
                if (blockPos.equals(destroyPos)) {
                    gameTicksMining++;
                } else {
                    gameTicksMining = 0;
                    destroyPos = blockPos;
                }
                incrementDestroyProgress(level, blockState, blockPos, player, itemStack, blocksToBreak);
            }
            if (event.getDuration() >= event.getItem().getUseDuration()) { //Server Only
                cancelBreaks(level, blockState, blockPos, player, itemStack, blocksToBreak);
            }
        }
    }

    private static float incrementDestroyProgress(Level level, BlockState pState, BlockPos pPos, Player player, ItemStack toggleableToolStack, Set<BlockPos> blocksToBreak) {
        int i = gameTicksMining;
        float f = pState.getDestroyProgress(player, player.level(), pPos) * (float) (i + 1);
        int j = (int) (f * 10.0F);
        for (BlockPos blockPos : blocksToBreak) {
            if (blockPos.equals(pPos)) continue; //Let the vanilla mechanics handle the block we're hitting
            if (level.isClientSide)
                level.destroyBlockProgress(player.getId() + generatePosHash(blockPos), blockPos, j);
            else
                sendDestroyBlockProgress(player.getId() + generatePosHash(blockPos), blockPos, -1, (ServerPlayer) player);
        }
        return f;
    }

    private static void cancelBreaks(Level level, BlockState pState, BlockPos pPos, Player player, ItemStack toggleableToolStack, Set<BlockPos> blocksToBreak) {
        for (BlockPos blockPos : blocksToBreak) {
            if (blockPos.equals(pPos)) continue; //Let the vanilla mechanics handle the block we're hitting
            player.level().destroyBlockProgress(player.getId() + generatePosHash(blockPos), blockPos, -1);
        }
    }

    public static int generatePosHash(BlockPos blockPos) {
        return (31 * 31 * blockPos.getX()) + (31 * blockPos.getY()) + blockPos.getZ(); //For now this is probably good enough, will add more randomness if needed
    }

    public static void sendDestroyBlockProgress(int pBreakerId, BlockPos pPos, int pProgress, ServerPlayer serverPlayer) {
        serverPlayer.connection.send(new ClientboundBlockDestructionPacket(pBreakerId, pPos, pProgress));
    }

}
