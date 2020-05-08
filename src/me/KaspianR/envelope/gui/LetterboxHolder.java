package me.KaspianR.envelope.gui;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class LetterboxHolder implements InventoryHolder {
	
	public Location position;
	
	public LetterboxHolder(Location pos){
		
		this.position = pos;
		
	}
	
	@Override
	public Inventory getInventory() {
		return null;
	}
	
}
