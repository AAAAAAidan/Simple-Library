package org.simplelibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.simplelibrary.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Testing class for account services.
 */
@Slf4j
@SpringBootTest
class AccountServiceTests {

  @Autowired
  private AccountService accountService;

  @Test
  void testGetByEmail() {
    String email = "user@mail.com";
    Account realAccount = accountService.getByEmail(email);
    assertEquals(realAccount.getEmail(), email);

    Account fakeAccount = accountService.getByEmail("junk");
    assertNull(fakeAccount);
  }

}
