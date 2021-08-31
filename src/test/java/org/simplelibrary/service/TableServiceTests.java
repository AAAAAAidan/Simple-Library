package org.simplelibrary.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.simplelibrary.model.Book;
import org.simplelibrary.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TableServiceTests {

  @Autowired
  private TableService tableService;

  @Test
  @Order(1)
  void testSelect() {
    List<Book> books = tableService.filterBy("bookId=1").sortBy("bookId").inOrder("dEsC").select(Book.class);
    assertTrue(books.size() > 3);

    Book book = tableService.filterBy("bookId=1").selectOne(Book.class);
    assertNotNull(book);
  }

  @Test
  @Order(2)
  void testInsert() {
    Book book = new Book("new title");
    int insertCount = tableService.insert(book);
    assertTrue(insertCount > 0);
  }

  @Test
  @Order(3)
  void testUpdate() {
    int updateCount = tableService.filterBy("bookId=1").sortBy("bookId").inOrder("dEsC").limitTo(1).update(Book.class, "bookIdentifiers", "CHANGEME!!!!");
    assertTrue(updateCount > 0);
  }

  @Test
  @Order(4)
  void testDelete() {
    int deleteCount = tableService.filterBy("bookId=1").sortBy("bookId").inOrder("dEsC").limitTo(1).delete(Book.class);
    assertTrue(deleteCount > 0);
  }

}
