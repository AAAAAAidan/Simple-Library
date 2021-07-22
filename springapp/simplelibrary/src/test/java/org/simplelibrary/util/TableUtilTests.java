package org.simplelibrary.util;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.simplelibrary.model.Book;

class TableUtilTests extends DatabaseConnection {

	private static Table<Book> table = new Table<Book>(Book.class);

	@Test
	@Order(1)
	void testSelect() throws SQLException {
    	List<Book> results = table.select("bookId");
    	Book book = results.get(0);
    	System.out.println(book.getBookId());
		assertNotNull(book.getBookId());
	}

//	@Test
//	@Order(2)
//	void testInsert() {
//	}
//
//	@Test
//	@Order(3)
//	void testUpdate() {
//	}
//
//	@Test
//	@Order(4)
//	void testDelete() {
//	}

}
