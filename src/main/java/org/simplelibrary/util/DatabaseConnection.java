package org.simplelibrary.util;

import org.simplelibrary.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

/**
 * Utility class for connecting JPQL to the database.
 */
public abstract class DatabaseConnection {

  protected EntityManagerFactory entityManagerFactory;
  protected EntityManager entityManager;

  @Autowired
  DatabaseService databaseService;

  /**
   * Connects to the database.
   */
  public void connect() {
    String database = databaseService.getDatabase();
    Properties properties = databaseService.getProperties();
    entityManagerFactory = Persistence.createEntityManagerFactory(database, properties);
    entityManager = entityManagerFactory.createEntityManager();
  }

  /**
   * Disconnects from the database.
   */
  public void disconnect() {
    if (entityManager != null) {
      entityManager.close();
    }

    if (entityManagerFactory != null) {
      entityManagerFactory.close();
    }
  }

  /**
   * Connects and begins database transaction.
   */
  public void begin() {
    this.connect();
    entityManager.getTransaction().begin();
  }

  /**
   * Commits database transaction and disconnects.
   */
  public void commit() {
    entityManager.getTransaction().commit();
    this.disconnect();
  }

  /**
   * Persists an object to the database.
   *
   * @param object the object to persist
   */
  public void persist(Object object) {
    entityManager.persist(object);
  }

}
