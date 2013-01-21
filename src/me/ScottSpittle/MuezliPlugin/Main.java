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

import java.io.File;
import java.sql.SQLException;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main plugin;
	//public final MyPlayerListener pl = new MyPlayerListener();
	public final MySQL sql = new MySQL(this);
	public final BukkitLogger blo = new BukkitLogger(this);
    public static Permission perms = null;
    public static Player player = null;
    public boolean isPlayer = false;

	@Override
	public void onDisable(){
		blo.enabled(false);
	}

	@Override
	public void onEnable(){
		//set plugin to this instance.
		plugin = this;
	    //run BukkitLogger class on enable.
		blo.enabled(true);
		//register with the plugin manager
	  //PluginManager pm = getServer().getPluginManager();
		//register player events
	  //pm.registerEvents(this.pl, this);
		//using vault setting up permissions.
		setupPermissions();
		//create config if it doesn't exsist
		createConfig();
		//check config
		checkConfig();
		//Connect to database.
		sql.connectMySQL();
	}
	//Register Permissions via Vault.
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	//Checks config file has SQL Connection informaion..
    public void checkConfig(){
        if(getConfig().getString("SQLConnection.user").equalsIgnoreCase("") || getConfig().getString("SQLConnection.pass").equalsIgnoreCase("") || getConfig().getString("SQLConnection.url").equalsIgnoreCase("")){
        	blo.logger.severe("[MuezliPlugin] SQL Connection inofrmation has not been set properly");
        	disablePlugin();
            return;
        }
	}
    
	//Creates the config file ..
	public void createConfig(){
		File file = new File(getDataFolder()+File.separator+"config.yml");
		if(!file.exists()){
			getLogger().info("[MuezliPlugin] Creating default config file ...");
			saveDefaultConfig();
			getLogger().info("[MuezliPlugin] Config created successfully!");
		}else {
			getLogger().info("[MuezliPlugin] Config Already Exsists!");
		}
	}

	//Disables the plugin
	public void disablePlugin(){
		Bukkit.getPluginManager().disablePlugin(this);
	}

	//When a player types a Command.
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(sender instanceof Player){
			isPlayer = true;
		}
		if(commandLabel.equalsIgnoreCase("home")){
			if(isPlayer){
				Player player = (Player) sender;
				if(args.length == 0){
					if(perms.has(player, "muezli.home")){
						String homePlayer = player.getDisplayName();
						String homeWorld = player.getLocation().getWorld().getName();
						try {
							sql.homeExsists(homePlayer, homeWorld);
							if (sql.updateCount >= 1){   					
								long x = sql.homeLocation.get(0);
								long y = sql.homeLocation.get(1);
								long z = sql.homeLocation.get(2);
								long yaw = sql.homeLocation.get(3);
								long pitch = sql.homeLocation.get(4);
								Location hLoc = new Location(Bukkit.getWorld(homeWorld), x, y, z);
								hLoc.setPitch(pitch);
								hLoc.setYaw(yaw);
								player.teleport(hLoc);
								player.sendMessage(ChatColor.BLUE + "Welcome back to your home");
								sql.updateCount = 0;
							}else{
								player.sendMessage(ChatColor.RED + "You must set a home in this world before you can teleport to it.");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
						player.sendMessage(ChatColor.RED + "you don't have permission to use that command");
					}
				}
				if(args.length == 1){
					if (args[0].equalsIgnoreCase("set")) {
						if(perms.has(player, "muezli.home")){
							String homePlayer = player.getDisplayName();
							String homeWorld = player.getLocation().getWorld().getName();
							Location homeLoc = player.getLocation();    					
							double x = homeLoc.getBlockX();
							double y = homeLoc.getBlockY();
							double z = homeLoc.getBlockZ();
							int yaw = (int) homeLoc.getYaw();
							int pitch = (int) homeLoc.getPitch();
							try {
								sql.homeExsists(homePlayer, homeWorld);
								if (sql.updateCount >= 1){
									sql.updateQuery(homePlayer, homeWorld, x, y, z, yaw, pitch);
									if (sql.updateCount == 1){
										player.sendMessage(ChatColor.GREEN + "Home successfully updated");
										sql.updateCount = 0;
									}
								}else {
									sql.insertQuery(homePlayer, homeWorld, x, y, z, yaw, pitch);
									if(sql.updateCount == 1){
										player.sendMessage(ChatColor.GREEN + "Home Created Successfully");
										sql.updateCount = 0;
									}else {
										player.sendMessage(ChatColor.RED + "Home was not created");
									}
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							player.sendMessage(ChatColor.RED + "you don't have permission to use that command");
						}
					}
						if (!args[0].equalsIgnoreCase("set")) {
						if(perms.has(player, "muezli.home.other")){
							String homePlayer = args[0];
							String homeWorld = player.getLocation().getWorld().getName();
							try {
								sql.homeExsists(homePlayer, homeWorld);
								if (sql.updateCount >= 1){   					
									long x = sql.homeLocation.get(0);
									long y = sql.homeLocation.get(1);
									long z = sql.homeLocation.get(2);
									long yaw = sql.homeLocation.get(3);
									long pitch = sql.homeLocation.get(4);
									Location hLoc = new Location(Bukkit.getWorld(homeWorld), x, y, z);
									hLoc.setPitch(pitch);
									hLoc.setYaw(yaw);
									player.teleport(hLoc);
									player.sendMessage(ChatColor.BLUE + "Your at " + args[0] + "'s home");
									sql.updateCount = 0;
								}else{
									player.sendMessage(ChatColor.RED + args[0] + " Does not have a home set");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else {
							player.sendMessage(ChatColor.RED + "you don't have permission to use that command");
						}
					}
				}else {
					if(args.length > 1){
						player.sendMessage(ChatColor.YELLOW + "Command does not Exsist");
					}
				}
			}else{
				sender.sendMessage(ChatColor.RED + "You must be a player");
			}
		}

		if(commandLabel.equalsIgnoreCase("homehelp")){
			Player player = (Player) sender;
			player.sendMessage(ChatColor.YELLOW + "Muezli Help Is Here ^^");
			
		}
			return false;
	}
}