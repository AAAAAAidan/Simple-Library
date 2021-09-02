package org.simplelibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.simplelibrary.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class HomeServiceTests {

  @Autowired
  private HomeService homeService;

  @Test
  void testGetSearchResults() {
    List<Account> accounts = (List<Account>) homeService.getSearchResults("", "books", "");
    assertTrue(accounts.size() >= 10);
  }

}
