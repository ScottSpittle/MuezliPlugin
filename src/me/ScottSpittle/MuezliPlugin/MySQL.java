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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;

public class MySQL {
	public static Main plugin;
	protected Connection db;
	protected String uri;
	protected String user;
	protected String password;
	public int updateCount = 0;
	public ArrayList<Long> homeLocation = new ArrayList<Long>();;
	
	public MySQL(Main instance){
		plugin = instance;
	}

	public void connectMySQL() {
		try {
			FileConfiguration config = plugin.getConfig();

			this.uri = config.getString("SQLConnection.url");
			this.user = config.getString("SQLConnection.user");
			this.password = config.getString("SQLConnection.pass");

			this.connect();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void checkConnection() throws SQLException {
		if (!this.db.isValid(3)) {
			plugin.getLogger().info("Lost connection with sql server. Reconnecting.");
			this.connect();
		}
	}
	
	protected final void connect() throws SQLException {
        try {
    		plugin.getLogger().info("[MuezliPlugin-SQL] Connecting to database...");
    		db = DriverManager.getConnection("jdbc:" + uri, user, password);
            Statement st = db.createStatement();
            String table = "CREATE TABLE IF NOT EXISTS MuezliPlugin_Homes(id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY(id), player TEXT, world_name TEXT, games_played TEXT, x_coord double, y_coord double, z_coord double, yaw smallint(6), pitch smallint(6))";
            st.executeUpdate(table);
        } catch (Exception e) {
        	plugin.getLogger().severe("Could not connect to database! Verify your database details in the configuration are correct.");
            plugin.disablePlugin();
        }
    }
	
	public void insertQuery(String player, String world_name, Double x, Double y, Double z, int yaw, int pitch) throws SQLException{
		Connection conn = db;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO `MuezliPlugin_Homes` (`player`, `world_name`, `x_coord`, `y_coord`, `z_coord`, `yaw`, `pitch`) VALUES ('" + player + "','" + world_name + "','" + x + "','" + y + "','" + z + "','" + yaw + "','" + pitch + "')");

			st.executeUpdate();
			updateCount = st.getUpdateCount();
		} catch(SQLException e) {
			plugin.blo.logger.warning("Unable to create Home");
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}
	public void updateQuery(String player, String world_name, Double x, Double y, Double z, int yaw, int pitch) throws SQLException{
		Connection conn = db;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE `MuezliPlugin_Homes` SET `x_coord`='" + x +"', `y_coord`='" + y +"', `z_coord`='" + z +"', `yaw`='" + yaw +"', `pitch`= '" + pitch + "' WHERE `world_name` = '" + world_name + "' AND `player` = '" + player + "'");
			st.executeUpdate();
			updateCount = st.getUpdateCount();
		} catch(SQLException e) {
			plugin.blo.logger.warning("Unable to create Home");
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}
	
	public void homeExsists(String player, String world_name) throws SQLException{
		Connection conn = db;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `MuezliPlugin_Homes` WHERE `world_name` = '" + world_name + "' AND `player` = '" + player + "'");
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				updateCount = updateCount + 1;
				homeLocation.add(0, rs.getLong("x_coord"));
				homeLocation.add(1, rs.getLong("y_coord"));
				homeLocation.add(2,rs.getLong("z_coord"));
				homeLocation.add(3,rs.getLong("yaw"));
				homeLocation.add(4,rs.getLong("pitch"));
			}
		} catch(SQLException e) {
			plugin.blo.logger.warning("Unable to check Home");
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}
	
	@Override
	protected void finalize() throws SQLException {
		try {
			db.close();
		} catch (SQLException e) {
			plugin.getLogger().warning("Error while disconnecting from database: {0}" + e.getMessage());
		}
	}
	
} 