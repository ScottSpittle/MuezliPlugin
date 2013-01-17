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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class MyBlockListener implements Listener {

	public static Main plugin;
	public static Material[] blacklist = {Material.BEDROCK, Material.DIAMOND_BLOCK};
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event) {
		Material Block = event.getBlock().getType();
		Player player = event.getPlayer();
		
		for(Material blocked : blacklist) {
			if(blocked == Block){
				if(player.isOp()){
					
				}else {
					event.getBlock().setType(Material.AIR);
					player.chat("I Just Placed " + ChatColor.DARK_RED + blocked);
				}
			}
		}
	}
}
