package me.anon695.simpleauth.setupmode;

import me.anon695.simpleauth.SimpleAuth;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    private SimpleAuth simpleAuth;
    public JoinEvent(SimpleAuth simpleAuth) {
        this.simpleAuth = simpleAuth;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(simpleAuth.getConfig().getBoolean("setup") && player.hasPermission("simpleauth.setup")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bWelcome to SimpleAuth setup, this will guide you through all the steps to setup the plugin!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fFirstly do /setup setspawn to set the spawn of the server!"));
        } else {
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to join! if you are an admin please check console!"));
            simpleAuth.getLogger().info(ChatColor.RED + "The player " + player.getName() + " tried to join but the server but does not have permission simpleauth.setup!");
        }
    }
}
