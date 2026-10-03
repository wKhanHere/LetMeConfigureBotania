package net.wkhan.lmcb.mixin.block.RunicAltar;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import net.wkhan.lmcb.data.duckfaces.ICatalystRunicAltarRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import vazkii.botania.common.crafting.RunicAltarRecipe;

@Mixin(RunicAltarRecipe.class)
public abstract class RunicAltarRecipeMixin implements ICatalystRunicAltarRecipe, vazkii.botania.api.recipe.RunicAltarRecipe {
    @Unique
    private ItemStack lmcb$catalyst = ItemStack.EMPTY;

    @Override
    public Ingredient getReagent() {
        return lmcb$catalyst.isEmpty() ?
                Ingredient.of(ForgeRegistries.ITEMS.getValue(ResourceLocation.fromNamespaceAndPath("botania", "livingrock"))) :
                Ingredient.of(lmcb$catalyst);
    }

    @Override
    public ItemStack lmcb$getCatalyst() {
        return lmcb$catalyst;
    }

    @Override
    public void lmcb$setCatalyst(ItemStack catalyst) {
        this.lmcb$catalyst = catalyst;
    }
}
