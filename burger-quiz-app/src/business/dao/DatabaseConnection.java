package business.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection{

	/**
	 * Database information
	 */
	private static String url = new String("jdbc:mysql://localhost:3306/burgerquiz");
	private static String user = "burgerquiz";
	private static String password = "burgerquiz";

	/**
	 * Hold our active connection
	 */
	private static Connection conn;


	/**
	 * Create or return the active connection
	 * @return conn
	 */
	public static Connection getInstance(){		
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(url, user, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	

		return conn;	
	}	

}