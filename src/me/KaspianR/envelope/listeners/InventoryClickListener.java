package me.KaspianR.envelope.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.gui.SealEnvelopeGUI;
import me.KaspianR.envelope.utils.NBTEditor;
import me.KaspianR.envelope.utils.Utils;

@SuppressWarnings("unused")
public class InventoryClickListener implements Listener {
	
	private Main Plugin;
	
	public InventoryClickListener(Main plugin) {
		
		this.Plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		
		String Title = event.getView().getTitle();
		
		if(Title.equals(SealEnvelopeGUI.EnvelopeInventoryName) || Title.equals(SealEnvelopeGUI.PackageInventoryName)) {
			
			int slot = event.getRawSlot();
			
			if(slot < 9 && (slot > 4 || (Title.equals(SealEnvelopeGUI.EnvelopeInventoryName) && slot > 1)) || NBTEditor.HasNBTString(event.getCurrentItem(), "Container")) {
				
				event.setCancelled(true);
				
				if(event.getCurrentItem() == null) {
					
					return;
					
				}
				else {
					
					SealEnvelopeGUI.Clicked((Player) event.getWhoClicked(), event.getSlot(), event.getCurrentItem(), event.getInventory());
					
				}
				
			}
			
		}
		
	}
	
}
