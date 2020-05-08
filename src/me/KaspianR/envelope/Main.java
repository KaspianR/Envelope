package me.KaspianR.envelope;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.KaspianR.envelope.classes.LetterBoxCraftingRecipe;
import me.KaspianR.envelope.classes.Letterbox;
import me.KaspianR.envelope.gui.LetterboxGUI;
import me.KaspianR.envelope.gui.SealEnvelopeGUI;
import me.KaspianR.envelope.listeners.BlockBreakListener;
import me.KaspianR.envelope.listeners.BlockPlaceListener;
import me.KaspianR.envelope.listeners.CraftingPrepareListener;
import me.KaspianR.envelope.listeners.InventoryClickListener;
import me.KaspianR.envelope.listeners.InventoryCloseListener;
import me.KaspianR.envelope.listeners.PlayerInteractListener;
import me.KaspianR.envelope.utils.ConfigManager;
import me.KaspianR.envelope.utils.NBTEditor;
import me.KaspianR.envelope.utils.Utils;

public class Main extends JavaPlugin{
	
	public static Logger log;
	
	public static ItemStack Envelope;
	public static ItemStack Packet;
	
	public static LetterBoxCraftingRecipe[] LetterBoxTypes = {
			new LetterBoxCraftingRecipe("eb2815b99c13bfc55b4c5c2959d157a6233ab06186459233bc1e4d4f78792c69", Material.AIR, "", ""),
			new LetterBoxCraftingRecipe("95fbbc625fa4eb6496be8dbbf0aa2b28f10297cffbcf5e0aaf6cb11e8f2616ed", Material.GREEN_DYE, "Green", "green"),
			new LetterBoxCraftingRecipe("585d089006a8c7565bebd9d44dab65892235497584145cf4974df9dca04db745", Material.BROWN_DYE, "Brown", "brown"),
			new LetterBoxCraftingRecipe("279f02402eb523006afbc43b6662878a1bced9a42829fe2283000f2a18c89c89", Material.CYAN_DYE, "Cyan", "cyan"),
			new LetterBoxCraftingRecipe("885300f63e52ab6cab17955050b757556d56c4e3d83220b3295e305d0106efa", Material.MAGENTA_DYE, "Magenta", "magenta"),
			new LetterBoxCraftingRecipe("e4b349e74cf9a55af031e392aa808c5c161e157a4ebb78414481ca0cbba2fae3", Material.LIGHT_BLUE_DYE, "Light blue", "light_blue"),
			new LetterBoxCraftingRecipe("b96af36eaaed8a7dbd2727fd4cd7afbc6c0afb8c7606294c9cb401141aac379", Material.GRAY_DYE, "Gray", "gray"),
			new LetterBoxCraftingRecipe("30ae3d50642379e98a64d4a06bb4ef94a34b4786da3784a930d9346ec6113d2", Material.LIGHT_GRAY_DYE, "Light gray", "light_gray"),
			new LetterBoxCraftingRecipe("8eab41e3cb8769ebad2a821d55ccb85a4a940dd5c6e8bf738b0b122f8791e", Material.RED_DYE, "Red", "red"),
			new LetterBoxCraftingRecipe("d9601262988392927f9e5ee12bedd5ba9f4c01797681f153529cd43e23488e4", Material.ORANGE_DYE, "Orange", "orange"),
			new LetterBoxCraftingRecipe("9281c01539c7c83b7427fc0b180b3dc884a1438d6bee48ceccfbef791fcac", Material.YELLOW_DYE, "Yellow", "yellow"),
			new LetterBoxCraftingRecipe("95d9b6d47e97362f9c4bbcf9e2a7ab312383de6e63549cf05e2ce3b13bc11320", Material.BLUE_DYE, "Blue", "blue"),
			new LetterBoxCraftingRecipe("9585e6888c19918ea7de1bf6ca8ddea5eb833a83e85c46d4e7ef7739e2f6", Material.PURPLE_DYE, "Purple", "purple")};
	
	static {
		
        ConfigurationSerialization.registerClass(Letterbox.class, "Letterbox");
        
    }
	
	@Override
    public void onLoad() {
        log = this.getLogger();
    }
	
	@Override
	public void onEnable() {
		
		ConfigManager.setPlugin(this);
		
		//Save configs
		this.saveDefaultConfig();
		ConfigManager.createCustomConfig();
		
		//Setup listeners
		new InventoryClickListener(this);
		new InventoryCloseListener(this);
		new PlayerInteractListener(this);
		new CraftingPrepareListener(this);
		new BlockPlaceListener(this);
		new BlockBreakListener(this);
		SealEnvelopeGUI.Initialize(this);
		LetterboxGUI.Initialize(this);
		
		Envelope = new ItemStack(Material.PAPER, 1);
		
		//Setup item
        ItemMeta EnvelopeMeta = Envelope.getItemMeta();
        EnvelopeMeta.setDisplayName(Utils.Format("&r&6Envelope"));
        Envelope.setItemMeta(EnvelopeMeta);
        Envelope = NBTEditor.AddNBTString(Envelope, "Container", "Envelope");
        
        //Setup crafting recipe
        NamespacedKey EnvelopeKey = new NamespacedKey(this, "envelope");
        ShapedRecipe EnvelopeRecipe = new ShapedRecipe(EnvelopeKey, Envelope);
        EnvelopeRecipe.shape("PP", "PP");
        EnvelopeRecipe.setIngredient('P', Material.PAPER);
        Bukkit.addRecipe(EnvelopeRecipe);
        
        Packet = Utils.GetSkull("http://textures.minecraft.net/texture/fa85971fb13bf0b78eb9f96b2bdce1a11331373de30d9239e8bc06a2912c4a4");
		
        //Setup item
        ItemMeta PacketMeta = Packet.getItemMeta();
        PacketMeta.setDisplayName(Utils.Format("&r&6Package"));
        Packet.setItemMeta(PacketMeta);
        Packet = NBTEditor.AddNBTString(Packet, "Container", "Package");
        
        //Setup crafting recipe
        NamespacedKey PacketKey = new NamespacedKey(this, "packet");
        ShapedRecipe PacketRecipe = new ShapedRecipe(PacketKey, Packet);
        PacketRecipe.shape("PPP", "P P", "PPP");
        PacketRecipe.setIngredient('P', Material.PAPER);
        Bukkit.addRecipe(PacketRecipe);
        
        //Loop through all color variants
        for(int n = 0; n < LetterBoxTypes.length; n++) {
        	
        	LetterBoxTypes[n].Letterbox = Utils.GetSkull(LetterBoxTypes[n].url);
			
	        //Setup item
	        ItemMeta LetterBoxMeta = LetterBoxTypes[n].Letterbox.getItemMeta();
	        LetterBoxMeta.setDisplayName(Utils.Format("&r&6" + LetterBoxTypes[n].name));
	        LetterBoxTypes[n].Letterbox.setItemMeta(LetterBoxMeta);
	        LetterBoxTypes[n].Letterbox = NBTEditor.AddNBTString(LetterBoxTypes[n].Letterbox, "LetterBox", "true");
	        LetterBoxTypes[n].Letterbox = NBTEditor.AddNBTString(LetterBoxTypes[n].Letterbox, "ID", LetterBoxTypes[n].id);
	        
	        //Setup crafting recipe
	        NamespacedKey LetterBoxKey = new NamespacedKey(this, "letterbox_" + LetterBoxTypes[n].id);
	        ShapedRecipe LetterBoxRecipe = new ShapedRecipe(LetterBoxKey, LetterBoxTypes[n].Letterbox);
	        LetterBoxRecipe.shape(" I ", "ICI", "IDI");
	        LetterBoxRecipe.setIngredient('I', Material.IRON_INGOT);
	        LetterBoxRecipe.setIngredient('C', Material.CHEST);
	        LetterBoxRecipe.setIngredient('D', LetterBoxTypes[n].dye);
	        Bukkit.addRecipe(LetterBoxRecipe);
	        
        }
        
	}
	
}
