package dev.skript.givebook.utils;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class MetaCooldown {

    private Player player;
    private String string;
    private long time;
    private JavaPlugin instance;



    public MetaCooldown(Player player, String metaData, long time, JavaPlugin instance){
        this.player = player;
        this.string = metaData;
        this.time = time;
        this.instance = instance;
    }


    public void start(){
        player.setMetadata(string, new FixedMetadataValue(instance, System.currentTimeMillis() + time));
    }

    public Long getCooldown(){
        return (player.hasMetadata(string) && player.getMetadata(string).size() > 0) ? player.getMetadata(string).get(0).asLong() : null;
    }

    public int getCooldownSeconds(){
        return (int) (getCooldown() / 1000L  - (System.currentTimeMillis() / 1000));
    }
}