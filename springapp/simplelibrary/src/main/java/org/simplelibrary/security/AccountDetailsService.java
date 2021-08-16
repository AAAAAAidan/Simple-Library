package org.simplelibrary.security;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.model.Account;
import org.simplelibrary.service.AccountService;
import org.simplelibrary.service.AuthGroupService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountDetailsService implements UserDetailsService {

  private AccountService accountService;
  private AuthGroupService authGroupService;

  public AccountDetailsService(AccountService accountService, AuthGroupService authGroupService) {
    this.accountService = accountService;
    this.authGroupService = authGroupService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = accountService.getAccountByEmail(email);

    if (account == null) {
      throw new UsernameNotFoundException("No account found for " + email);
    }

    List<AuthGroup> authGroups = account.getAuthGroups();
    return new AccountDetails(account, authGroups);
  }

}
