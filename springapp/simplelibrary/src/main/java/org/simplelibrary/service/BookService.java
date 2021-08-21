package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Book;
import org.simplelibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookService {

  private BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public void save(Book book) {
    bookRepository.save(book);
  }

  public void saveAll(List<Book> books) {
    bookRepository.saveAll(books);
  }

  public boolean existsByName(String name) {
    return bookRepository.existsByName(name);
  }

  public Book getById(Integer id) {
    return bookRepository.getById(id);
  }

  public Book getByName(String name) {
    return bookRepository.getByName(name);
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

}
