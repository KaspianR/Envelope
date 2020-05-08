package me.KaspianR.envelope.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class Utils {
	
	public static String Format(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static ItemStack SetItem(Inventory inv, ItemStack item, int slot) {
		
		inv.setItem(slot, item);
		
		return item;
		
	}
	
	public static ItemStack CreateItem(String materialType, int amount, String displayName, String... loreStrings) {
		
		ItemStack Item = new ItemStack(Material.getMaterial(materialType), amount);
		List<String> Lore = new ArrayList<String>();
		
		ItemMeta Meta = Item.getItemMeta();
		Meta.setDisplayName(Format(displayName));
		for(String s : loreStrings) {
			Lore.add(Format(s));
		}
		Meta.setLore(Lore);
		Item.setItemMeta(Meta);
		
		return Item;
		
	}
	
    public static ItemStack GetSkull(String url) {
    	
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        if(url.isEmpty())return item;
        
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try
        {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        item.setItemMeta(itemMeta);
        return item;
        
    }
    
    public static String InventoryToBase64(Inventory inventory) throws IllegalStateException {
    	
        try {
        	
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            
            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());
            
            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
            
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
            
        }
        catch (Exception e) {
        	
            throw new IllegalStateException("Unable to save item stacks.", e);
            
        }
        
    }
    
    public static Inventory InventoryFromBase64(String data) throws IOException {
    	
        try {
        	
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());
    
            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            
            dataInput.close();
            return inventory;
            
        }
        catch (ClassNotFoundException e) {
        	
            throw new IOException("Unable to decode content.", e);
            
        }
        
    }
    
    public static boolean InventoryIsEmpty(Inventory inv) {
    	
    	for(int n = 0; n < inv.getSize(); n++) {
    		
    		if(inv.getItem(n) != null) {
    			
    			return false;
    			
    		}
    		
    	}
    	
    	return true;
    	
    }
    
}
