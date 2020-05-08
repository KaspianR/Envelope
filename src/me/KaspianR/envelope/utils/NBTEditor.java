package me.KaspianR.envelope.utils;

import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;

import net.minecraft.server.v1_15_R1.ItemStack;
import net.minecraft.server.v1_15_R1.NBTTagCompound;

public class NBTEditor {
	
	private static NBTTagCompound GetNBTTag(org.bukkit.inventory.ItemStack item) {
        ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
        if (itemNms.hasTag()) tag = itemNms.getTag();
        else tag = new NBTTagCompound();
        return tag;
    }
	
    private static org.bukkit.inventory.ItemStack SetNBTTag(org.bukkit.inventory.ItemStack item, NBTTagCompound tag) {
        ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        itemNms.setTag(tag);
        return CraftItemStack.asBukkitCopy(itemNms);
    }
    
    public static org.bukkit.inventory.ItemStack AddNBTString(org.bukkit.inventory.ItemStack item, String name, String value) {
        NBTTagCompound tag = GetNBTTag(item);
        tag.setString(name, value);
        return SetNBTTag(item, tag);
    }
    
    public static boolean HasNBTString(org.bukkit.inventory.ItemStack item, String name) {
        NBTTagCompound tag = GetNBTTag(item);
        return tag.hasKey(name);
    }
    
    public static String GetNBTString(org.bukkit.inventory.ItemStack item, String name) {
        NBTTagCompound tag = GetNBTTag(item);
        return tag.getString(name);
    }
	
}
