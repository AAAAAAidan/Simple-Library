package org.simplelibrary.repository;

import org.simplelibrary.model.Isbn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IsbnRepository extends JpaRepository<Isbn, String> {

}
