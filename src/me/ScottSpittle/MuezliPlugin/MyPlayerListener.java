package me.ScottSpittle.MuezliPlugin;

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
				Block block = player.getTargetBlock(null, 50);
				Location location = block.getLocation();
				World world = player.getWorld();
				world.createExplosion(location, 5);
			}else if(blockID == 2){
				Block block = player.getTargetBlock(null, 50);
				Location location = block.getLocation();
				World world = player.getWorld();
				world.strikeLightning(location);
			}
		}
		
	}

}
