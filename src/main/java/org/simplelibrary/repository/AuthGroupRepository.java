package org.simplelibrary.repository;

import org.simplelibrary.model.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for the authority group database table.
 */
@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {

  public AuthGroup getByName(String name);

}
