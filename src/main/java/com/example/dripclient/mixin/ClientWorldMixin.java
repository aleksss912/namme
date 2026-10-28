package com.example.dripclient.mixin;

import com.example.dripclient.modules.SkyModule;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    /**
     * Wlasny kolor nieba. Zwracany typ (int albo Vec3d) zalezy od wersji gry,
     * wiec podmieniamy wartosc zgodnie z tym, co oryginal faktycznie zwrocil.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
    private void dripclient$skyColor(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable cir) {
        int rgb = SkyModule.colorRgb;
        if (rgb < 0) return;
        Object orig = cir.getReturnValue();
        if (orig instanceof Integer i) {
            cir.setReturnValue((i & 0xFF000000) | (rgb & 0xFFFFFF));
        } else if (orig instanceof Vec3d) {
            cir.setReturnValue(new Vec3d(((rgb >> 16) & 255) / 255.0, ((rgb >> 8) & 255) / 255.0, (rgb & 255) / 255.0));
        }
    }
}
