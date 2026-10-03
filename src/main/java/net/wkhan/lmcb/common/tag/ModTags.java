package net.wkhan.lmcb.common.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.wkhan.lmcb.LetMeConfigureBotania;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> RUNE_ITEM = tag("rune_item");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(LetMeConfigureBotania.MODID, name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> TERRA_PLATE_BASE_ALT = tag("terra_plate_base_alt");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(LetMeConfigureBotania.MODID, name));
        }
    }

    public static class Entities {


        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(LetMeConfigureBotania.MODID, name));
        }
    }
}
