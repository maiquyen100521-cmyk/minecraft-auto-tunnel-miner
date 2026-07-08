package com.autotunnel;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoTunnelMod implements ModInitializer {
    public static final String MOD_ID = "autotunnelminer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Auto Tunnel Miner initialized!");
    }
}
