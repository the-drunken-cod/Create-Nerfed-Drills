package com.drunkencod.create_nerfed_drills.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.drunkencod.create_nerfed_drills.config.NeoForgeConfigHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import com.simibubi.create.content.kinetics.drill.DrillMovementBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(BlockBreakingMovementBehaviour.class)
public abstract class BlockBreakingMovementBehaviourMixin {

    // #region block breaking speed
    @ModifyReturnValue(method = "getBlockBreakingSpeed", at = @At("TAIL"))
    protected float createNerfedDrills$getBlockBreakingSpeed(float val, MovementContext context) {
        if (NeoForgeConfigHelper.SERVER.onlyNerfDrills.get() && !((Object) this instanceof DrillMovementBehaviour))
            return val;
        return val * (float) NeoForgeConfigHelper.SERVER.getSpeedMultiplier(context.world);
    }

    // #region zero speed handling
    /**
     * Create calculates {@code ticksUntilNextProgress = (int) (hardness / speed)}.
     * With a speed of 0 that is {@code Integer.MAX_VALUE}, which is saved in the
     * contraption's NBT and basically never gets hit, so drills get frozen
     * forever. Instead, while the effective speed is 0, pause the breaker
     * (keeping its state), and repair any already poisoned countdowns.
     */
    @Inject(method = "tickBreaker", at = @At("HEAD"), cancellable = true)
    private void createNerfedDrills$pauseAtZeroSpeed(MovementContext context, CallbackInfo ci) {
        if (context.world.isClientSide)
            return;
        if (NeoForgeConfigHelper.SERVER.onlyNerfDrills.get() && !((Object) this instanceof DrillMovementBehaviour))
            return;
        if (NeoForgeConfigHelper.SERVER.getSpeedMultiplier(context.world) <= 0.0D) {
            ci.cancel();
            return;
        }
        // repair a countdown poisoned by an earlier zero multiplier:
        if (context.data.getInt("TicksUntilNextProgress") > (1 << 30))
            context.data.putInt("TicksUntilNextProgress", 0);
    }

    // #region block drop chance
    @WrapOperation(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/BlockHelper;destroyBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;FLjava/util/function/Consumer;)V"))
    private void createNerfedDrills$filterDrillDrops(Level level, BlockPos pos, float effectChance,
            Consumer<ItemStack> original, Operation<Void> operation, MovementContext context, BlockPos breakingPos) {
        if (NeoForgeConfigHelper.SERVER.onlyNerfDrills.get() && !((Object) this instanceof DrillMovementBehaviour)) {
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
