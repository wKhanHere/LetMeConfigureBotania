package net.wkhan.lmcb.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import net.wkhan.lmcb.compat.kubejs.event.LMCBEvents;

public class KubeJSLMCBPlugin extends KubeJSPlugin {

    @Override
    public void registerEvents() {
        LMCBEvents.NATURES_AURA_PLUS_GROUP.register();
    }
}
