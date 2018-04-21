package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbConnectionPool {
	private static ArrayList<Connection> connections = new ArrayList<Connection>();
	private static String CONNECTION_STRING = "jdbc:google:mysql://ppmutestapp:us-central1:products/productdata?user=root&password=root";
	
	static {
		createConnection();

	}
	
	public static void returnConnection(Connection conn) {
		connections.add(conn);
	}
	public static Connection getConnection() {
		if(connections.size() == 0) {
			createConnection();
		}
		Connection conn = connections.get(0);
		connections.remove(0);
		return conn;
	}
	
	private static void createConnection() {
		try {
			connections.add(DriverManager.getConnection(CONNECTION_STRING));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
