package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Account;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.repository.AccountRepository;
import org.simplelibrary.security.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class AccountService {

  private final AccountRepository accountRepository;
  private final AuthGroupService authGroupService;
  private final FileService fileService;

  @Autowired
  public AccountService(AccountRepository accountRepository,
                        AuthGroupService authGroupService,
                        FileService fileService) {
    this.accountRepository = accountRepository;
    this.authGroupService = authGroupService;
    this.fileService = fileService;
  }

  public Account getByEmail(String email) {
    return accountRepository.getByEmail(email);
  }

  public Account getLoggedInAccount() {
    return accountRepository.getById(getLoggedInId());
  }

  public AccountDetails getLoggedInDetails() {
    return (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public String getLoggedInEmail() {
    return getLoggedInDetails().getUsername();
  }

  public Integer getLoggedInId() {
    return getLoggedInDetails().getId();
  }

  public void signUp(String email, String password) {
    Account account = new Account();
    account.setEmail(email);

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
    String encodedPassword = passwordEncoder.encode(password);
    account.setPassword(encodedPassword);

    List<AuthGroup> authGroups = new ArrayList<>();
    authGroups.add(authGroupService.getByName("ROLE_USER"));
    account.setAuthGroups(authGroups);
    accountRepository.save(account);
  }

  public void logIn(HttpServletRequest request, String email, String password) {
    try {
      request.login(email, password);
    }
    catch (ServletException e) {
      e.printStackTrace();
    }
  }

  public void saveProfilePicture(MultipartFile file) {
    String newFilename = "account-" + getLoggedInId() + ".png";
    fileService.saveAs(file, newFilename);
  }

}
