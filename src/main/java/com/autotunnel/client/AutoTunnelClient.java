package com.autotunnel.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.autotunnel.AutoTunnelMod;
import com.autotunnel.tunnel.TunnelManager;

@Environment(EnvType.CLIENT)
public class AutoTunnelClient implements ClientModInitializer {
    public static KeyBinding toggleKey;
    public static TunnelManager tunnelManager;
    public static boolean isActive = false;

    @Override
    public void onInitializeClient() {
        // Register keybinding
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.autotunnelminer.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "category.autotunnelminer"
        ));

        tunnelManager = new TunnelManager();

        // Client tick event
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (toggleKey.wasPressed()) {
                isActive = !isActive;
                if (isActive) {
                    AutoTunnelMod.LOGGER.info("Auto Tunnel Miner: ACTIVATED");
                    tunnelManager.startMining(client.player);
                } else {
                    AutoTunnelMod.LOGGER.info("Auto Tunnel Miner: DEACTIVATED");
                    tunnelManager.stopMining();
                }
            }

            if (isActive && tunnelManager != null) {
                tunnelManager.tick(client.player);
            }
        });
    }
}
