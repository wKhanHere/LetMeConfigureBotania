package net.wkhan.lmcb.mixin.block.RunicAltar;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.api.recipe.RunicAltarRecipe;
import vazkii.botania.client.integration.emi.BotaniaEmiRecipe;
import vazkii.botania.client.integration.emi.RunicAltarEmiRecipe;

import java.util.List;
import java.util.stream.Stream;

@Mixin(RunicAltarEmiRecipe.class)
public abstract class RunicAltarEmiRecipeMixin extends BotaniaEmiRecipe {
    public RunicAltarEmiRecipeMixin(EmiRecipeCategory category, Recipe<?> recipe) {
        super(category, recipe);
    }

    @Unique
    private EmiStack lmcb$catalystEmiStack;

    @WrapOperation(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/stream/Stream;concat(Ljava/util/stream/Stream;Ljava/util/stream/Stream;)Ljava/util/stream/Stream;"
            )
    )
    private <T> Stream<T> lmcb$setLivingRockToCatalyst(Stream<? extends T> a, Stream<? extends T> b,
                                                                     Operation<Stream<T>> original,
                                                                     @Local(argsOnly = true) RunicAltarRecipe recipe) {
        lmcb$catalystEmiStack = EmiStack.of(recipe.getReagent().getItems()[0]);
        return original.call(a, Stream.of(lmcb$catalystEmiStack));
    }

    @WrapOperation(
            method = "addWidgets",
            at = @At(
                    value = "INVOKE",
                    target = "Lvazkii/botania/client/integration/emi/RunicAltarEmiRecipe;addRunicAltarWidgets(Ldev/emi/emi/api/widget/WidgetHolder;Ldev/emi/emi/api/recipe/EmiRecipe;Ljava/util/List;Ldev/emi/emi/api/stack/EmiIngredient;Ldev/emi/emi/api/stack/EmiStack;[Ldev/emi/emi/api/stack/EmiIngredient;)V"
            ),
            remap = false
    )
    private void lmcb$drawCatalystInWidget(WidgetHolder widgets, EmiRecipe recipe, List<EmiIngredient> input,
                                                         EmiIngredient altar, EmiStack output, EmiIngredient[] reagents,
                                                         Operation<Void> original) {
        original.call(widgets, recipe, input, altar, output, new EmiIngredient[]{lmcb$catalystEmiStack});
    }

}
