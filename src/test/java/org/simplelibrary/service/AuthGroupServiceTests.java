package org.simplelibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.simplelibrary.model.AuthGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthGroupServiceTests {

  @Autowired
  private AuthGroupService authGroupService;

  @Test
  void testGetByName() {
    String role = "ROLE_ADMIN";
    AuthGroup authGroup = authGroupService.getByName(role);
    assertEquals(authGroup.getName(), role);
  }

}
