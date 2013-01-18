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

import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main plugin;
	public final MyPlayerListener pl = new MyPlayerListener();
	public final MySQL sql = new MySQL();
	public final BukkitLogger blo = new BukkitLogger();
    public static Permission perms = null;
    public static Player player = null;
    public boolean isPlayer = false;

	@Override
	public void onDisable(){
		blo.enabled(false);
	}

	@Override
	public void onEnable(){
		plugin = this; //set plugin to this instance.
		blo.enabled(true); //run BukkitLogger class on enable.
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.pl, this);
        setupPermissions();
        
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(sender instanceof Player){
			isPlayer = true;
		}
		if(commandLabel.equalsIgnoreCase("muezli")){
			if(isPlayer){
				Player player = (Player) sender;
				if(perms.has(player, "muezli.muezli")){
					sql.getConfigValues();
				}else{
					player.sendMessage("you don't have permission to use that command");
				}
			}else{
				sender.sendMessage("You must be a player");
			}
		}
		return false;
	}
}