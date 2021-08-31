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

  @Value("${driver}")
  private String driver;
  @Value("${url}")
  private String url;
  @Value("${user}")
  private String user;
  @Value("${password}")
  private String password;

  private final Properties properties = new Properties();

  @PostConstruct
  public void init() {
    properties.setProperty("javax.persistence.jdbc.driver", driver);
    properties.setProperty("javax.persistence.jdbc.url", url);
    properties.setProperty("javax.persistence.jdbc.user", user);
    properties.setProperty("javax.persistence.jdbc.password", password);
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
