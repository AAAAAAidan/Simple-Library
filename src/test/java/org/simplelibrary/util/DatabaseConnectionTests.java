package org.simplelibrary.util;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class DatabaseConnectionTests extends DatabaseConnection {

  @Test
  @Order(1)
  void testConnect() {
    this.connect();
    assertNotNull(entityManager);
  }

  @Test
  @Order(1)
  void testDisconnect() {
    this.disconnect();
    assertNull(entityManager);
  }

}
