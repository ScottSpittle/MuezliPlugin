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

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class MyPlayerListener implements Listener {
	
	public static Main plugin;
    public static Permission perms = null;
		
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event){
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.BLUE + "Hey " + player.getName() + ", Welcome to the Muezli-Server");
	}

	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(player.isOp()){
			int blockID = player.getItemInHand().getType().getId();

			if(blockID == 1){
				if(perms.has(player, "muezli.explode")){
					Block block = player.getTargetBlock(null, 50);
					Location location = block.getLocation();
					World world = player.getWorld();
					world.createExplosion(location, 5);
					player.sendMessage(ChatColor.BLUE + "willies");
				}else{
					player.sendMessage(ChatColor.DARK_RED + "No Permissions");
				}
			}else if(blockID == 2){
				if(perms.has(player, "muezli.lightning")){
					Block block = player.getTargetBlock(null, 50);
					Location location = block.getLocation();
					World world = player.getWorld();
					world.strikeLightning(location);
				}else{
					player.sendMessage(ChatColor.DARK_RED + "No Permissions");
				}
			}
		}
		
	}
}
