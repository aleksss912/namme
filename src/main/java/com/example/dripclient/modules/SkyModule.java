package com.example.dripclient.modules;

import com.example.dripclient.Category;
import com.example.dripclient.Module;
import com.example.dripclient.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

/**
 * Wyglad nieba (tylko u Ciebie): stala pora dnia (noc z ksiezycem, zachod, wschod, dzien),
 * faza ksiezyca i opcjonalnie wlasny kolor nieba.
 */
public class SkyModule extends Module {
    /** Kolor nieba (RGB) czytany przez mixin, -1 = nie nadpisuj. */
    public static volatile int colorRgb = -1;

    private final Setting.Num preset = add(new Setting.Num("Preset (0=własna)", 3, 0, 4, 1)); // 1 dzien, 2 zachod, 3 noc, 4 wschod
    private final Setting.Num time = add(new Setting.Num("Własna pora dnia", 18000, 0, 24000, 500));
    private final Setting.Num phase = add(new Setting.Num("Faza księżyca", 0, 0, 7, 1)); // 0 = pelnia
    private final Setting.Bool customColor = add(new Setting.Bool("Własny kolor nieba", false));
    private final Setting.Num hue = add(new Setting.Num("Odcień", 0.75, 0.0, 1.0, 0.05));
    private final Setting.Num sat = add(new Setting.Num("Nasycenie", 0.7, 0.0, 1.0, 0.05));
    private final Setting.Num val = add(new Setting.Num("Jasność", 0.55, 0.0, 1.0, 0.05));

    public SkyModule() {
        super("Niebo", Category.RENDER);
    }

    private static final int[] PRESET_TIMES = {0, 6000, 12500, 18000, 23000};

    private void apply(MinecraftClient mc) {
        if (mc.world == null) return;
        int pr = (int) preset.value;
        long t = pr > 0 ? PRESET_TIMES[pr] : (long) time.value;
        // doba zachowuje numer dnia z fazy ksiezyca, wiec ksiezyc wyglada tak, jak ustawiono
        mc.world.setTimeOfDay((long) phase.value * 24000L + t);
        colorRgb = customColor.value
                ? MathHelper.hsvToRgb((float) hue.value, (float) sat.value, (float) val.value) & 0xFFFFFF
                : -1;
    }

    @Override
    public void onTick(MinecraftClient mc) {
        apply(mc);
    }

    @Override
    public void onFrame(MinecraftClient mc, float delta) {
        apply(mc);
    }

    @Override
    public void onDisable() {
        colorRgb = -1; // czas wroci do normalnego przy najblizszej aktualizacji z serwera
    }

    @Override
    public void onLoad() {
        colorRgb = -1;
    }
}
