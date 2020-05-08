package me.KaspianR.envelope.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.gui.LetterboxGUI;
import me.KaspianR.envelope.gui.LetterboxHolder;
import me.KaspianR.envelope.gui.SealEnvelopeGUI;
import me.KaspianR.envelope.gui.SealEnvelopeHolder;

public class InventoryCloseListener implements Listener {

	@SuppressWarnings("unused")
	private Main Plugin;
	
	public InventoryCloseListener(Main plugin) {
		
		this.Plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		
		//Check if it is a custom inventory and execute "close" function
		if(event.getInventory().getHolder() instanceof SealEnvelopeHolder) {
			
			SealEnvelopeGUI.Closed((Player) event.getPlayer(), event.getInventory());
			
		}
		else if(event.getInventory().getHolder() instanceof LetterboxHolder){
			
			LetterboxGUI.Closed((Player) event.getPlayer(), event.getInventory(), ((LetterboxHolder) event.getInventory().getHolder()).position);
			
		}
		
	}

}
