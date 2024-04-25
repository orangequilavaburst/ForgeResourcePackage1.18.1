package com.idtech.client.container;

import com.idtech.client.MenuTypeMod;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CottonCandyMachineMenu extends AbstractContainerMenu {

    public static final int RESULT_SLOT = 0;
    public static final int SUGAR_SLOT = 1;
    public static final int POTION_SLOT_START = 2;
    public static final int POTION_SLOT_END = 4;
    public static final int STICK_SLOT = 4;
    private static final int INV_SLOT_START = 5;
    private static final int INV_SLOT_END = 32;

    private final ContainerLevelAccess access;
    private final Player player;

    public CottonCandyMachineMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(MenuTypeMod.COTTON_CANDY_MACHINE_MENU.get(), pContainerId);
        this.player = pPlayerInventory.player;
        this.access = pAccess;

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(pPlayerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }
    }

    public CottonCandyMachineMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf buf) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    public int getResultSlotIndex() {
        return RESULT_SLOT;
    }
    public boolean shouldMoveToInventory(int pSlotIndex) {
        return pSlotIndex != this.getResultSlotIndex();
    }
}
