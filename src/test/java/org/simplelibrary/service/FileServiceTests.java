package org.simplelibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
