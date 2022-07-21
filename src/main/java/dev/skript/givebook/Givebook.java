package dev.skript.givebook;

import dev.skript.givebook.commands.CmdBook;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Givebook extends JavaPlugin {

    private static Givebook instance;



    @Override
    public void onEnable(){
        instance = this;
        saveDefaultConfig();
        PluginCommand pluginCommand = getCommand("Book");
        pluginCommand.setExecutor(new CmdBook(instance));
        pluginCommand.setTabCompleter(new CmdBook(instance));
    }

    public static Givebook getInstance(){
        return instance;
    }
}
