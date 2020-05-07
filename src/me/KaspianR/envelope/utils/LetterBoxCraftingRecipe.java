package me.KaspianR.envelope.utils;

import org.bukkit.Material;

public class LetterBoxCraftingRecipe {
	
	public String url;
	public Material dye;
	public String name;
	public String id;
	
	public LetterBoxCraftingRecipe(String url, Material dye, String name, String id) {
		
		this.url = "http://textures.minecraft.net/texture/" + url;
		this.dye = dye;
		this.name = name == "" ? "Letter box" : name + " letter box";
		this.id = "_" + id;
		
	}
	
}
