package me.KaspianR.envelope.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.classes.Letterbox;
import me.KaspianR.envelope.utils.ConfigManager;
import me.KaspianR.envelope.utils.Utils;

public class LetterboxGUI {
	
	public static JavaPlugin MainPlugin;
	
	public static int InvBoxes = 36;
	
	public static ItemStack RedStainedGlass;
	
	public static String InventoryName;
	
	public static void Initialize(JavaPlugin main) {
		
		MainPlugin = main;
		
		InventoryName = Utils.Format("&lLetterbox");
		
		RedStainedGlass = Utils.CreateItem("RED_STAINED_GLASS_PANE", 1, Utils.Format("&4-"));
		
	}
	
	public static Inventory Show(int Size, Inventory inv, Location pos) {
		
		Inventory inventory = Bukkit.createInventory(new LetterboxHolder(pos), InvBoxes, InventoryName);
		
		inventory.setContents(inv.getContents());
		
		for(int n = 18; n < 36; n++) {
			Utils.SetItem(inventory, RedStainedGlass, n);
		}
		
		Utils.SetItem(inventory, Utils.CreateItem("RED_CONCRETE", 1, "&4Delete"), 31);
		
		return inventory;
		
	}
	
	public static void Clicked(Player player, int slot, ItemStack item, Inventory inv) {
		
		if(item.getItemMeta().getDisplayName().equals(Utils.Format("&4Delete"))) {
			
			@SuppressWarnings("unchecked")
			List<Letterbox> letterboxes = (List<Letterbox>) ConfigManager.getCustomConfig().getList("letterBoxes");
			
			for(int n = 0; n < letterboxes.size(); n++) {
				
				if(letterboxes.get(n).position.equals(((LetterboxHolder)inv.getHolder()).position)) {
					
					for(int m = 0; m < inv.getSize(); m++) {
						
						if(inv.getItem(m) != null) {
							
							//Check if it's a red stained glass pane in which case we can break because there will be no more objects
							if(inv.getItem(m).getType().equals(Material.RED_STAINED_GLASS_PANE)) {
								
								break;
								
							}
							
							player.getWorld().dropItem(letterboxes.get(n).position, inv.getItem(m));
							
						}
						
					}
					
					//Find the letterbox with the matching ID and spawn it so that the player gets the letterbox back and can use it again
					for(int m = 0; m < Main.LetterBoxTypes.length; m++) {
						
						if(letterboxes.get(n).id.equals(Main.LetterBoxTypes[m].id)) {
							
							player.getWorld().dropItem(letterboxes.get(n).position, Main.LetterBoxTypes[m].Letterbox);
							break;
							
						}
						
					}
					
					letterboxes.get(n).position.getBlock().setType(Material.AIR);
					
					letterboxes.remove(n);
					ConfigManager.save(letterboxes);
					
					MainPlugin.getServer().getScheduler().runTask(MainPlugin, new Runnable() {public void run() {player.closeInventory();}});
					
				}
				
			}
			
		}
		
	}
	
	public static void Closed(Player player, Inventory inv, Location pos) {
		
		@SuppressWarnings("unchecked")
		List<Letterbox> letterboxes = (List<Letterbox>) ConfigManager.getCustomConfig().getList("letterBoxes");
		
		for(int n = 0; n < letterboxes.size(); n++) {
			
			if(letterboxes.get(n).position.equals(pos)) {
				
				Letterbox box = letterboxes.get(n);
				
				box.inventory = inv;
				
				letterboxes.set(n, box);
				
				ConfigManager.save(letterboxes);
				
			}
			
		}
		
	}

}
