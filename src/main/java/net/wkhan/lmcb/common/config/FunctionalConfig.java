package net.wkhan.lmcb.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.wkhan.lmcb.LetMeConfigureBotania;

@Mod.EventBusSubscriber(modid = LetMeConfigureBotania.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FunctionalConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    //Tigerseye
    public static final ForgeConfigSpec.IntValue MIN_MANA_FOR_TIGERSEYE;
    public static final ForgeConfigSpec.IntValue MANA_COST_FOR_TIGERSEYE;
    public static final ForgeConfigSpec.IntValue MAX_MANA_FOR_TIGERSEYE;
    public static final ForgeConfigSpec.IntValue RADIUS_FOR_TIGERSEYE;

    static {
        BUILDER.comment("Config for Tigerseye").push("Tigerseye");
        MIN_MANA_FOR_TIGERSEYE = BUILDER
                .comment("Minimum mana required by Tigerseye to function. (Default: 70)")
                .defineInRange("tigerseyeMinMana", 70, 0, Integer.MAX_VALUE);
        MANA_COST_FOR_TIGERSEYE = BUILDER
                .comment("Mana cost for Tigerseye per tick. (Default: 70)")
                .defineInRange("tigerseyeManaCost", 70, 0, Integer.MAX_VALUE);
        MAX_MANA_FOR_TIGERSEYE = BUILDER
                .comment("Max internal mana storage for Tigerseye. (Default: 1000)")
                .defineInRange("tigerseyeMaxMana", 1000, 0, Integer.MAX_VALUE);
        RADIUS_FOR_TIGERSEYE = BUILDER
                .comment("Max radius for Tigerseye functioning. (Default: 10)")
                .defineInRange("tigerseyeRadius", 10, 0, Integer.MAX_VALUE);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if(event.getConfig().getSpec() != SPEC)
            return;

    }
}
