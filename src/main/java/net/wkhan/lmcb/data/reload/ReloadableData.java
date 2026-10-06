package net.wkhan.lmcb.data.reload;

import net.minecraft.resources.ResourceLocation;
import net.wkhan.lmcb.data.MultiBlockStructureMatcherData;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.HashMap;

public class ReloadableData {

    public static final HashMap<ResourceLocation, MultiBlockStructureMatcherData> MultiBlockStructureMatcherDataMap = new HashMap<>();
    public static final HashMap<ResourceLocation, IMultiblock> BuiltMultiBlocks = new HashMap<>();
    public static final HashMap<ResourceLocation, IMultiblock> ClientBuiltMultiBlocks = new HashMap<>();

    public static IMultiblock buildMultiBlock(ResourceLocation id) {
        MultiBlockStructureMatcherData multiBlockData = MultiBlockStructureMatcherDataMap.get(id);
        return PatchouliAPI.get().makeMultiblock(
                multiBlockData.assembleLayerMap("[LMCB] Error in assembling 3D layer map for terrestrial agglomeration plate."),
                multiBlockData.assembleSymbolMap()
        );
    }

    //Some sort of register-able channel for multiblocks so I can register in that class and it automatically handles its dynamic building
    //hard coded for now
}
