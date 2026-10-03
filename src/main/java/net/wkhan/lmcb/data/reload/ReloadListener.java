package net.wkhan.lmcb.data.reload;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.wkhan.lmcb.data.MultiBlockStructureMatcherData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.wkhan.lmcb.LetMeConfigureBotania.LMCB_LOGGER;
import static net.wkhan.lmcb.data.ReloadableData.MultiBlockStructureMatcherDataMap;

public class ReloadListener extends SimpleJsonResourceReloadListener {
    public ReloadListener() {
        super(new Gson(), "common");
    }

    protected static final List<String> loadedStructures = new ArrayList<>();

    @Override
    protected void apply(
            Map<ResourceLocation, JsonElement> data,
            @NotNull ResourceManager manager,
            @NotNull ProfilerFiller profiler
    ) {
        clearData();
        data.forEach((fileId, jsonElement) -> {
            try {
                JsonObject json = jsonElement.getAsJsonObject();
                if (!json.has("type")) {
                    LMCB_LOGGER.error("Missing 'type' field in rule file: {}", fileId);
                    return;
                }
                String type = json.get("type").getAsString();
                switch (type) { //don't listen to intelliJ, let this be a switch statement
                    case "multiblock_structure" -> {
                        DataResult<MultiBlockStructureMatcherData> result = MultiBlockStructureMatcherData.CODEC.parse(JsonOps.INSTANCE, json)
                                .mapError(originalError -> "Error in file '" + fileId + "': " + originalError);
                        result.resultOrPartial(
                                        errorMessage -> LMCB_LOGGER.error("[LMCB] Multiblock Structure JSON Error: {}", errorMessage))
                                .ifPresent(rule -> {
                                    loadedStructures.add(fileId.toString());
                                    MultiBlockStructureMatcherDataMap.put(ResourceLocation.parse(rule.structureId()), rule);
                                });
                    }

                    default -> LMCB_LOGGER.error("Unknown rule type '{}' in file: {}", type, fileId);
                }
            }
            catch (Exception e) {
                LMCB_LOGGER.error("Failed to load rule: {}\nHere's your stack:\n", fileId, e);
            }
        });
    }

    private void clearData() {
        MultiBlockStructureMatcherDataMap.clear();
    }

//    @Mod.EventBusSubscriber(modid = LetMeConfigureBotania.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
//    public static class onServerSetupEvents {
//
//        @SubscribeEvent
//        public static void onTagsUpdated(TagsUpdatedEvent event) {
//            multiblock structures auto-update on reload, not soon :tm:
//        }
//
//        @SubscribeEvent
//        public static void onRecipesUpdated(RecipesUpdatedEvent event) {
//
//        }
//    }
}