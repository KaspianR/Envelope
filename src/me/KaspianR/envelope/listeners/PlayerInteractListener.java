package me.KaspianR.envelope.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.gui.SealEnvelopeGUI;
import me.KaspianR.envelope.utils.NBTEditor;
import me.KaspianR.envelope.utils.Utils;

public class PlayerInteractListener implements Listener {
	
	@SuppressWarnings("unused")
	private Main Plugin;
	
	public PlayerInteractListener(Main plugin) {
		
		this.Plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerUse(PlayerInteractEvent event){
		
		try {
			
			Player player = event.getPlayer();
			
			ItemStack item = player.getInventory().getItemInMainHand();
		    
			if(NBTEditor.HasNBTString(item, "Container") && NBTEditor.GetNBTString(item, "Container").equals("Envelope") && !NBTEditor.HasNBTString(item, "Content")){
		    	
		    	player.openInventory(SealEnvelopeGUI.Show(player, 2, true));
		    	event.setCancelled(true);
		    	
		    }
		    else if(NBTEditor.HasNBTString(item, "Container") && NBTEditor.GetNBTString(item, "Container").equals("Package") && !NBTEditor.HasNBTString(item, "Content")){
		    	
		    	player.openInventory(SealEnvelopeGUI.Show(player, 5, false));
		    	event.setCancelled(true);
		    	
		    }
		    else if(NBTEditor.HasNBTString(item, "Container") && NBTEditor.GetNBTString(item, "Container").equals("Envelope") && NBTEditor.HasNBTString(item, "Content")) {
		    	
		    	Inventory inv = Utils.InventoryFromBase64(NBTEditor.GetNBTString(item, "Content"));
		    	
		    	//Loop through items and drop them
		    	for(int n = 0; n < 3; n++) {
					if(inv.getItem(n) != null) {
						player.getWorld().dropItem(player.getLocation(), inv.getItem(n));
					}
				}
		    	
		    	item.setAmount(item.getAmount() - 1);
		    	player.getInventory().setItemInMainHand(item.getAmount() < 1 ? null : item);
		    	
		    	event.setCancelled(true);
		    	
		    }
		    else if(NBTEditor.HasNBTString(item, "Container") && NBTEditor.GetNBTString(item, "Container").equals("Package") && NBTEditor.HasNBTString(item, "Content")) {
		    	
		    	Inventory inv = Utils.InventoryFromBase64(NBTEditor.GetNBTString(item, "Content"));
		    	
		    	//Loop through items and drop them
		    	for(int n = 0; n < 7; n++) {
					if(inv.getItem(n) != null) {
						player.getWorld().dropItem(player.getLocation(), inv.getItem(n));
					}
				}
		    	
		    	item.setAmount(item.getAmount() - 1);
		    	player.getInventory().setItemInMainHand(item.getAmount() < 1 ? null : item);
		    	
		    	event.setCancelled(true);
		    	
		    }
			
		}
		catch(Exception e) {
			
			System.out.println("Error: " + e);
			
		}
		
	}
	
}
