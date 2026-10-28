package com.example.dripclient.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    /** Prawdziwy FOV uzywany przez gre (z uwzglednieniem sprintu, efektow, wody). */
    @Invoker("getFov")
    float dripclient$getFov(Camera camera, float tickDelta, boolean changingFov);
}
