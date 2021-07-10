package org.closedlibrary.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
	
	private static final String URL = "jdbc:mariadb://127.0.0.1/closedlibrary";
	private static Connection connection = null;
	
	public static Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				connection = DriverManager.getConnection(URL, "root", "Password");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return connection;
	}
	
}
