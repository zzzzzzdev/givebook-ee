package dev.skript.givebook.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SUtil {

    public static String fixColor(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void addItem(Player player, ItemStack itemStack){
        if(itemStack == null)return;
        if(itemStack.getType() == Material.AIR)return;

        if(player.getInventory().firstEmpty() == -1){
            player.getWorld().dropItem(player.getLocation(), itemStack);
            return;
        }
        player.getInventory().addItem(itemStack);
    }


}
