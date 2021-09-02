package org.simplelibrary.util;

import org.simplelibrary.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public abstract class DatabaseConnection {

  protected EntityManagerFactory entityManagerFactory;
  protected EntityManager entityManager;

  @Autowired
  DatabaseService databaseService;

  // Connect to the database
  public void connect() {
    String database = databaseService.getDatabase();
    Properties properties = databaseService.getProperties();
    entityManagerFactory = Persistence.createEntityManagerFactory(database, properties);
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
