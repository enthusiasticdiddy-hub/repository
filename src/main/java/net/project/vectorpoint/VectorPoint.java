package net.project.vectorpoint;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class VectorPoint implements ClientModInitializer {
    private static KeyBinding guiKey;
    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        // 1. Register Keybinds
        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open Sentinel GUI", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "VectorPoint"));
        
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Toggle Aim Assist", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_SEMICOLON, "VectorPoint"));

        // 2. Register the Tick Event (The Loop)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Handle Toggles
            while (toggleKey.wasPressed()) {
                AimModule.enabled = !AimModule.enabled;
            }
            while (guiKey.wasPressed()) {
                client.setScreen(new SentinelScreen());
            }

            // Run Logic
            AimModule.tick(client);
        });
    }
}