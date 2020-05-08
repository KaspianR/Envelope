package me.KaspianR.envelope.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.KaspianR.envelope.utils.NBTEditor;
import me.KaspianR.envelope.utils.Utils;

public class SealEnvelopeGUI {
	
	public static JavaPlugin Main;
	
	public static Inventory Inv;
	public static int InvBoxes = 9;
	
	public static ItemStack RedStainedGlass;
	
	public static String EnvelopeInventoryName;
	public static String PackageInventoryName;
	
	public static void Initialize(JavaPlugin main) {
		
		Main = main;
		
		EnvelopeInventoryName = Utils.Format("&lEnvelope");
		PackageInventoryName = Utils.Format("&lPackage");
		
		Inv = Bukkit.createInventory(new SealEnvelopeHolder(), InvBoxes, "Template");
		
		RedStainedGlass = Utils.CreateItem("RED_STAINED_GLASS_PANE", 1, Utils.Format("&4-"));
		
		Utils.SetItem(Inv, Utils.CreateItem("LIME_CONCRETE", 1, "&aSeal"), 7);
		Utils.SetItem(Inv, Utils.CreateItem("RED_CONCRETE", 1, "&4Cancel"), 8);
		
	}
	
	public static Inventory Show(int Size, boolean Envelope) {
		
		Inventory inventory = Bukkit.createInventory(new SealEnvelopeHolder(), InvBoxes, Envelope ? EnvelopeInventoryName : PackageInventoryName);
		
		inventory.setContents(Inv.getContents());
		
		for(int n = Size; n < 7; n++) {
			Utils.SetItem(inventory, RedStainedGlass, n);
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
						
						player.sendMessage(Utils.Format("&c&l[Envelope] &r&cThe envelope is empty!"));
						Main.getServer().getScheduler().runTask(Main, new Runnable() {public void run() {player.closeInventory();}});
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
						
						player.sendMessage(Utils.Format("&c&l[Envelope] &r&cThe package is empty!"));
						Main.getServer().getScheduler().runTask(Main, new Runnable() {public void run() {player.closeInventory();}});
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
				
				player.sendMessage(Utils.Format("&c&l[Envelope] &r&cYour not holding a container!"));
				
				Main.getServer().getScheduler().runTask(Main, new Runnable() {public void run() {player.closeInventory();}});
				return;
				
			}
			
			Main.getServer().getScheduler().runTask(Main, new Runnable() {public void run() {player.closeInventory();}});
			
		}
		else if(item.getItemMeta().getDisplayName().equals(Utils.Format("&4Cancel"))) {
			
			Main.getServer().getScheduler().runTask(Main, new Runnable() {public void run() {player.closeInventory();}});
			
		}
		
	}
	
	public static void Closed(Player player, Inventory inv) {
		
		for(int n = 0; n < 7; n++) {
			
			if(inv.getItem(n) != null) {
				
				//Check if it's a red stained glass pane in which case we can return because there will be no more objects
				if(inv.getItem(n).getType().equals(Material.RED_STAINED_GLASS_PANE)) {
					
					return;
					
				}
				
				player.getWorld().dropItem(player.getLocation(), inv.getItem(n));
				
			}
			
		}
		
	}

}
