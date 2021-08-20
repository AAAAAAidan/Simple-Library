package org.simplelibrary.security;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Account;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountDetailsService implements UserDetailsService {

  private AccountRepository accountRepository;

  public AccountDetailsService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = accountRepository.getAccountByEmail(email);

    if (account == null) {
      throw new UsernameNotFoundException("No account found for " + email);
    }

    List<AuthGroup> authGroups = account.getAuthGroups();
    return new AccountDetails(account, authGroups);
  }

}
