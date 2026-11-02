package com.drunkencod.create_nerfed_drills;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class CreateNerfedDrillsMod {

    public CreateNerfedDrillsMod(IEventBus eventBus, ModContainer modContainer) {
        CreateNerfedDrills.init();
    }
}
