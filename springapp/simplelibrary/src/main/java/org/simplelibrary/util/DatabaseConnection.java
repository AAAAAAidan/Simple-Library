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

  private final String DATABASE = "library";

  // Connect to the database
  public void connect() {
    entityManagerFactory = Persistence.createEntityManagerFactory(DATABASE);
    entityManager = entityManagerFactory.createEntityManager();
  }
  
  // Disconnect from the database
  public void disconnect() {
    if (entityManager != null) {
      entityManager.close();
    }
    
    if (entityManagerFactory != null) {
      entityManagerFactory.close();
    }
  }

  // Connect and begin transaction
  public void begin() {
    this.connect();
    entityManager.getTransaction().begin();
  }

  // Commit transaction and disconnect
  public void commit() {
    entityManager.getTransaction().commit();
    this.disconnect();
  }

  // Persist an object to the database
  public void persist(Object object) {
    entityManager.persist(object);
  }

}
