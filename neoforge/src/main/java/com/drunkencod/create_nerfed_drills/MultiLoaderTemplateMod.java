package com.drunkencod.create_nerfed_drills;

import com.drunkencod.create_nerfed_drills.config.NeoForgeConfigHelper;
import com.drunkencod.create_nerfed_drills.datagen.NeoForgeItemModelProvider;
import com.drunkencod.create_nerfed_drills.platform.Services;
import com.drunkencod.create_nerfed_drills.registry.NeoForgeRegistryHelper;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(Constants.MOD_ID)
public class MultiLoaderTemplateMod {

    public MultiLoaderTemplateMod(IEventBus eventBus, ModContainer modContainer) {
        // Wire DeferredRegisters
        ((NeoForgeRegistryHelper) Services.REGISTRY).initialize(eventBus);

        // Register configs
        ((NeoForgeConfigHelper) Services.CONFIG).register(modContainer);

        eventBus.addListener(this::onGatherData);

        MultiLoaderTemplate.init();
    }

    private void onGatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                event.includeClient(),
                new NeoForgeItemModelProvider(event.getGenerator().getPackOutput(), event.getExistingFileHelper()));
    }
}
