package me.KaspianR.envelope.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.utils.NBTEditor;

public class CraftItemListener implements Listener {
	
	@SuppressWarnings("unused")
	private Main Plugin;
	
	public CraftItemListener(Main plugin) {
		
		this.Plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void craftItem(CraftItemEvent event) {

		CraftingInventory inv = event.getInventory();
		int count = 0;
		
		for(ItemStack item : inv.getStorageContents()) {
			
			if(NBTEditor.HasNBTString(item, "Container")) {
				
				count++;
				
			}
			
		}
		
		if(count > 1 || (!NBTEditor.HasNBTString(event.getRecipe().getResult(), "Container") && count > 0)) {
			
			event.setCancelled(true);
			
		}
	    
	}
	
}
