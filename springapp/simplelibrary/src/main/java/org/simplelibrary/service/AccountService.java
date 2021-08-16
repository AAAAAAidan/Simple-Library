package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Account;
import org.simplelibrary.model.AuthGroup;
import org.simplelibrary.repository.AccountRepository;
import org.simplelibrary.security.AccountDetails;
import org.simplelibrary.util.Table;
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
import java.util.Optional;

@Slf4j
@Service
public class AccountService {

  @Value("${upload.path}")
  private String uploadPath;

  private AccountRepository accountRepository;
  private AuthGroupService authGroupService;
  private FileService fileService;

  @Autowired
  public AccountService(AccountRepository accountRepository, AuthGroupService authGroupService, FileService fileService) {
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
