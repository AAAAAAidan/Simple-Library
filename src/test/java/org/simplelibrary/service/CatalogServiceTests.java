package org.simplelibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Testing class for catalog services.
 */
@Slf4j
@SpringBootTest
class CatalogServiceTests {

  @Autowired
  private CatalogService catalogService;

  @Test
  void test() {
    fail("Not yet implemented");
  }

}
