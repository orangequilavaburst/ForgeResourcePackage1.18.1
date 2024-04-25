package com.idtech.client.container;

import com.idtech.block.BlockMod;
import com.idtech.block.entity.CottonCandyMachineBlockEntity;
import com.idtech.client.MenuTypeMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class CottonCandyMachineMenu extends AbstractContainerMenu {

    public final CottonCandyMachineBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public static final int RESULT_SLOT = 0;
    public static final int SUGAR_SLOT = 1;
    public static final int POTION_SLOT_START = 2;
    public static final int POTION_SLOT_END = 3;
    public static final int STICK_SLOT = 4;
    private static final int INV_SLOT_START = 5;
    private static final int INV_SLOT_END = 32;

    public CottonCandyMachineMenu(int pContainerId, Inventory pInventory, BlockEntity pBlockEntity, ContainerData pData) {
        super(MenuTypeMod.COTTON_CANDY_MACHINE_MENU.get(), pContainerId);

        checkContainerSize(pInventory, 5);
        blockEntity = (CottonCandyMachineBlockEntity) pBlockEntity;
        this.level = pInventory.player.level();
        this.data = pData;

        // inventory
        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(pInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }
        // hotbar
        for (int i = 0; i < 9; ++i){
            this.addSlot(new Slot(pInventory, i, 8 + i * 18, 142));
        }
        // slots
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, SUGAR_SLOT, 12, 35));
            for (int i = POTION_SLOT_START; i <= POTION_SLOT_END; i++){
                this.addSlot(new SlotItemHandler(iItemHandler, i, 56 + (i - POTION_SLOT_START)*20, 26));
            }
            this.addSlot(new SlotItemHandler(iItemHandler, STICK_SLOT, 66, 46));
            this.addSlot(new SlotItemHandler(iItemHandler, RESULT_SLOT, 140, 35));
        });
    }

    public int getSugarProgress(){
        int sugar = this.data.get(0);
        int max_sugar = this.data.get(1);
        int progress_size = 54;

        return (max_sugar != 0 && sugar != 0) ? progress_size * (sugar / max_sugar) : 0;
    }

    public int getSugar(){
        return this.data.get(0);
    }

    public int getMaxSugar(){
        return this.data.get(1);
    }

    public CottonCandyMachineMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(5));
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.index != RESULT_SLOT && super.canTakeItemForPickAll(pStack, pSlot);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        /*
        if (pPlayer instanceof ServerPlayer serverPlayer) {
            for (ItemStack itemStack : this.getItems()){
                if (!itemStack.isEmpty()) {
                    if (pPlayer.isAlive() && !((ServerPlayer)pPlayer).hasDisconnected()) {
                        pPlayer.getInventory().placeItemBackInInventory(itemStack);
                    } else {
                        pPlayer.drop(itemStack, false);
                    }
                }
            }
        }
        */
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        // look at this later plz
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, BlockMod.COTTON_CANDY_MACHINE.get());
    }

    public int getResultSlotIndex() {
        return RESULT_SLOT;
    }
    public boolean shouldMoveToInventory(int pSlotIndex) {
        return pSlotIndex != this.getResultSlotIndex();
    }
}
