package com.test;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import java.io.InputStream;

public class Initlogs implements ModInitializer {
    public static final String MOD_ID = "custom";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        try {
            InputStream configStream = null;
            
            // try 1
            configStream = getClass().getClassLoader().getResourceAsStream("logs/log4j2.xml");
            
            // try 2
            if (configStream == null) {
                configStream = getClass().getResourceAsStream("/logs/log4j2.xml");
            }
            
            if (configStream != null) {
                configStream.close();
                
                // try 3
                LoggerContext context = (LoggerContext) LogManager.getContext(false);
                context.setConfigLocation(getClass().getResource("/logs/log4j2.xml").toURI());
                context.reconfigure();
                LOGGER.info("Custom log4j2.xml loaded from mod logs folder");
            } else {
                LOGGER.warn("[Custom logs] Could not find /logs/log4j2.xml in mod resources");
                LOGGER.warn("[Custom logs] Trying alternative paths...");
                
                //  try 4
                LOGGER.warn("[Custom logs] ClassLoader resource: " + 
                    getClass().getClassLoader().getResource("logs/log4j2.xml"));
                LOGGER.warn("[Custom logs] Class resource: " + 
                    getClass().getResource("/logs/log4j2.xml"));
                LOGGER.warn("[Custom logs] Fabric mod file: " + 
                    getClass().getProtectionDomain().getCodeSource().getLocation());
            }
        } catch (Exception e) {
            LOGGER.warn("[Custom logs] Failed to load log4j2.xml: " + e.getMessage());
            e.printStackTrace();
        }

        LOGGER.info("Loading Mod...");
        LOGGER.info("Mod Loaded successfully!");
    }
}
