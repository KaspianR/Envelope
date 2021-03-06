package me.KaspianR.envelope.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.classes.Letterbox;
import me.KaspianR.envelope.utils.ConfigManager;
import me.KaspianR.envelope.utils.NBTEditor;
import me.KaspianR.envelope.utils.Utils;

public class BlockPlaceListener implements Listener {
	
	@SuppressWarnings("unused")
	private Main Plugin;
	
	public BlockPlaceListener(Main plugin) {
		
		this.Plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void BlockPlace(BlockPlaceEvent event) {
		
		if(NBTEditor.HasNBTString(event.getItemInHand(), "LetterBox")) {
			
			event.getPlayer().sendMessage(Utils.Format("&b&l[Envelope] &r&bYou placed a letter box"));
			
			Letterbox box = new Letterbox(event.getBlock().getLocation(), event.getPlayer().getUniqueId(), NBTEditor.GetNBTString(event.getItemInHand(), "ID"));
			
			List<Letterbox> letterboxes = (List<Letterbox>) ConfigManager.getCustomConfig().getList("letterBoxes");
			letterboxes.add(box);
			
			ConfigManager.save(letterboxes);
			
		}
		
	}
	
}
