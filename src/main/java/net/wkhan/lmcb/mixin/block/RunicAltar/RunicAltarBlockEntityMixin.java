package net.wkhan.lmcb.mixin.block.RunicAltar;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.wkhan.lmcb.data.duckfaces.ICatalystRunicAltarRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.api.recipe.RunicAltarRecipe;
import vazkii.botania.common.block.block_entity.RunicAltarBlockEntity;
import vazkii.botania.common.block.block_entity.SimpleInventoryBlockEntity;

import static net.wkhan.lmcb.common.tag.ModTags.Items.RUNE_ITEM;


@Mixin(RunicAltarBlockEntity.class)
public abstract class RunicAltarBlockEntityMixin extends SimpleInventoryBlockEntity{
    protected RunicAltarBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @WrapOperation(
            method = "onUsedByWand",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;asItem()Lnet/minecraft/world/item/Item;"
            )
    )
    private Item lmcb$allowUseAnyBaseIngredientForAltar(Block instance, Operation<Item> original,
                                                                      @Local(name = "recipe") RunicAltarRecipe recipe) {
        return ((ICatalystRunicAltarRecipe) recipe).lmcb$getCatalyst().getItem();
    } //Allows custom catalyst other than livingrock

    @WrapOperation(
            method = "onUsedByWand",
            at = @At(
                    value = "CONSTANT",
                    args = "classValue=vazkii/botania/common/item/material/RuneItem"
            ),
            remap = false
    )
    private boolean lmcb$allowVariableRuneMaterial(Object object, Operation<Boolean> original) {
        return ((Item) object).getDefaultInstance().is(RUNE_ITEM) || original.call(object);
    } //Allows saving custom items other than runes in altar recipe

    @WrapOperation(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private static boolean lmcb$allowAnyDroppedToBeAccepted(ItemStack instance, Item pItem, Operation<Boolean> original) {
        return false;
    } //Allows any dropped item on runic altar to be accepted by it

    @WrapOperation(
            method = "addItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean lmcb$allowPlaceNoItem(ItemStack instance, Item pItem, Operation<Boolean> original) {
        return false;
    } //Disallows the quick item entity spawn placing for runic altar (livingrock by default)

}
