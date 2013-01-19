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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MyPlayerListener implements Listener {
	
    public static Permission perms = null;
		
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent e){
		Main.plugin.getServer().broadcastMessage("Welcome " + e.getPlayer().getName() + " To The Server");
	}
	@EventHandler
	public void onPlayerQuit (PlayerQuitEvent e){
		//Player player = event.getPlayer();
		Main.plugin.getServer().broadcastMessage(e.getPlayer().getName() + "Fucked off..");
	}

	@EventHandler
	public void onPlayerChat (AsyncPlayerChatEvent e){
		
	}
}
