package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Book;
import org.simplelibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class BookService {

  private BookRepository bookRepository;
  private FileService fileService;

  @Autowired
  public BookService(BookRepository bookRepository,
                     FileService fileService) {
    this.bookRepository = bookRepository;
    this.fileService = fileService;
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

  public Book getByRandom() {
    List<Book> books = bookRepository.findAll();
    Random random = new Random();
    return books.get(random.nextInt(books.size()));
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public String getCoverPath(Integer id) {
    String filename = "cover-" + id + ".png";
    return fileService.getSrc(filename);
  }

  public String getReaderPath(Integer id) {
    String filename = "reader-" + id + ".epub";
    return fileService.getSrc(filename);
  }

  public String getReaderName(Integer id) {
    String filename = "reader-" + id + ".epub";

    if (!fileService.exists(filename)) {
      filename = filename.replaceAll("-.*\\.", "-default.");
    }

    return filename;
  }

}
