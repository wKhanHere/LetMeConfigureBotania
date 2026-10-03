package net.wkhan.lmcb.mixin.block.RunicAltar;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import net.wkhan.lmcb.data.duckfaces.ICatalystRunicAltarRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.common.crafting.RunicAltarRecipe;

@Mixin(RunicAltarRecipe.Serializer.class)
public abstract class RunicAltarRecipeSerializerMixin {

    @ModifyReturnValue(
            method = "fromJson(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonObject;)Lvazkii/botania/common/crafting/RunicAltarRecipe;",
            at = @At(value = "RETURN"),
            remap = false
    )
    private RunicAltarRecipe lmcb$fromJsonCatalystCompat(RunicAltarRecipe original,
                                                                       @Local(argsOnly = true) JsonObject json) {
        ResourceLocation livingrockLocation = ResourceLocation.fromNamespaceAndPath("botania", "livingrock");
        Ingredient catalystIngredient = json.has("catalyst") ?
                Ingredient.fromJson(json.get("catalyst")) : Ingredient.of(ForgeRegistries.ITEMS.getValue(livingrockLocation));
        ((ICatalystRunicAltarRecipe) original).lmcb$setCatalyst(catalystIngredient.getItems()[0]);
        return original;
    }

    @ModifyReturnValue(
            method = "fromNetwork(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/FriendlyByteBuf;)Lvazkii/botania/common/crafting/RunicAltarRecipe;",
            at = @At(value = "RETURN"),
            remap = false
    )
    private RunicAltarRecipe lmcb$fromNetworkCatalystCompat(RunicAltarRecipe original,
                                                                          @Local(argsOnly = true) FriendlyByteBuf buffer) {
        ((ICatalystRunicAltarRecipe) original).lmcb$setCatalyst(buffer.readItem());
        return original;
    }

    @Inject(
            method = "toNetwork(Lnet/minecraft/network/FriendlyByteBuf;Lvazkii/botania/common/crafting/RunicAltarRecipe;)V",
            at = @At(value = "TAIL"),
            remap = false
    )
    private void lmcb$toNetworkCatalystCompat(FriendlyByteBuf buf, RunicAltarRecipe recipe, CallbackInfo ci) {
        buf.writeItem(recipe.getReagent().getItems()[0]);
    }
}
