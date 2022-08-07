package me.anon695.simpleauth.setupmode;

import me.anon695.simpleauth.SimpleAuth;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommands implements CommandExecutor {
    private SimpleAuth simpleAuth;
    public SetupCommands(SimpleAuth simpleAuth) {
        this.simpleAuth = simpleAuth;
    }
    private Boolean enableSetupMode = false;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("simpleauth.setup")) {
                if (simpleAuth.getConfig().getBoolean("setup")) {
                    if (args.length == 0) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlease provide an argument! [setspawn/help]"));
                    }
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("setspawn")) {

                        } else if (args[0].equalsIgnoreCase("help")) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', StringUtils.center("&b&lSIMPLEAUTH by Anon695#1080", 154)));
                        }
                    }
                } else {
                    if (!enableSetupMode) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSetup mode has been enabled! All current players are going to be kicked!"));
                        enableSetupMode = true;
                        simpleAuth.getConfig().set("setup", true);
                        simpleAuth.saveConfig();
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (!p.hasPermission("simpleauth.setup")) {
                                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to join! if you are an admin please check console!"));
                                simpleAuth.getLogger().info(ChatColor.RED + "The player " + p.getName() + " tried to join but the server but does not have permission simpleauth.setup!");
                            }
                        }
                    }
                }
            }
        } else {
            simpleAuth.getLogger().info(ChatColor.RED + "This command is only for players!");
        }
        return false;
    }
}
