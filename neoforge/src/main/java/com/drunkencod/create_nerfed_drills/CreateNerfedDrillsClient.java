package com.drunkencod.create_nerfed_drills;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * Client-only entrypoint. Registers NeoForge's auto-generated config screen so
 * the "Config" button in Mods > Create Nerfed Drills becomes available.
 */
@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class CreateNerfedDrillsClient {

    public CreateNerfedDrillsClient(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
