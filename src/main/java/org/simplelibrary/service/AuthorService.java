package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Author;
import org.simplelibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service class for the author entity.
 */
@Slf4j
@Service
public class AuthorService {

  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  /**
   * Saves an author.
   *
   * @param author the author to save
   */
  public void save(Author author) {
    authorRepository.save(author);
  }

  /**
   * Saves all authors provided.
   *
   * @param authors the authors to save
   */
  public void saveAll(List<Author> authors) {
    authorRepository.saveAll(authors);
  }

  /**
   * Checks if an author with the provided name exists.
   *
   * @param name the name of the author
   * @return true if the author exists, else false
   */
  public boolean existsByName(String name) {
    return authorRepository.existsByName(name);
  }

  /**
   * Gets a author based on the provided ID.
   *
   * @param id the ID of the author
   * @return the author found
   */
  public Author getById(Integer id) {
    return authorRepository.getById(id);
  }

  /**
   * Gets a author based on the provided name.
   *
   * @param name the name of the author
   * @return the author found
   */
  public Author getByName(String name) {
    return authorRepository.getByName(name);
  }

  /**
   * Gets a list of authors based on the provided name.
   *
   * @param name the name to use as a search term
   * @param sort the sort order for the results
   * @return the authors found
   */
  public List<Author> getAuthorsByNameIsContainingIgnoreCase(String name, Sort sort) {
    return authorRepository.getAuthorsByNameIsContainingIgnoreCase(name, sort);
  }

  /**
   * Gets a random author.
   *
   * @return the author found
   */
  public Author getByRandom() {
    List<Author> authors = authorRepository.findAll();
    Random random = new Random();
    return authors.get(random.nextInt(authors.size()));
  }

  /**
   * Finds all authors.
   *
   * @return the authors found
   */
  public List<Author> findAll() {
    return authorRepository.findAll();
  }

  /**
   * Adds a book to an author's list of books if not already present.
   *
   * @param author the author to save
   * @param book the book to save to the author
   */
  public void mapToBook(Author author, Book book) {
    List<Book> books = author.getBooks();
    boolean alreadyPresent = false;

    if (books == null) {
      books = new ArrayList<>();
    }

    for (Book b : books) {
      if (b.getName().equals(book.getName())) {
        alreadyPresent = true;
        break;
      }
    }

    if (!alreadyPresent) {
      books.add(book);
    }

    author.setBooks(books);
    authorRepository.save(author);
  }

}
