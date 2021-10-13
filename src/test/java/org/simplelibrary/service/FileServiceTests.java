package org.simplelibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Testing class for file services.
 */
@Slf4j
@SpringBootTest
class FileServiceTests {

  @Autowired
  private FileService fileService;

  @Test
  void testExists() {
    assertTrue(fileService.exists("account-default.png"));
    assertFalse(fileService.exists("junk"));
  }

}
