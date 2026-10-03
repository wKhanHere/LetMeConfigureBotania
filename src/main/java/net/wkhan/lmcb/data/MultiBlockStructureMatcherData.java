package net.wkhan.lmcb.data;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.wkhan.lmcb.LMCBUtils;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

import static net.wkhan.lmcb.LMCBUtils.INT_KEY;
import static net.wkhan.lmcb.LetMeConfigureBotania.LMCB_LOGGER;


public record MultiBlockStructureMatcherData(
        String structureId,
        Map<Integer, Map<Integer, String>> structure3DLayeredMap,
        Map<String, Either<Block, TagKey<Block>>> symbolToBlockMap
) {
    public static final Codec<MultiBlockStructureMatcherData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("structure").forGetter(MultiBlockStructureMatcherData::structureId),
                    Codec.unboundedMap(INT_KEY, Codec.unboundedMap(INT_KEY, Codec.STRING))
                            .fieldOf("layout").forGetter(MultiBlockStructureMatcherData::structure3DLayeredMap),
                    Codec.unboundedMap(Codec.STRING, LMCBUtils.elementOrTagCodec(ForgeRegistries.BLOCKS, Registries.BLOCK))
                            .fieldOf("symbol_map").forGetter(MultiBlockStructureMatcherData::symbolToBlockMap)
            ).apply(instance, MultiBlockStructureMatcherData::new)
    );

    public String[][] assembleLayerMap(String errorMsg) {
        int layerCount = structure3DLayeredMap.size();
        OptionalInt optionalMaxRowCountPerLayer = structure3DLayeredMap.values().stream().mapToInt(Map::size).max();
        if (optionalMaxRowCountPerLayer.isEmpty()) {
            LMCB_LOGGER.error(errorMsg);
            return new String[0][0];
        }

        String[][] layerMap = new String[layerCount][optionalMaxRowCountPerLayer.getAsInt()];
        for (int i = 0; i < layerCount; i++) {
            for (int j = 0; j < structure3DLayeredMap.get(i).size(); j++) {
                layerMap[i][j] = structure3DLayeredMap.get(i).get(j);
            }
        }

        return layerMap;
    }

    public Object[] assembleSymbolMap() {
        List<Object> symbolMap = new ArrayList<>();
        for (String symbol : symbolToBlockMap.keySet()) {
            symbolMap.add(symbol.charAt(0));
            symbolToBlockMap.get(symbol)
                    .ifRight(blockTag -> symbolMap.add(PatchouliAPI.get().tagMatcher(blockTag)))
                    .ifLeft(symbolMap::add);
        }
        return symbolMap.toArray();
    }

    //Currently, incorrect structure JSON files have no explicit safety nets built to catch errors or crashes. Happy pack dev-ing!
}
