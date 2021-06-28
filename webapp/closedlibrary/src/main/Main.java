package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import database.Table;

public class Main {
	
	public static Connection connection = null;
	public static ResultSet result = null;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("org.mariadb.jdbc.Driver");
		
		String url = "jdbc:mariadb://127.0.0.1/closedlibrary";
		String user = "root";
		String password = "Password";
		
		Connection connection = DriverManager.getConnection(url, user, password);
		Table bookTable = new Table(connection, "book");
		
		int inserts = bookTable.insert(new String[] {"bookId", "bookTitle"}, new String[] {"abcabcabcabc", "title"});
		System.out.println(inserts + " rows inserted.");
		int updates = bookTable.filter("bookId='abcabcabcabc'").update("bookTitle", "new title");
		System.out.println(updates + " rows updated.");
		
		ResultSet result = bookTable.sort("bookTitle").order("descenDING").range(1, 4).select();
		ResultSetMetaData metadata = result.getMetaData();
		String col = metadata.getColumnName(2);
		System.out.println(col);
		
		while (result.next()) {
			String value = result.getString(2);
			System.out.println(value);
		}
		
		int deletes = bookTable.filter("bookId='abcabcabcabc'").delete();
		System.out.println(deletes + " rows deleted.");
		
	}
	
}
