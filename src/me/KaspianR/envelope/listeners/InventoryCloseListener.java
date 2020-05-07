package me.KaspianR.envelope.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.gui.SealEnvelopeGUI;

public class InventoryCloseListener implements Listener {

	@SuppressWarnings("unused")
	private Main Plugin;
	
	public InventoryCloseListener(Main plugin) {
		
		this.Plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		
		if(event.getInventory().getClass().equals(SealEnvelopeGUI.Inv.getClass())) {
			
			SealEnvelopeGUI.Closed((Player) event.getPlayer(), event.getInventory());
			
		}
		
	}

}
