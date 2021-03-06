package org.simplelibrary.repository;

import org.simplelibrary.model.Subject;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for the subject database table.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

  public Subject getByName(String name);
  public List<Subject> getSubjectsByNameIsContainingIgnoreCase(String name, Sort sort);
  public boolean existsByName(String name);

}
