package com.drunkencod.create_nerfed_drills.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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
