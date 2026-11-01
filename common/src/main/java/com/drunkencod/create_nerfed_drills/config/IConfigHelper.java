package com.drunkencod.create_nerfed_drills.config;

/**
 * Cross-loader config service interface.
 * <p>
 * Add config entries here as interface methods, then implement them in
 * {@code NeoForgeConfigHelper} (using {@code ModConfigSpec}) and
 * {@code FabricConfigHelper} (using Cloth Config / AutoConfig).
 *
 * <p>
 * Config is loaded via
 * {@link com.drunkencod.create_nerfed_drills.platform.Services#CONFIG}.
 */
public interface IConfigHelper {

    /** Multiplier for Drill Contraption mining speed. */
    float getBreakingSpeedMultiplier();

    /** Multiplier for Drill Contraption mining drop chance. */
    float getBreakingDropChance();

    /**
     * Whether to only apply the multipliers to Mechanical Drills on contraptions,
     * or also to Mechanical Saws and other breaking blocks implementing
     * BlockBreakingMovementBehaviour.
     */
    boolean getOnlyNerfDrills();
}
