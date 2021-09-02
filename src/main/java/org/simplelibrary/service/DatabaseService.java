package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Slf4j
@Service
public class DatabaseService {

  @Value("${database}")
  private String database;

  @Value("${spring.cloud.gcp.sql.instance-connection-name:null}")
  private String instance;

  @Value("${spring.datasource.driver-class-name}")
  private String driver;
  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String user;
  @Value("${spring.datasource.password}")
  private String password;

  private final Properties properties = new Properties();

  @PostConstruct
  public void init() {
    log.info(driver);
    log.info(url);

    if (!instance.equals("null")) {
      driver = "com.google.cloud.sql.Driver";
      url = "jdbc:google:rdbms://" + instance + "/" + database;
    }

    properties.setProperty("javax.persistence.jdbc.driver", driver);
    properties.setProperty("javax.persistence.jdbc.url", url);
    properties.setProperty("javax.persistence.jdbc.user", user);
    properties.setProperty("javax.persistence.jdbc.password", password);

    log.info(driver);
    log.info(url);
  }

  public String getDatabase() {
    this.init();
    return database;
  }

  public Properties getProperties() {
    this.init();
    return properties;
  }

}
