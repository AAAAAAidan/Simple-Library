package org.simplelibrary.repository;

import org.simplelibrary.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

  public Author getByName(String name);
  public boolean existsByName(String name);

}
