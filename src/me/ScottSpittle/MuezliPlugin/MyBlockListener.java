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
