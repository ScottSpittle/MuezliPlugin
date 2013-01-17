/*
   Copyright 2013 Scott Spittle, James Loyd

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package me.ScottSpittle.MuezliPlugin;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main plugin;
	public final MyBlockListener blockListener = new MyBlockListener();
	public final MyPlayerListener pl = new MyPlayerListener();
	public final BukkitLogger blo = new BukkitLogger(this);
	
	
	@Override
	public void onDisable(){
		PluginManager pm = getServer().getPluginManager();
		blo.enabled(false);
		pm.addPermission(new Permissions().motd);
		getServer().getPluginManager().removePermission(new Permissions().motd);
	}

	@Override
	public void onEnable(){
		blo.enabled(true);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.blockListener, this);
		pm.registerEvents(this.pl, this);
		pm.addPermission(new Permissions().motd);
		
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		
		if (commandLabel.equalsIgnoreCase("motd")){
			if(sender.hasPermission(new Permissions().motd)){
				player.sendMessage(getConfig().getString("MOTD") + getConfig().getInt("radius"));
			}else {
				sender.sendMessage("No Permissions");
			}
		}

		if (commandLabel.equalsIgnoreCase("roll")){
			Random object = new Random();
			int muezliInt;

			for(int counter = 1; counter <= 1; counter++) {
				muezliInt =1+object.nextInt(2);
				if (muezliInt == 1){
					player.sendMessage("Heads!");
				}else if(muezliInt == 2){
					player.sendMessage("Tails!");
				}
			}
		}

		if (commandLabel.equalsIgnoreCase("sendme") || commandLabel.equalsIgnoreCase("sm")){
			if (args.length ==0){
				//sendme = 0 args ... /heal scott = 1 args
				player.sendMessage(ChatColor.BLUE + "willies " + getConfig().getInt("radius"));
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
		if (commandLabel.equalsIgnoreCase("willieport")){
			if (args.length == 0){
				player.sendMessage(ChatColor.DARK_RED + "You Didn't Specify a player #noob!");
			} else if (args.length == 1){
				if (player.getServer().getPlayer(args[0]) != null){
					Player targetPlayer = player.getServer().getPlayer(args[0]);
					Location targetPlayerLocation = targetPlayer.getLocation();
					player.teleport(targetPlayerLocation);
					player.sendMessage(ChatColor.GREEN + "Successfully teleported.");
				} else {
					player.sendMessage(ChatColor.BLUE + "Player Is not online, you cannot tp to them");
				}
			} else if (args.length == 2){
				if (player.getServer().getPlayer(args[0]) != null && player.getServer().getPlayer(args[1]) != null ){
					Player targetPlayer1 = player.getServer().getPlayer(args[0]);
					Player targetPlayer2 = player.getServer().getPlayer(args[1]);
					Location targetPlayerLocation = targetPlayer2.getLocation();
					targetPlayer1.teleport(targetPlayerLocation);
					targetPlayer1.sendMessage(ChatColor.GREEN + "You have Teleported to " + targetPlayer2.getDisplayName());
					targetPlayer2.sendMessage(ChatColor.GREEN + targetPlayer1.getDisplayName() + " Teleported to you");
				} else {
					player.sendMessage(ChatColor.BLUE + "One or more of those players is not online... tuttut");
				}
			}
		}

		return false;
	}
}