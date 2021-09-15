package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Account;
import org.simplelibrary.model.AccountDetails;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);

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

  // Account getters

  public boolean isLoggedIn() {
    return SecurityContextHolder.getContext().getAuthentication() != null &&
           SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
           !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
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

  public Account getLoggedInAccount() {
    return accountRepository.getById(getLoggedInId());
  }

  public Account getByEmail(String email) {
    return accountRepository.getByEmail(email);
  }

  // Account validators

  public List<String> getEmailErrors(String email) {
    List<String> emailErrors = new ArrayList<>();

    if (getByEmail(email) != null) {
      emailErrors.add("That email is already taken!");
    }

    if (!Pattern.matches("[A-Za-z0-9]+@+[A-Za-z0-9]+.+[A-Za-z]+", email)) {
      emailErrors.add("The email must contain a valid email address!");
    }

    return emailErrors;
  }

  public List<String> getPasswordErrors(String password, String passwordConfirm) {
    List<String> passwordErrors = new ArrayList<>();

    if (!password.equals(passwordConfirm)) {
      passwordErrors.add("The passwords entered do not match!");
    }

    if (password.length() < 8) {
      passwordErrors.add("The password must contain 8 or more characters!");
    }

    return passwordErrors;
  }

  public List<String> getPasswordValidationErrors(String password) {
    List<String> passwordValidationErrors = new ArrayList<>();

    if (!passwordEncoder.matches(password, getLoggedInPassword())) {
      passwordValidationErrors.add("The password entered is incorrect!");
    }

    return passwordValidationErrors;
  }

  public List<String> getFileErrors(MultipartFile file) {
    List<String> emailErrors = new ArrayList<>();

    if (!Pattern.matches(".*.(png|jpg|jpeg)", file.getOriginalFilename())) {
      emailErrors.add("The file must be .png or .jpg!");
    }

    if (file.getSize() > 5000000L) {
      emailErrors.add("The file size must be 5 megabytes or less!");
    }

    return emailErrors;
  }

  // Account updaters

  public Account saveAndFlush(Account account) {
    return accountRepository.saveAndFlush(account);
  }

  public void saveAuthentication(String email) {
    AccountDetails accountDetails = (AccountDetails) accountDetailsService.loadUserByUsername(email);
    Authentication authentication = new UsernamePasswordAuthenticationToken(accountDetails, accountDetails.getPassword(), accountDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  public void saveAccount(String email, String password) {
    List<AuthGroup> authGroups = new ArrayList<>();
    authGroups.add(authGroupService.getByName("ROLE_USER"));
    Account account = new Account();
    account.setEmail(email);
    account.setPassword(passwordEncoder.encode(password));
    account.setAuthGroups(authGroups);
    accountRepository.save(account);
  }

  public void saveEmail(String email) {
    Account account = getLoggedInAccount();
    account.setEmail(email);
    accountRepository.save(account);
  }

  public void savePassword(String password) {
    Account account = getLoggedInAccount();
    String encodedPassword = passwordEncoder.encode(password);
    account.setPassword(encodedPassword);
    accountRepository.save(account);
  }

  public void saveProfilePicture(MultipartFile file) {
    String newFilename = "account-" + getLoggedInId() + ".png";
    fileService.saveAs(file, newFilename);
  }

}
