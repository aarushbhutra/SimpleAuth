package me.anon695.simpleauth;

import me.anon695.simpleauth.setupmode.JoinEvent;
import me.anon695.simpleauth.setupmode.SetupCommands;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class SimpleAuth extends JavaPlugin implements Listener {

    public File file = new File(getDataFolder(), "data.yml");
    public YamlConfiguration dataFile = YamlConfiguration.loadConfiguration(file);

    //public File mfile = new File(getDataFolder(), "message.yml");
    //public YamlConfiguration messageFile = YamlConfiguration.loadConfiguration(file);
    @Override
    public void onEnable() {
        this.file = new File(getDataFolder(), "data.yml");
        getDataFolder().mkdirs();
        //this.mfile = new File(getDataFolder(), "message.yml");
        //getDataFolder().mkdirs();
        this.dataFile = YamlConfiguration.loadConfiguration(file);
        //this.messageFile = YamlConfiguration.loadConfiguration(mfile);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("[SimpleAuth] Could not create data.yml file! Please reinstall the plugin and contact Anon695#0180");
                e.printStackTrace();
                return;
            }
        }
        try {
            dataFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        /**if(!mfile.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("[SimpleAuth] Could not create message.yml file! Please reinstall the plugin and contact Anon695#0180");
                e.printStackTrace();
                return;
            }
        }
        try {
            messageFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }**/
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        if(getConfig().getString("storage-method").equalsIgnoreCase("uuid")){
            UUID uuid = event.getUniqueId();
            if(!dataFile.contains(uuid.toString())) {
                dataFile.set(uuid.toString(), "");
                System.out.println("[SimpleAuth] Added " + uuid + " to the data file!");
            }
            try {
                dataFile.save(file);
            } catch (IOException e) {
                e.printStackTrace();
                return; 
            }
        } else if(getConfig().getString("storage-method").equalsIgnoreCase("username")) {
            String player = event.getName();
            if(!dataFile.contains(player)) {
                dataFile.set(player, "");
                System.out.println("[SimpleAuth] Added " + player + " to the data file!");
            }
            try {
                dataFile.save(file);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            System.out.println("[SimpleAuth] The given method of storage " + getConfig().getString("storage-method") + " is not a valid storage type! Please fix this in the config!");
            //event.getPlayer().kickPlayer();
        }
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinEvent(this), this);
    }

    public void registerCommands() {
        getCommand("setup").setExecutor(new SetupCommands(this));
    }

}
