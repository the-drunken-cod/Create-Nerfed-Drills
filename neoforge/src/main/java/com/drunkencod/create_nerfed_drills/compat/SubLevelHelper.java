package com.drunkencod.create_nerfed_drills.compat;

import dev.ryanhcode.sable.companion.SableCompanion;

import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Thin wrapper around Sable Companion (bundled via jar-in-jar). When Sable /
 * Create: Aeronautics isn't installed, the companion's default implementation
 * is used, which never reports a sublevel.
 */
public final class SubLevelHelper {

    private SubLevelHelper() {
    }

    /** Whether the block entity is located inside a Sable sublevel's plot. */
    public static boolean isInSubLevel(BlockEntity blockEntity) {
        if (blockEntity.getLevel() == null)
            return false;
        return SableCompanion.INSTANCE.getContaining(blockEntity) != null;
    }
}
