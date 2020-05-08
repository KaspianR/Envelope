package me.KaspianR.envelope.classes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//This class is used for storing all the information about a single crafting recipe allowing for easy edits
public class LetterBoxCraftingRecipe {
	
	public String url;
	public Material dye;
	public String name;
	public String id;
	
	public ItemStack Letterbox;
	
	public LetterBoxCraftingRecipe(String url, Material dye, String name, String id) {
		
		this.url = "http://textures.minecraft.net/texture/" + url;
		this.dye = dye;
		this.name = name == "" ? "Letter box" : name + " letter box";
		this.id = id;
		
	}
	
}
