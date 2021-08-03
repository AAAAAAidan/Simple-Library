package org.simplelibrary.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.simplelibrary.model.Book;

class TableUtilTests {

  private static Table<Book> table = new Table<Book>(Book.class);

  @Test
  @Order(1)
  void testSelect() {
    List<Book> books = table.filter("bookId=Wads4igrTJAC").orderBy("bookId").sort("dEsC").limit(1)
        .select();
    Book book = books.size() == 0 ? null : books.get(0);
      System.out.println(book.getId());
    assertNotNull(book.getId());
  }

  @Test
  @Order(2)
  void testInsert() {
    Book book = new Book();
    book.setId("new id");
    book.setTitle("new title");
    int insertCount = table.insert(book);
    assertTrue(insertCount > 0);
  }

  @Test
  @Order(3)
  void testUpdate() {
    int updateCount = table.filter("bookId=Wads4igrTJAC").orderBy("bookId").sort("dEsC").limit(1)
        .update("bookIdentifiers", "CHANGEME!!!!");
    assertTrue(updateCount > 0);
  }

  @Test
  @Order(4)
  void testDelete() {
    int deleteCount = table.filter("bookId=Wads4igrTJAC").orderBy("bookId").sort("dEsC").limit(1)
        .delete();
    assertTrue(deleteCount > 0);
  }

}
