package me.ScottSpittle.MuezliPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MyPlayerListener implements Listener {
	
	public static Main plugin;
		
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event){
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.BLUE + "Hey " + player.getName() + ", Welcome to the Muezli-Server");
	}
}
