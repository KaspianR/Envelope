package me.KaspianR.envelope.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.KaspianR.envelope.utils.NBTEditor;
import me.KaspianR.envelope.utils.Utils;

public class SealEnvelopeGUI {
	
	public static Inventory Inv;
	public static int InvBoxes = 9;
	
	public static ItemStack RedStainedGlass;
	
	public static String EnvelopeInventoryName;
	public static String PackageInventoryName;
	
	public static void Initialize() {
		
		EnvelopeInventoryName = Utils.Format("Envelope");
		PackageInventoryName = Utils.Format("Package");
		
		Inv = Bukkit.createInventory(null, InvBoxes, "Template");
		
		RedStainedGlass = Utils.CreateItem("RED_STAINED_GLASS_PANE", 1, Utils.Format("&4-"));
		
		Utils.AddItem(Inv, Utils.CreateItem("LIME_CONCRETE", 1, "&aSeal"), 7);
		Utils.AddItem(Inv, Utils.CreateItem("RED_CONCRETE", 1, "&4Cancel"), 8);
		
	}
	
	public static Inventory Show(Player player, int Size, boolean Envelope) {
		
		Inventory inventory = Bukkit.createInventory(player, InvBoxes, Envelope ? EnvelopeInventoryName : PackageInventoryName);
		
		inventory.setContents(Inv.getContents());
		
		for(int n = Size; n < 7; n++) {
			Utils.AddItem(inventory, RedStainedGlass, n);
		}
		
		return inventory;
		
	}
	
	public static void Clicked(Player player, int slot, ItemStack item, Inventory inv) {
		
		ItemStack container = player.getInventory().getItemInMainHand();
		
		if(item.getItemMeta().getDisplayName().equals(Utils.Format("&aSeal"))) {
			
			if(NBTEditor.HasNBTString(container, "Container")) {
				
				if(NBTEditor.GetNBTString(container, "Container").equals("Envelope")) {
					
					player.getInventory().setItemInMainHand(NBTEditor.AddNBTString(container, "Owner", player.getUniqueId().toString()));
					
					//Remove items not supposed to be stored!
					Inventory inventory = inv;
					for(int n = 2; n < 9; n++) {
						inventory.setItem(n, null);
					}
					
					if(Utils.InventoryIsEmpty(inv)) {
						
						player.sendMessage(Utils.Format("&cThe envelope is empty!"));
						player.closeInventory();
						return;
						
					}
					else {
						
						//Give the new item
						ItemStack stack = container;
						stack = NBTEditor.AddNBTString(stack, "Content", Utils.InventoryToBase64(inventory));
						stack.setAmount(1);
						int firstEmpty = player.getInventory().firstEmpty();
						if(firstEmpty == -1) {
							player.getWorld().dropItem(player.getLocation(), stack);
						}
						else {
							player.getInventory().setItem(firstEmpty, stack);
						}
						
						//Remove one item
						container.setAmount(container.getAmount() - 1);
				    	player.getInventory().setItemInMainHand(item.getAmount() < 1 ? null : container);
				    	
						//Clear items from inventory to prevent dropping
						for(int n = 0; n < 2; n++) {
							inv.setItem(n, null);
						}
						
					}
					
				}
				else if(NBTEditor.GetNBTString(container, "Container").equals("Package")) {
					
					player.getInventory().setItemInMainHand(NBTEditor.AddNBTString(container, "Owner", player.getUniqueId().toString()));
					
					//Remove items not supposed to be stored!
					Inventory inventory = inv;
					for(int n = 5; n < 9; n++) {
						inventory.setItem(n, null);
					}
					
					if(Utils.InventoryIsEmpty(inv)) {
						
						player.sendMessage(Utils.Format("&cThe package is empty!"));
						player.closeInventory();
						return;
						
					}
					else {
						
						//Give the new item
						ItemStack stack = container;
						stack = NBTEditor.AddNBTString(stack, "Content", Utils.InventoryToBase64(inventory));
						stack.setAmount(1);
						int firstEmpty = player.getInventory().firstEmpty();
						if(firstEmpty == -1) {
							player.getWorld().dropItem(player.getLocation(), stack);
						}
						else {
							player.getInventory().setItem(firstEmpty, stack);
						}
						
						//Remove one item
						container.setAmount(container.getAmount() - 1);
				    	player.getInventory().setItemInMainHand(item.getAmount() < 1 ? null : container);
						
						//Clear items from inventory to prevent dropping
						for(int n = 0; n < 5; n++) {
							inv.setItem(n, null);
						}
						
					}
					
				}
				
			}
			else {
				
				player.sendMessage(Utils.Format("&c&lERROR! &r&cNOT HOLDING CONTAINER!"));
				player.closeInventory();
				return;
				
			}
			
			player.closeInventory();
			
		}
		else if(item.getItemMeta().getDisplayName().equals(Utils.Format("&4Cancel"))) {
			
			player.closeInventory();
			
		}
		
	}
	
	public static void Closed(Player player, Inventory inv) {
		
		for(int n = 0; n < 7; n++) {
			
			if(inv.getItem(n) != null) {
				
				if(inv.getItem(n).getType().equals(Material.RED_STAINED_GLASS_PANE)) {
					
					return;
					
				}
				
				if(inv.getItem(n) != null) {
					
					player.getWorld().dropItem(player.getLocation(), inv.getItem(n));
					
				}
				
			}
			
		}
		
	}

}
