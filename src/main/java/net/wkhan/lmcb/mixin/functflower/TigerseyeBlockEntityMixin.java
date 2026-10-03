package net.wkhan.lmcb.mixin.functflower;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.common.block.flower.functional.TigerseyeBlockEntity;

import static net.wkhan.lmcb.common.config.FunctionalConfig.*;

@Mixin(TigerseyeBlockEntity.class)
public abstract class TigerseyeBlockEntityMixin extends FunctionalFlowerBlockEntity {
    public TigerseyeBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @WrapOperation(
            method = "tickFlower",
            at = @At(value = "INVOKE", target = "Lvazkii/botania/common/block/flower/functional/TigerseyeBlockEntity;getMana()I"),
            remap = false
    )
    private int lmcb$configurableMinManaForTigerseyePacify(TigerseyeBlockEntity instance, Operation<Integer> original) {
        return original.call(instance)+70-MIN_MANA_FOR_TIGERSEYE.get();
    }

    @WrapOperation(
            method = "tickFlower",
            at = @At(value = "INVOKE", target = "Lvazkii/botania/common/block/flower/functional/TigerseyeBlockEntity;addMana(I)V"),
            remap = false
    )
    private void lmcb$configurableManaCostForTigerseyePacify(TigerseyeBlockEntity instance, int i, Operation<Void> original) {
        original.call(instance, -MANA_COST_FOR_TIGERSEYE.get());
    }

    @WrapMethod(
            method = "getMaxMana",
            remap = false
    )
    private int lmcb$configurableTigerseyeMaxMana(Operation<Integer> original) {
        return MAX_MANA_FOR_TIGERSEYE.get();
    }

    @WrapOperation(
            method = "getRadius",
            at = @At(value = "INVOKE", target = "Lvazkii/botania/api/block_entity/RadiusDescriptor$Rectangle;square(Lnet/minecraft/core/BlockPos;I)Lvazkii/botania/api/block_entity/RadiusDescriptor$Rectangle;"),
            remap = false
    )
    private RadiusDescriptor.Rectangle lmcb$configurableTigerseyeRadius(BlockPos subtileCoords, int expand,
                                                                               Operation<RadiusDescriptor.Rectangle> original) {
        return original.call(subtileCoords, RADIUS_FOR_TIGERSEYE.get());
    }
}
