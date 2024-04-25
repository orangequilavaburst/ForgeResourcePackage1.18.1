package com.idtech.block.entity;

import com.idtech.BaseMod;
import com.idtech.client.container.CottonCandyMachineMenu;
import com.idtech.client.screen.CottonCandyMachineScreen;
import com.idtech.item.ItemMod;
import com.idtech.utils.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CottonCandyMachineBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(5);

    private static final int RESULT_SLOT = 0;
    private static final int SUGAR_SLOT = 1;
    private static final int POTION_SLOT_START = 2;
    private static final int POTION_SLOT_END = 3;
    private static final int STICK_SLOT = 4;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int sugar = 0;
    private int max_sugar = 256;
    private Item sugar_item = Items.SUGAR;

    public CottonCandyMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityMod.COTTON_CANDY_MACHINE.get(), pPos, pBlockState);

        this.sugar = 0;
        this.max_sugar = 256;

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch(pIndex){
                    case 0 -> CottonCandyMachineBlockEntity.this.sugar;
                    case 1 -> CottonCandyMachineBlockEntity.this.max_sugar;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch(pIndex){
                    case 0 -> CottonCandyMachineBlockEntity.this.sugar = pValue;
                    case 1 -> CottonCandyMachineBlockEntity.this.max_sugar = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
        if (this.sugar > 0){
            Containers.dropItemStack(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(),
                    new ItemStack(this.sugar_item, this.sugar));
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.".concat(BaseMod.MODID).concat(".cotton_candy_machine"));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CottonCandyMachineMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("sugar", sugar);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        sugar = pTag.getInt("sugar");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        // handle sugar
        if (this.canPutInSugar()){
            if (this.canIncreaseSugar()) {
                while (this.canIncreaseSugar()) {
                    this.itemHandler.extractItem(SUGAR_SLOT, 1, false);
                    sugar++;
                }
                setChanged(pLevel, pPos, pState);
            }
        }

        // handle cotton candy
        List<MobEffectInstance> effects = new ArrayList<>();
        for (int i = POTION_SLOT_START; i <= POTION_SLOT_END; i++){
            if (this.itemHandler.getStackInSlot(i).getItem() instanceof PotionItem potionItem){
                effects.addAll(PotionUtils.getMobEffects(this.itemHandler.getStackInSlot(i)));
            }
        }

        if (this.hasRecipe()){
            ItemStack cottonCandyItemStack = new ItemStack(ItemMod.COTTON_CANDY.get(), 1);
            PotionUtils.setCustomEffects(cottonCandyItemStack, effects);
            this.itemHandler.setStackInSlot(RESULT_SLOT, cottonCandyItemStack);
        }

    }

    private boolean canPutInSugar(){
        return this.itemHandler.getStackInSlot(SUGAR_SLOT).getTags().toList().contains(TagMod.Items.COTTON_CANDY_SUGAR);
    }

    private boolean canIncreaseSugar(){
        return !this.itemHandler.getStackInSlot(SUGAR_SLOT).isEmpty() && sugar < max_sugar;
    }

    private boolean hasRecipe(){
        return sugar > 0 && this.itemHandler.getStackInSlot(STICK_SLOT).getTags().toList().contains(Tags.Items.RODS_WOODEN)
                && this.itemHandler.getStackInSlot(STICK_SLOT).getCount() > 0;
    }

}
