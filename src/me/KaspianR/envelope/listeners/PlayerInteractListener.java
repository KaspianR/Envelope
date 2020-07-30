package me.KaspianR.envelope.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.classes.Letterbox;
import me.KaspianR.envelope.gui.LetterboxGUI;
import me.KaspianR.envelope.gui.SealEnvelopeGUI;
import me.KaspianR.envelope.utils.ConfigManager;
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
	public void onPlayerUse(PlayerInteractEvent event) throws Exception {
		
		if(event.getHand().equals(EquipmentSlot.HAND)) {
			
			Block clickedBlock = event.getClickedBlock();
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			
			if(NBTEditor.HasNBTString(item, "Container") && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
				
				if(NBTEditor.HasNBTString(item, "Content")) {
					
					if(NBTEditor.GetNBTString(item, "Container").equals("Envelope") && player.isSneaking()) {
				    	
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
				    else if(NBTEditor.GetNBTString(item, "Container").equals("Package") && player.isSneaking()) {
				    	
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
				    else if(NBTEditor.GetNBTString(item, "Container").equals("Envelope") && !player.isSneaking() && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && clickedBlock.getBlockData().getMaterial().equals(Material.PLAYER_HEAD)) {
				    	
				    	@SuppressWarnings("unchecked")
						List<Letterbox> letterboxes = (List<Letterbox>) ConfigManager.getCustomConfig().getList("letterBoxes");
				    	
				    	for(int n = 0; n < letterboxes.size(); n++) {
				    		
				    		if(clickedBlock.getLocation().equals(letterboxes.get(n).position)) {
				    			
			    				Letterbox box = letterboxes.get(n);
			    				
			    				if(box.inventory.firstEmpty() != -1) {
			    					
			    					ItemStack container = player.getInventory().getItemInMainHand();
			    					
			    					player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
							    	player.getInventory().setItemInMainHand(item.getAmount() < 1 ? null : player.getInventory().getItemInMainHand());
			    					
							    	container.setAmount(1);
							    	
				    				Utils.SetItem(box.inventory, container, box.inventory.firstEmpty());
				    				letterboxes.set(n, box);
				    				
				    				ConfigManager.save(letterboxes);
			    					
			    				}
			    				else {
			    					
			    					player.sendMessage(Utils.Format("&c&l[Envelope] &r&cThe letterbox is full!"));
			    					
			    				}
			    				
				    			event.setCancelled(true);
				    			return;
				    			
				    		}
				    		
				    	}
				    	
				    }
					
				}
				else {
					
					if(NBTEditor.GetNBTString(item, "Container").equals("Envelope")){
				    	
				    	player.openInventory(SealEnvelopeGUI.Show(2, true));
				    	event.setCancelled(true);
				    	
				    }
				    else if(NBTEditor.GetNBTString(item, "Container").equals("Package")){
				    	
				    	player.openInventory(SealEnvelopeGUI.Show(5, false));
				    	event.setCancelled(true);
				    	
				    }
					
				}
			
			}
			else if(!player.isSneaking() && event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && clickedBlock.getBlockData().getMaterial().equals(Material.PLAYER_HEAD)) {
		    	
		    	@SuppressWarnings("unchecked")
				List<Letterbox> letterboxes = (List<Letterbox>) ConfigManager.getCustomConfig().getList("letterBoxes");
		    	
		    	for(int n = 0; n < letterboxes.size(); n++) {
		    		
		    		if(clickedBlock.getLocation().equals(letterboxes.get(n).position)) {
		    			
		    			if(letterboxes.get(n).player.equals(player.getUniqueId()) || player.hasPermission("envelope.open.all")) {
			    			
			    			player.openInventory(LetterboxGUI.Show(2, letterboxes.get(n).inventory, letterboxes.get(n).position));
			    			
		    			}
		    			else {
		    				
	    					player.sendMessage(Utils.Format("&c&l[Envelope] &r&cHey! That's not yours! (If you want to post something you need to hold that item)"));
		    				
		    			}
		    			
		    			event.setCancelled(true);
		    			return;
		    			
		    		}
		    		
		    	}
		    	
			}
			
		}
		
	}
	
}
