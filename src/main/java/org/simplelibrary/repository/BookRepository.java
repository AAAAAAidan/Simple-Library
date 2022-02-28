package org.simplelibrary.repository;

import org.simplelibrary.model.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for the book database table.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

  public Book getByName(String name);
  public List<Book> getBooksByNameIsContainingIgnoreCase(String name, Sort sort);
  public boolean existsByName(String name);

}
