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

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import me.ScottSpittle.MuezliPlugin.Main;

public class MySQL {
    static String user = null; 
    static String pass = null; 
    static String url = null;
    public boolean yes = true;
    Connection conn = null;

	public void getConfigValues() {
		FileConfiguration config = Main.plugin.getConfig();
		
		String user = config.getString("SQLConnection.user");
		String pass = config.getString("SQLConnection.pass");
		String url = config.getString("SQLConnection.url");
		
    	System.out.println(user + pass + url);
	}
} 
