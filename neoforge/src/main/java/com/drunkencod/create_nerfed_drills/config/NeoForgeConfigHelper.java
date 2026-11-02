package com.drunkencod.create_nerfed_drills.config;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NeoForgeConfigHelper {

    // -------------------------------------------------------------------------
    // Server config
    // -------------------------------------------------------------------------

    public static final ServerConfig SERVER;
    private static final ModConfigSpec SERVER_SPEC;

    static {
        Pair<ServerConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder()
                .configure(ServerConfig::new);
        SERVER = specPair.getLeft();
        SERVER_SPEC = specPair.getRight();
    }

    // -------------------------------------------------------------------------
    // Registration — called from MultiLoaderTemplateMod constructor
    // -------------------------------------------------------------------------

    /**
     * Must be called in the NeoForge mod constructor with the injected
     * {@link ModContainer}
     * so that configs are registered before the world loads.
     */
    public static void register(ModContainer modContainer) {
        // modContainer.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
        // modContainer.registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
    }

    // -------------------------------------------------------------------------
    // Inner config classes
    // -------------------------------------------------------------------------

    public static class ServerConfig {
        public final ModConfigSpec.DoubleValue breakingSpeedMultiplier;
        public final ModConfigSpec.DoubleValue breakingDropChance;
        public final ModConfigSpec.BooleanValue onlyNerfDrills;
        public final ModConfigSpec.ConfigValue<List<? extends String>> dimensionSpeedMultipliers;
        public final ModConfigSpec.ConfigValue<List<? extends String>> dimensionDropChances;

        ServerConfig(ModConfigSpec.Builder builder) {
            breakingSpeedMultiplier = builder
                    .comment(
                            "Multiplier for Drill Contraption breaking speed. A value of 0.0 will make breaking impossible.")
                    .comment(
                            "Note: as the breaking speed is based on calculating the next tick at which to progress mining, setting this to a low value and then back to a high value can take a lot of time to apply for contraptions that are currently actively mining, or even require a complete server restart when it was set to 0.0 before.")
                    .defineInRange("breakingSpeedMultiplier", 1.0D, 0.0D, Double.MAX_VALUE);

            breakingDropChance = builder
                    .comment(
                            "Multiplier for Drill Contraption breaking drop chance. A value of 0.0 will make breaking drop nothing.")
                    .defineInRange("breakingDropChance", 1.0D, 0.0D, Double.MAX_VALUE);

            onlyNerfDrills = builder
                    .comment(
                            "Whether to only apply the multipliers to Mechanical Drills on contraptions, or also to Mechanical Saws and other breaking blocks from addon mods that are implementing Create's BlockBreakingMovementBehaviour.")
                    .define("onlyNerfDrills", true);

            String dimFormatLine = "Format: \"<dimension_id>=<multiplier>\", e.g. \"minecraft:the_nether=0.5\". Spaces are ignored.";

            dimensionSpeedMultipliers = builder
                    .comment("Per-dimension overrides for breakingSpeedMultiplier.", dimFormatLine)
                    .defineListAllowEmpty("dimensionSpeedMultipliers", List::of,
                            () -> "minecraft:the_nether=1.0", ServerConfig::isValidEntry);

            dimensionDropChances = builder
                    .comment("Per-dimension overrides for breakingDropChance.", dimFormatLine)
                    .defineListAllowEmpty("dimensionDropChances", List::of,
                            () -> "minecraft:the_nether=1.0", ServerConfig::isValidEntry);
        }

        /**
         * Speed multiplier for the given level's dimension (falls back to the global
         * value).
         */
        public double getSpeedMultiplier(Level level) {
            return lookup(dimensionSpeedMultipliers.get(), level, breakingSpeedMultiplier.getAsDouble());
        }

        /**
         * Drop chance for the given level's dimension (falls back to the global value).
         */
        public double getDropChance(Level level) {
            return lookup(dimensionDropChances.get(), level, breakingDropChance.getAsDouble());
        }

        /** Resolves and parses a dimension-bound config entry. */
        private static double lookup(List<? extends String> entries, Level level, double fallback) {
            String dim = level.dimension().location().toString();
            for (String entry : entries) {
                int eq = entry.indexOf('=');
                if (entry.substring(0, eq).trim().equals(dim))
                    return Double.parseDouble(entry.substring(eq + 1).trim());
            }
            return fallback;
        }

        private static boolean isValidEntry(Object o) {
            if (!(o instanceof String s))
                return false;
            int eq = s.indexOf('=');
            if (eq <= 0 || ResourceLocation.tryParse(s.substring(0, eq).trim()) == null)
                return false;
            try {
                return Double.parseDouble(s.substring(eq + 1).trim()) >= 0.0D;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
