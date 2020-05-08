package me.KaspianR.envelope.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.classes.Letterbox;
import me.KaspianR.envelope.utils.ConfigManager;
import me.KaspianR.envelope.utils.Utils;

public class BlockBreakListener implements Listener {
	
	@SuppressWarnings("unused")
	private Main Plugin;
	
	public BlockBreakListener(Main plugin) {
		
		this.Plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void BlockBreak(BlockBreakEvent event) {
		
		if(event.getBlock().getBlockData().getMaterial().equals(Material.PLAYER_HEAD)) {
			
			List<Letterbox> letterboxes = (List<Letterbox>) ConfigManager.getCustomConfig().getList("letterBoxes");
	    	
	    	for(int n = 0; n < letterboxes.size(); n++) {
	    		
	    		if(event.getBlock().getLocation().equals(letterboxes.get(n).position)) {
	    			
	    			event.getPlayer().sendMessage(Utils.Format("&c&l[Envelope] &r&cYou can't break that!"));
	    			event.setCancelled(true);
	    			return;
	    			
	    		}
	    		
	    	}
			
		}
		
	}
	
}
