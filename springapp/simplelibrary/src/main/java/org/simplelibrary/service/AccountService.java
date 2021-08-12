package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Account;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.repository.AccountRepository;
import org.simplelibrary.util.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountService {

  private Table<Account> accountTable = new Table<>(Account.class);
  private AccountRepository accountRepository;
  private AuthGroupService authGroupService;

  @Autowired
  public AccountService(AccountRepository accountRepository, AuthGroupService authGroupService) {
    this.accountRepository = accountRepository;
    this.authGroupService = authGroupService;
  }

  public Account getAccountByEmail(String email) {
    String filter = "account_email = " + email;
    return accountTable.filterBy(filter).selectOne();
  }

  public void addAccount(String email, String password) {
    Account account = new Account();
    account.setEmail(email);

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
    String encodedPassword = passwordEncoder.encode(password);
    account.setPassword(encodedPassword);

    List<AuthGroup> authGroups = new ArrayList<>();
    Optional<AuthGroup> authGroup = authGroupService.findAuthGroupById(1);

    if (authGroup.isPresent()) {
      authGroups.add(authGroup.get());
      account.setAuthGroups(authGroups);
    }

    accountRepository.save(account);
  }
}
