package org.simplelibrary.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnection {
	
	protected EntityManagerFactory entityManagerFactory = null;
	protected EntityManager entityManager = null;
	protected Connection connection = null;
	
    private final String DATABASE = "library";
	private final String URL = "jdbc:mysql://127.0.0.1/" + DATABASE;
	private final String USER = "root";
	private final String PASSWORD = "Password";
	
	// Connect to the database
	public void connect() {
		entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE);
		entityManager = entityManagerFactory.createEntityManager();
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Disconnect from the database
	public void disconnect() {
		if (entityManager != null) {
			entityManager.close();
		}
		
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
		
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
