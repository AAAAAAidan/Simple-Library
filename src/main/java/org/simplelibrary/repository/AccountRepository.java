package org.simplelibrary.repository;

import org.simplelibrary.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for the account database table.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  public Account getByEmail(String email);

}
