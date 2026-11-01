package com.drunkencod.create_nerfed_drills;

import com.drunkencod.create_nerfed_drills.config.NeoForgeConfigHelper;
import com.drunkencod.create_nerfed_drills.platform.Services;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class CreateNerfedDrillsMod {

    public CreateNerfedDrillsMod(IEventBus eventBus, ModContainer modContainer) {
        // Register configs
        ((NeoForgeConfigHelper) Services.CONFIG).register(modContainer);

        CreateNerfedDrills.init();
    }
}
