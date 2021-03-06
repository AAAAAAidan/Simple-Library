package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Account;
import org.simplelibrary.model.AccountDetails;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for user details authentication.
 */
@Service
@Slf4j
public class AccountDetailsService implements UserDetailsService {

  private final AccountRepository accountRepository;

  public AccountDetailsService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  /**
   * Loads user details based on the provided email.
   *
   * @param email the email of the desired user
   * @return the user details found
   * @throws UsernameNotFoundException if no user is found
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = accountRepository.getByEmail(email);

    if (account == null) {
      throw new UsernameNotFoundException("No account found for " + email);
    }

    List<AuthGroup> authGroups = account.getAuthGroups();
    return new AccountDetails(account, authGroups);
  }

}
