package dev.skript.givebook.commands;

import com.google.common.collect.Lists;
import dev.skript.givebook.Givebook;
import dev.skript.givebook.utils.MetaCooldown;
import dev.skript.givebook.utils.SUtil;
import net.splodgebox.eliteenchantments.EliteEnchantments;
import net.splodgebox.eliteenchantments.enchants.data.CustomEnchant;
import net.splodgebox.eliteenchantments.enchants.data.EnchantBook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CmdBook implements CommandExecutor, TabCompleter {

    private Givebook instance;

    public CmdBook(Givebook instance){
        this.instance = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(! (sender instanceof Player)) {
            sender.sendMessage(SUtil.fixColor("&c&l(!) &cThis is an in game command only."));
            return false;
        }
        Player player = (Player) sender;
        FileConfiguration fileConfiguration = instance.getConfig();
        if(! player.hasPermission("givebook.use")) {
            player.sendMessage(SUtil.fixColor(fileConfiguration.getString("Commands.Book.Permission")));
            return false;
        }

        if(args.length == 0) {
            player.sendMessage(SUtil.fixColor(fileConfiguration.getString("Commands.Book.InvalidArgs")));
            return false;
        }

        if (!player.hasPermission("givebook.cooldown")) {
            MetaCooldown metaCooldown = new MetaCooldown(player, "givebook_cooldown", fileConfiguration.getInt("Options.Cooldown") * 1000L, instance);
            if (metaCooldown.getCooldown() != null && metaCooldown.getCooldown() > System.currentTimeMillis()) {
                player.sendMessage(SUtil.fixColor(fileConfiguration.getString("Commands.Book.Cooldown").replace("%s%", String.valueOf(metaCooldown.getCooldownSeconds()))));
                return false;
            }
            metaCooldown.start();
        }
        SUtil.addItem(player, getBook(player, args[0]));
        player.sendMessage(SUtil.fixColor(fileConfiguration.getString("Commands.Book.Received").replace("%enchantment%", args[0])));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if(! sender.hasPermission("givebook.use")) return null;
        List<String> tabCompletions = Lists.newArrayList();
        tabCompletions.addAll(EliteEnchantments.getInstance().getEnchantManager().getEnchantController().getEnchants().keySet());
        return tabCompletions;
    }

    public ItemStack getBook(Player player, String enchant){
        CustomEnchant customEnchant = EliteEnchantments.getInstance().getEnchantManager().getEnchantController().getEnchant(enchant);
        try {
            return new EnchantBook(customEnchant, customEnchant.getMaxLevel(), 100).create();
        } catch (NullPointerException | IllegalArgumentException exception){
            player.sendMessage(SUtil.fixColor(instance.getConfig().getString("Commands.Book.InvalidEnchantment")));
        }
        return null;
    }


}
