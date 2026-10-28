package com.example.dripclient.mixin;

import com.example.dripclient.modules.HitboxModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    /** Hitbox: powiekszone boxy sa ustawiane tuz przed wyznaczeniem celu pod celownikiem. */
    @Inject(method = "updateCrosshairTarget", at = @At("HEAD"))
    private void dripclient$hitboxBeforePick(float tickDelta, CallbackInfo ci) {
        HitboxModule.applyNow(MinecraftClient.getInstance());
    }
}
