package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Account;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.repository.AccountRepository;
import org.simplelibrary.security.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class AccountService {

  @Value("${upload.path}")
  private String uploadPath;

  private AccountRepository accountRepository;
  private AuthGroupService authGroupService;
  private FileService fileService;

  @Autowired
  public AccountService(AccountRepository accountRepository,
                        AuthGroupService authGroupService,
                        FileService fileService) {
    this.accountRepository = accountRepository;
    this.authGroupService = authGroupService;
    this.fileService = fileService;
  }

  public Account getAccountByEmail(String email) {
    return accountRepository.getAccountByEmail(email);
  }

  public Integer getLoggedInAccountId() {
    AccountDetails accountDetails = (AccountDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return accountDetails.getId();
  }

  public void signUpAccount(String email, String password) {
    Account account = new Account();
    account.setEmail(email);

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
    String encodedPassword = passwordEncoder.encode(password);
    account.setPassword(encodedPassword);

    List<AuthGroup> authGroups = new ArrayList<>();
    AuthGroup authGroup = authGroupService.getAuthGroupByName("ROLE_USER");

    if (authGroup != null) {
      authGroups.add(authGroup);
      account.setAuthGroups(authGroups);
    }

    accountRepository.save(account);
  }

  public void logInAccount(HttpServletRequest request, String email, String password) {
    try {
      request.login(email, password);
    }
    catch (ServletException e) {
      log.warn(e.toString());
    }
  }

  public void uploadProfilePicture(MultipartFile file) {
    String newFileName = "account-" + getLoggedInAccountId() + ".png";
    fileService.saveAs(file, newFileName);
  }

  public String getProfilePicturePath() {
    String fileName = "account-" + getLoggedInAccountId() + ".png";
    Path filePath = Paths.get(uploadPath + File.separator + fileName);

    if (!Files.exists(filePath)) {
      fileName = fileName.replace(String.valueOf(getLoggedInAccountId()), "default");
    }

    return "files" + File.separator + fileName;
  }
}
