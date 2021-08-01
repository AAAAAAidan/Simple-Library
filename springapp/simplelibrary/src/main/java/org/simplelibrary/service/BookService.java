package org.simplelibrary.service;

import org.simplelibrary.model.Book;
import org.simplelibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService implements BookRepository {
  
  public Book findById(String id) {
    Book book = new Book();
    book.setBookTitle("Title");
    book.setBookDescription("Description");
    return book;
  }
  
}
