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

/**
* Service class for the account entity.
*/
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

  /////////////////////
  // Account getters //
  /////////////////////

  /**
   * Checks if the current user is logged in.
   *
   * @return true if the user logged in, else false
   */
  public boolean isLoggedIn() {
    return SecurityContextHolder.getContext().getAuthentication() != null &&
           SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
           !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
  }

  /**
   * Gets user details from the logged in user.
   *
   * @return the user details of the logged in user
   */
  public AccountDetails getLoggedInDetails() {
    return (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  /**
   * Gets the ID of the logged in user.
   *
   * @return the ID of the logged in user
   */
  public Integer getLoggedInId() {
    return getLoggedInDetails().getId();
  }

  /**
   * Gets the email of the logged in user.
   *
   * @return the email of the logged in user
   */
  public String getLoggedInEmail() {
    return getLoggedInDetails().getUsername();
  }

  /**
   * Gets the password of the logged in user.
   *
   * @return the password of the logged in user
   */
  public String getLoggedInPassword() {
    return getLoggedInDetails().getPassword();
  }

  /**
   * Gets the account of the logged in user.
   *
   * @return the account of the logged in user
   */
  public Account getLoggedInAccount() {
    return accountRepository.getById(getLoggedInId());
  }

  /**
   * Gets an account based on the provided email.
   *
   * @param email the email of the desired account
   * @return the account found
   */
  public Account getByEmail(String email) {
    return accountRepository.getByEmail(email);
  }

  ////////////////////////
  // Account validators //
  ////////////////////////

  /**
   * Gets any errors based on the provided email.
   *
   * @param email the email to check
   * @return a list of any errors found
   */
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

  /**
   * Gets any errors based on the provided passwords.
   *
   * @param password the password to check
   * @param passwordConfirm the confirmation password to check
   * @return a list of any errors found
   */
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

  /**
   * Gets any validation errors based on the provided password.
   *
   * @param password the password to verify
   * @return a list of any validation errors found
   */
  public List<String> getPasswordValidationErrors(String password) {
    List<String> passwordValidationErrors = new ArrayList<>();

    if (!passwordEncoder.matches(password, getLoggedInPassword())) {
      passwordValidationErrors.add("The password entered is incorrect!");
    }

    return passwordValidationErrors;
  }

  /**
   * Gets any errors based on the provided file.
   *
   * @param file the file to check
   * @return a list of any errors found
   */
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

  //////////////////////
  // Account updaters //
  //////////////////////

  /**
   * Saves and returns an account.
   *
   * @param account the account to save
   * @return the saved account
   */
  public Account saveAndFlush(Account account) {
    return accountRepository.saveAndFlush(account);
  }

  /**
   * Saves a new email to the current authentication.
   *
   * @param email the email to use in authentication
   */
  public void saveAuthentication(String email) {
    AccountDetails accountDetails = (AccountDetails) accountDetailsService.loadUserByUsername(email);
    Authentication authentication = new UsernamePasswordAuthenticationToken(accountDetails, accountDetails.getPassword(), accountDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  /**
   * Saves a new account with default authorities.
   *
   * @param email the email to save to the account
   * @param password the password to encode and save to the account
   */
  public void saveAccount(String email, String password) {
    List<AuthGroup> authGroups = new ArrayList<>();
    authGroups.add(authGroupService.getByName("ROLE_USER"));
    Account account = new Account();
    account.setEmail(email);
    account.setPassword(passwordEncoder.encode(password));
    account.setAuthGroups(authGroups);
    accountRepository.save(account);
  }

  /**
   * Saves a new email to the logged in account.
   *
   * @param email the email to save to the account
   */
  public void saveEmail(String email) {
    Account account = getLoggedInAccount();
    account.setEmail(email);
    accountRepository.save(account);
  }

  /**
   * Saves a new password to the logged in account.
   *
   * @param password the password to save to the account
   */
  public void savePassword(String password) {
    Account account = getLoggedInAccount();
    String encodedPassword = passwordEncoder.encode(password);
    account.setPassword(encodedPassword);
    accountRepository.save(account);
  }

  /**
   * Saves a new profile picture to the logged in account.
   *
   * @param file the profile picture to save to the account
   */
  public void saveProfilePicture(MultipartFile file) {
    String newFilename = "account-" + getLoggedInId() + ".png";
    fileService.saveAs(file, newFilename);
  }

}
