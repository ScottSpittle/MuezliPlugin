package me.ScottSpittle.MuezliPlugin;


import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQL {
	public static Main plugin;
    static String user = "scott"; 
    static String pass = ",08@u!<J1|JQg7};SVjewi!CP96X~Y"; 
    static String url = "jdbc:mysql://localhost:3306/MuezliServerPrivate";
    public boolean yes = true;
    Connection conn = null;
	int count = 0;
    
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
    		conn = null;
    	}
    }

    public void SelectQuery(String query) throws SQLException{
    	try {
    		Connection conn = DriverManager.getConnection(url, user, pass);
    		Statement statement = conn.createStatement();

    		ResultSet rs = statement.executeQuery(query);

    		while(rs.next()){
    			Long id = rs.getLong("id");
    			String player = rs.getString("player");
    			String world_name = rs.getString("world_name");
    			//Long x_coord = rs.getLong("x_coord");
    			//Long z_coord = rs.getLong("z_coord");
    			//Long y_coord = rs.getLong("y_coord");
    			//Long yaw = rs.getLong("yaw");
    			//Long pitch = rs.getLong("pitch");
    			Main.player.sendMessage("ID: " + id + " Player: " + player + " World: " + world_name + "\n");
    			//System.out.println("ID: " + id + " Player: " + player + " World: " + world_name + "\n");
    		}
    	}catch (SQLException s){
    		System.out.println("SQL statement is not executed!");
    	}finally {
    		conn = null;
    	}
    }
} 
