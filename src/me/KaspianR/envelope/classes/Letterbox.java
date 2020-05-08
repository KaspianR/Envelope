package me.KaspianR.envelope.classes;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;

import me.KaspianR.envelope.gui.LetterboxHolder;
import me.KaspianR.envelope.utils.CustomLogger;
import me.KaspianR.envelope.utils.Utils;

public class Letterbox implements ConfigurationSerializable{
	
	public Location position;
	public UUID player;
	public String id;
	public Inventory inventory;
	
	//Constructor with no predefined inventory
	public Letterbox(Location pos, UUID uuid, String id){
		
		this.position = pos;
		this.player = uuid;
		this.id = id;
		this.inventory = Bukkit.createInventory(new LetterboxHolder(pos), 18, "Letter box");
		
	}
	
	//Constructor with predefined inventory
	public Letterbox(Location pos, UUID uuid, String id, Inventory inventory){
		
		this.position = pos;
		this.player = uuid;
		this.id = id;
		this.inventory = inventory;
		
	}
	
	//Serialize object and return the map
	public Map<String, Object> serialize() {
		
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("position", position);
        result.put("player", player.toString());
        result.put("id", this.id);
        result.put("inventory", Utils.InventoryToBase64(inventory));
        return result;
		
	}
	
	//Deserialize object and return a full item
	public static Letterbox deserialize(Map<String, Object> data) throws IOException {
		
		Location pos;
        UUID uuid;
        Inventory inv;
        String id;
        
        if(data.containsKey("position")) {
        	
            pos = (Location) data.get("position");
            
            if(data.containsKey("player")) {
            	
            	uuid = UUID.fromString((String) data.get("player"));
                
                if(data.containsKey("id")) {
                	
                    id = (String) data.get("id");
                    
                    if(data.containsKey("inventory")) {
                    	
                        inv = Utils.InventoryFromBase64((String) data.get("inventory"));
                        
                        return new Letterbox(pos, uuid, id, inv);
                        
                    }
                    
                }
                
            }
            
        }
        
        CustomLogger.Warning("Could not deserialize letterbox: Missing keys");
        return null;
		
	}
	
}
