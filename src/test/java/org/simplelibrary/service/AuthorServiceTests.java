package org.simplelibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Testing class for author services.
 */
@Slf4j
@SpringBootTest
class AuthorServiceTests {

  @Autowired
  private AuthorService authorService;

  @Test
  void test() {
    fail("Not yet implemented");
  }

}
