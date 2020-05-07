package me.KaspianR.envelope.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LetterBox {
	
	public Location position;
	public Player player;
	public Inventory inventory;
	
	public LetterBox(Location pos, Player player){
		
		this.position = pos;
		this.player = player;
		this.inventory = Bukkit.createInventory(player, 18, "Letter box");
		
	}
	
}
