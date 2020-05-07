package me.KaspianR.envelope.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.KaspianR.envelope.ConfigManager;
import me.KaspianR.envelope.Main;
import me.KaspianR.envelope.utils.LetterBox;
import me.KaspianR.envelope.utils.NBTEditor;
import me.KaspianR.envelope.utils.Utils;

//THIS CODE IS STILL WORK IN PROGRESS AND THIS IS JUST USED FOR EXPERIMENTING
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
			
			event.getPlayer().sendMessage(Utils.Format("&bYou placed a letter box"));
			
			LetterBox box = new LetterBox(event.getBlock().getLocation(), event.getPlayer());
			
			/*=================================*/
			box.inventory.addItem(Main.Envelope);
			/*=================================*/
			
			List<LetterBox> letterBoxes = (List<LetterBox>) ConfigManager.getCustomConfig().getList("letterBoxes");
			letterBoxes.add(box);
			
			ConfigManager.save(letterBoxes);
			
			//ConfigManager.getCustomConfig().set("letterBoxes", letterBoxes);
			//ConfigManager.saveCustomConfig();
			
		}
		
	}
	
}
