package com.drunkencod.create_nerfed_drills.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.drunkencod.create_nerfed_drills.compat.SubLevelHelper;
import com.drunkencod.create_nerfed_drills.config.NeoForgeConfigHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.content.kinetics.drill.DrillBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Nerfs the stationary (block entity) breakers, i.e. drills that are placed
 * directly on a Create: Aeronautics / Sable sublevel rather than being part of
 * a Create contraption. These are only affected while they're inside a
 * sublevel; drills in the regular world are left untouched.
 */
@Mixin(BlockBreakingKineticBlockEntity.class)
public abstract class BlockBreakingKineticBlockEntityMixin {

    @Shadow
    protected int ticksUntilNextProgress;

    /** Whether the multipliers should currently apply to this block entity. */
    @Unique
    private boolean createNerfedDrills$shouldNerf() {
        if (!NeoForgeConfigHelper.SERVER.nerfSubLevelDrills.get())
            return false;
        if (NeoForgeConfigHelper.SERVER.onlyNerfDrills.get() && !((Object) this instanceof DrillBlockEntity))
            return false;
        return SubLevelHelper.isInSubLevel((BlockEntity) (Object) this);
    }

    @Unique
    private Level createNerfedDrills$level() {
        return ((BlockEntity) (Object) this).getLevel();
    }

    // #region block breaking speed
    @ModifyReturnValue(method = "getBreakSpeed", at = @At("TAIL"))
    private float createNerfedDrills$getBreakSpeed(float val) {
        Level level = createNerfedDrills$level();
        if (level == null || !createNerfedDrills$shouldNerf())
            return val;
        return val * (float) NeoForgeConfigHelper.SERVER.getSpeedMultiplier(level);
    }

    // #region zero speed handling
    /**
     * Same problem as with contraptions: with an effective speed of 0, Create
     * computes {@code ticksUntilNextProgress = (int) (hardness / 0)}, which is
     * {@code Integer.MAX_VALUE} and effectively freezes the drill forever. So
     * while the multiplier is 0 the countdown is just held at 1 (the drill
     * keeps its progress, but never advances), and any already poisoned
     * countdown is repaired.
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void createNerfedDrills$pauseAtZeroSpeed(CallbackInfo ci) {
        Level level = createNerfedDrills$level();
        if (level == null || level.isClientSide || !createNerfedDrills$shouldNerf())
            return;
        if (NeoForgeConfigHelper.SERVER.getSpeedMultiplier(level) <= 0.0D) {
            if (ticksUntilNextProgress >= 0)
                ticksUntilNextProgress = 1;
        } else if (ticksUntilNextProgress > (1 << 30)) {
            ticksUntilNextProgress = 0;
        }
    }

    // #region block drop chance
    @WrapOperation(method = "onBlockBroken", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/BlockHelper;destroyBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;FLjava/util/function/Consumer;)V"))
    private void createNerfedDrills$filterDrops(Level level, BlockPos pos, float effectChance,
            Consumer<ItemStack> original, Operation<Void> operation, BlockState stateToBreak) {
        if (!createNerfedDrills$shouldNerf()) {
            operation.call(level, pos, effectChance, original);
            return;
        }
        Consumer<ItemStack> filtered = stack -> {
            if (level.random.nextDouble() < NeoForgeConfigHelper.SERVER.getDropChance(level))
                original.accept(stack);
        };
        operation.call(level, pos, effectChance, filtered);
    }
}
