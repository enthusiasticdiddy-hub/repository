package net.project.vectorpoint;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class SentinelScreen extends Screen {
    public SentinelScreen() {
        super(Text.literal("VectorPoint Settings"));
    }

    @Override
    protected void init() {
        int x = width / 2 - 100;

        // Toggle Status
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Status: " + (AimModule.enabled ? "ON" : "OFF")), b -> {
            AimModule.enabled = !AimModule.enabled;
            b.setMessage(Text.literal("Status: " + (AimModule.enabled ? "ON" : "OFF")));
        }).dimensions(x, 40, 200, 20).build());

        // Target: Head vs Torso
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Target: " + (AimModule.targetHead ? "Head" : "Torso")), b -> {
            AimModule.targetHead = !AimModule.targetHead;
            b.setMessage(Text.literal("Target: " + (AimModule.targetHead ? "Head" : "Torso")));
        }).dimensions(x, 70, 200, 20).build());

        // Strength (Cycle through 0.1 to 1.0)
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Strength: " + AimModule.strength), b -> {
            AimModule.strength = (AimModule.strength >= 0.9f) ? 0.1f : AimModule.strength + 0.2f;
            b.setMessage(Text.literal("Strength: " + String.format("%.1f", AimModule.strength)));
        }).dimensions(x, 100, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), b -> close()).dimensions(x, 140, 200, 20).build());
    }

    @Override
    public boolean shouldPause() { return false; }
}