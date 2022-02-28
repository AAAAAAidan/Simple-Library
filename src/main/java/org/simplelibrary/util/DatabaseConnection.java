package org.simplelibrary.util;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

/**
 * Utility class for connecting JPQL to the database.
 * @deprecated due to unresolved issues with entity managers.
 */
@Slf4j
@Deprecated(since="02/26/2022")
public abstract class DatabaseConnection {

  protected static EntityManagerFactory entityManagerFactory;
  protected static EntityManager entityManager;

  @Autowired
  DatabaseService databaseService;

  /**
   * Connects to the database.
   */
  public void connect() {
    if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
      String database = databaseService.getDatabase();
      Properties properties = databaseService.getProperties();
      entityManagerFactory = Persistence.createEntityManagerFactory(database, properties);
      log.info("Opened entity manager factory");
    }

    if (entityManager == null || !entityManager.isOpen()) {
      entityManager = entityManagerFactory.createEntityManager();
      log.info("Opened entity manager " + entityManagerFactory.isOpen() + " " + entityManager.isOpen());
    }
  }

  /**
   * Disconnects from the database.
   */
  public void disconnect() {
    if (entityManager.isOpen()) {
      entityManager.close();
      log.info("Closed entity manager factory");
    }

    if (entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
      log.info("Closed entity manager");
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
