package com.drunkencod.create_nerfed_drills;

import com.drunkencod.create_nerfed_drills.config.NeoForgeConfigHelper;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class CreateNerfedDrillsMod {

    public CreateNerfedDrillsMod(IEventBus eventBus, ModContainer modContainer) {
        NeoForgeConfigHelper.register(modContainer);

        CreateNerfedDrills.init();
    }
}
