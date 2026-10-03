package net.wkhan.lmcb.mixin.block;

import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.wkhan.lmcb.common.tag.ModTags;
import net.wkhan.lmcb.data.MultiBlockStructureMatcherData;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.block_entity.TerrestrialAgglomerationPlateBlockEntity;
import vazkii.botania.common.lib.BotaniaTags;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.function.Supplier;

import static net.wkhan.lmcb.LetMeConfigureBotania.MODID;
import static net.wkhan.lmcb.data.ReloadableData.MultiBlockStructureMatcherDataMap;

@Mixin(TerrestrialAgglomerationPlateBlockEntity.class)
public class TerrestrialAgglomerationPlateBlockEntityMixin {

    @Shadow(remap = false) @Mutable @Final
    public static Supplier<IMultiblock> MULTIBLOCK;

    @Unique
    private static MultiBlockStructureMatcherData lmcb$plateStructureData =
            MultiBlockStructureMatcherDataMap.get(ResourceLocation.parse(MODID+":terrestrial_agglomeration_plate"));
    @Unique
    private static String[][] lmcb$layerMap = lmcb$plateStructureData == null ? null :
            lmcb$plateStructureData.assembleLayerMap(
                    "[LMCB] Error in assembling 3D layer map for terrestrial agglomeration plate.");
    @Unique
    private static Object[] lmcb$symbolMap = lmcb$plateStructureData == null ? null :
            lmcb$plateStructureData.assembleSymbolMap();

    @Inject(
            method = "<clinit>",
            at = @At("RETURN")
    )
    private static void lmcb$replaceMultiblock(CallbackInfo ci) {
        MULTIBLOCK = Suppliers.memoize(() -> PatchouliAPI.get().makeMultiblock(
                new String[][] {
                        {"_____", "_____", "__P__", "_____", "_____"},
                        {"XXLXX", "XLLLX", "LL0LL", "XLLLX", "XXLXX"}
                },
                'P', BotaniaBlocks.terraPlate,
                'X', PatchouliAPI.get().tagMatcher(ModTags.Blocks.TERRA_PLATE_BASE_ALT),
                '0', PatchouliAPI.get().tagMatcher(BotaniaTags.Blocks.TERRA_PLATE_BASE),
                'L', PatchouliAPI.get().tagMatcher(BotaniaTags.Blocks.TERRA_PLATE_BASE)
        )); //safe fallback for mod initialization

        if (lmcb$plateStructureData == null)
            return;

        MULTIBLOCK = Suppliers.memoize(() -> PatchouliAPI.get().makeMultiblock(lmcb$layerMap, lmcb$symbolMap));
    }

    @Inject(
            method = "serverTick",
            at = @At("RETURN"),
            remap = false
    )
    private static void lmcb$setPlateStructureData(CallbackInfo ci) {
        if (lmcb$plateStructureData != null)
            return;
        lmcb$setPlateStructureData();
    }

    @Unique
    private static void lmcb$setPlateStructureData() {
        lmcb$plateStructureData = MultiBlockStructureMatcherDataMap.get(
                ResourceLocation.parse( MODID + ":terrestrial_agglomeration_plate")
        );
        lmcb$layerMap();
        lmcb$symbolMap();

        MULTIBLOCK = Suppliers.memoize(() -> PatchouliAPI.get().makeMultiblock(lmcb$layerMap, lmcb$symbolMap));
    }
    @Unique
    private static void lmcb$layerMap() {
        lmcb$layerMap = lmcb$plateStructureData
                .assembleLayerMap("[LMCB] Error in assembling 3D layer map for terrestrial agglomeration plate.");
    }
    @Unique
    private static void lmcb$symbolMap() {
        lmcb$symbolMap = lmcb$plateStructureData.assembleSymbolMap();
    }
}

