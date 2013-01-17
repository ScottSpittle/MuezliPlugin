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
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class MySQL {
	public static Main plugin;
    static String user = "scott"; 
    static String pass = ",08@u!<J1|JQg7};SVjewi!CP96X~Y"; 
    static String url = "jdbc:mysql://localhost:3306/MuezliServerPrivate";
    public boolean yes = true;
    Connection conn = null;
	int counter = 0;
	String world_name = null;
	Double x_coord = null;
	Double z_coord = null;
	Double y_coord = null;
	int yaw = 0;
	int pitch = 0;
    
    public void create_tablesConnect() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, pass);
        PreparedStatement QueryStatement = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `MuezliPlugin_Homes` (`id` int NOT NULL UNIQUE, `player` varchar(32) NOT NULL, `world_name` varchar(32) NOT NULL , `x_coord` double NOT NULL, `y_coord` double NOT NULL, `z_coord` double NOT NULL, `yaw` smallint(6) NOT NULL, `pitch` smallint(6) NOT NULL)");

        QueryStatement.executeUpdate();
        QueryStatement.close();
        
        conn.close();
    }

    public void insertQuery(String query) throws SQLException{
    	try{
    		Connection conn = DriverManager.getConnection(url, user, pass);
    		Statement statement = conn.createStatement();
    		statement.execute(query);
    		if(statement != null){
    			System.out.println("MuezliPlugin Performed a Inster Query");
    		}
    	}catch (SQLException s){
    		System.out.println("SQL statement is not executed!");
    	}finally {
    		if(conn != null) try {conn.close();} catch(SQLException ignore){};
    	}
    }

    public void SelectQuery(String query, int action) throws SQLException{
    	try {
    		Connection conn = DriverManager.getConnection(url, user, pass);
    		Statement statement = conn.createStatement();

    		ResultSet rs = statement.executeQuery(query);

    		while(rs.next()){
    			counter = counter + 1;
    			//Long id = rs.getLong("id");
    			//String player = rs.getString("player");
    			world_name = rs.getString("world_name");
    			x_coord = (double) rs.getLong("x_coord");
    			z_coord = (double) rs.getLong("z_coord");
    			y_coord = (double) rs.getLong("y_coord");
    			yaw = (int) rs.getLong("yaw");
    			pitch = (int) rs.getLong("pitch");
    		    
    			//Main.player.sendMessage("ID: " + id + " Player: " + player + " World: " + world_name + "\n");
    			//System.out.println("ID: " + id + " Player: " + player + " World: " + world_name + "\n");
    		}
			if (counter == 1 && action == 1){
				Location hLoc = new Location(Bukkit.getWorld(world_name), x_coord, y_coord, z_coord);
				hLoc.setPitch(pitch);
				hLoc.setYaw(yaw);
				Main.player.teleport(hLoc);
			}else if(counter == 0){
				Main.player.sendMessage("Im sorry, You havent set a home yet.");
			}
    	}catch (SQLException s){
    		System.out.println("SQL statement is not executed!");
    	}finally {
    		if(conn != null) try {conn.close();} catch(SQLException ignore){};
    	}
    }
} 
