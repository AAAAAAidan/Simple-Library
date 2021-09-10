package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Account;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.repository.AccountRepository;
import org.simplelibrary.security.AccountDetails;
import org.simplelibrary.security.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class AccountService {

  private final AccountRepository accountRepository;
  private final AccountDetailsService accountDetailsService;
  private final AuthGroupService authGroupService;
  private final FileService fileService;

  @Autowired
  public AccountService(AccountRepository accountRepository,
                        AccountDetailsService accountDetailsService,
                        AuthGroupService authGroupService,
                        FileService fileService) {
    this.accountRepository = accountRepository;
    this.accountDetailsService = accountDetailsService;
    this.authGroupService = authGroupService;
    this.fileService = fileService;
  }

  public Account getByEmail(String email) {
    return accountRepository.getByEmail(email);
  }

  public List<String> getEmailMessages(String email) {
    List<String> emailMessages = new ArrayList<>();

    if (getByEmail(email) != null) {
      emailMessages.add("That email is already taken!");
    }

    if (!Pattern.matches("[A-Za-z0-9]+@+[A-Za-z0-9]+.+[A-Za-z]+", email)) {
      emailMessages.add("The email must contain a valid email address!");
    }

    return emailMessages;
  }

  public List<String> getPasswordMessages(String password, String passwordConfirm) {
    List<String> passwordMessages = new ArrayList<>();

    if (!password.equals(passwordConfirm)) {
      passwordMessages.add("The passwords entered do not match!");
    }

    if (password.length() < 8) {
      passwordMessages.add("The password must contain 8 or more characters!");
    }

    return passwordMessages;
  }

  public Account getLoggedInAccount() {
    return accountRepository.getById(getLoggedInId());
  }

  public AccountDetails getLoggedInDetails() {
    return (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public Integer getLoggedInId() {
    return getLoggedInDetails().getId();
  }

  public String getLoggedInEmail() {
    return getLoggedInDetails().getUsername();
  }

  public String getLoggedInPassword() {
    return getLoggedInDetails().getPassword();
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

  public void saveAuthentication(String email) {
    AccountDetails accountDetails = (AccountDetails) accountDetailsService.loadUserByUsername(email);
    Authentication authentication = new UsernamePasswordAuthenticationToken(accountDetails, accountDetails.getPassword(), accountDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  public void saveProfilePicture(MultipartFile file) {
    String newFilename = "account-" + getLoggedInId() + ".png";
    fileService.saveAs(file, newFilename);
  }

  public void saveEmail(String email) {
    Account account = getLoggedInAccount();
    account.setEmail(email);
    accountRepository.save(account);
  }

  public void savePassword(String password) {
    Account account = getLoggedInAccount();
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
    String encodedPassword = passwordEncoder.encode(password);
    account.setPassword(encodedPassword);
    accountRepository.save(account);
  }

}
