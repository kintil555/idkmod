package com.example.texturepaint.mixin.client;

import com.example.texturepaint.screen.TextureSelectorScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Mouse;

@Mixin(Mouse.class)
public abstract class MouseScrollMixin {

    @Shadow
    private MinecraftClient client;

    @Inject(method = "method_22684", at = @At("HEAD"), cancellable = true, remap = false)
    private void onMouseScroll(long window, double horizontal, double vertical, boolean discrete, CallbackInfo ci) {
        if (client.player == null || client.currentScreen != null) return;
        if (!client.player.isSneaking()) return;
        if (vertical == 0) return;

        HitResult target = client.crosshairTarget;
        if (!(target instanceof BlockHitResult blockHit)) return;
        if (blockHit.getType() != HitResult.Type.BLOCK) return;

        BlockPos pos = blockHit.getBlockPos();
        ci.cancel();
        client.execute(() -> client.setScreen(new TextureSelectorScreen(pos)));
    }
}
