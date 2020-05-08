package me.KaspianR.envelope.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.KaspianR.envelope.classes.Letterbox;

public class ConfigManager {
	
	private static JavaPlugin Plugin;
	
	private static File customConfigFile;
    private static FileConfiguration customConfig;
    
    public static boolean save(List<Letterbox> letterBoxes) {
    	
    	try{
			ConfigManager.getCustomConfig().set("letterBoxes", letterBoxes);
			ConfigManager.saveCustomConfig();
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    	
    }
	
    public static FileConfiguration getCustomConfig() {
    	
        return customConfig;
        
    }
    
    public static void saveCustomConfig() {
    	
		try {
			customConfig.save(customConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    public static void createCustomConfig() {
    	
        customConfigFile = new File(Plugin.getDataFolder(), "saves.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            Plugin.saveResource("saves.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void setPlugin(JavaPlugin plugin) {
    	
    	Plugin = plugin;
    	
    }
	
}
