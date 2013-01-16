package me.Scottspittle.MuezliPlugin;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin{
	
            public final Logger logger = Logger.getLogger("Minecraft");
            public static main plugin;
           
            @Override
            public void onDisable(){
                    PluginDescriptionFile pdfFile = this.getDescription();
                    this.logger.info(pdfFile.getName() + " Has Been Disabled");
            }
           
            @Override
            public void onEnable(){
                    PluginDescriptionFile pdfFile = this.getDescription();
                    this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled");
            }
           
            public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
                    Player player = (Player) sender;
                    if (commandLabel.equalsIgnoreCase("sendme") || commandLabel.equalsIgnoreCase("sm")){
                            if (args.length ==0){
                                    //sendme = 0 args ... /heal scott = 1 args
                                    player.sendMessage(ChatColor.BLUE + "willies");
                            } else if (args.length == 1) {
                                    if (player.getServer().getPlayer(args[0]) != null){
                                            Player targetPlayer = player.getServer().getPlayer(args[0]);
                                            targetPlayer.sendMessage(ChatColor.BLUE + player.getDisplayName() + " Sent you willies!");
                                            player.sendMessage(ChatColor.RED + "You sent willies to " + targetPlayer.getDisplayName());
                                    } else {
                                            player.sendMessage(ChatColor.BLUE + "Player Is not online, you cannot send them willies");
                                    }
                            }
                    }
                    return false;
            }
}
