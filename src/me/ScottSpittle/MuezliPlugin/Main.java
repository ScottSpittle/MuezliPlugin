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

import java.sql.SQLException;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main plugin;
	public final MyBlockListener blockListener = new MyBlockListener();
	public final MyPlayerListener pl = new MyPlayerListener();
	public final MySQL sql = new MySQL();
	public final BukkitLogger blo = new BukkitLogger(this);
    public static Permission perms = null;
	
	
	@Override
	public void onDisable(){
		blo.enabled(false);
	}

	@Override
	public void onEnable(){
		blo.enabled(true);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.blockListener, this);
		pm.registerEvents(this.pl, this);
        setupPermissions();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
        if(sql.yes == true) {
            try {
            	sql.create_tablesConnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("[MuezliPlugin_MySQL] Error While Connecting To Database!");
        }
        System.out.println("[MuezliPlugin_MySQL] Connected to Database");
	}
	
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	Player player = (Player) sender;

    	if (commandLabel.equalsIgnoreCase("muezlihome")){
    		if(args.length == 0){
				if(perms.has(player, "muezli.home.home")){
					try {
						sql.SelectQuery("SELECT * FROM `MuezliPlugin_Homes`");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else {
					//sender.sendMessage(ChatColor.DARK_RED + "No Permission muezli.home.home");
				}
    		}
    		if(args.length == 1){
    			if (args[0].equalsIgnoreCase("set")) {
    				if(perms.has(player, "muezli.home.set")){
    					Location homeLoc = player.getLocation();    					
    					int x = homeLoc.getBlockX();
    					int y = homeLoc.getBlockY();
    					int z = homeLoc.getBlockZ();
    					int homeYaw = (int) homeLoc.getYaw();
    					int homePitch = (int) homeLoc.getPitch();
    					try {
    						sql.insertQuery("INSERT INTO `MuezliPlugin_Homes` (`player`, `world_name`, `x_coord`, `y_coord`, `z_coord`, `yaw`, `pitch`) VALUES ('" + player.getDisplayName() +"','" +  player.getLocation().getWorld().getName() +"','" + x +"','" + y +"','" + z +"','" + homeYaw +"', '" + homePitch + "')");
    					} catch (SQLException e) {
    						e.printStackTrace();
    					}
    					player.sendMessage(player.getDisplayName() + " " + player.getLocation().getWorld().getName() + " " + x + " " + y + " " + z + " " + homeYaw + " " + homePitch);
    				}else {
    					sender.sendMessage(ChatColor.DARK_RED + "No Permission muezli.home.set");
    				}
    			} else {
    				player.sendMessage("Invalid Command");
    			}
    		}
    	}
    	return false;
    }
}