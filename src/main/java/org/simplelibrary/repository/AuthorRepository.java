package org.simplelibrary.repository;

import org.simplelibrary.model.Author;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for the author database table.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

  public Author getByName(String name);
  public List<Author> getAuthorsByNameIsContainingIgnoreCase(String name, Sort sort);
  public boolean existsByName(String name);

}
