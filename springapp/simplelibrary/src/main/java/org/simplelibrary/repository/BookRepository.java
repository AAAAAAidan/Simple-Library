package org.simplelibrary.repository;

import org.simplelibrary.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository {
  
  public Book findById(String id);
  
}
