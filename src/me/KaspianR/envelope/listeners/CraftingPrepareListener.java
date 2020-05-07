package me.KaspianR.envelope.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.utils.NBTEditor;

public class CraftingPrepareListener implements Listener {
	
	@SuppressWarnings("unused")
	private Main Plugin;
	
	public CraftingPrepareListener(Main plugin) {
		
		this.Plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void prepareItemCrafting(PrepareItemCraftEvent event) {
		
		if(event.getRecipe() == null) {
			
			return;
			
		}
		
		CraftingInventory inv = event.getInventory();
		int count = 0;
		
		for(ItemStack stack : inv.getStorageContents()) {
			
			if(NBTEditor.HasNBTString(stack, "Container")) {
				
				count++;
				
			}
			
		}
		
		if(count > 1 || (!NBTEditor.HasNBTString(event.getRecipe().getResult(), "Container") && count > 0)) {
			
			inv.setResult(null);
			
		}
		
	}
	
}
