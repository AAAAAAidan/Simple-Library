package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.service.AccountService;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class AccountController extends TemplateView {

  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/account")
  public String getAccount(Model model) {
    model.addAttribute("email", accountService.getLoggedInEmail());
    return loadView(model, "accounts/account");
  }

  @PostMapping("/account/profilepicture")
  public String postProfilePicture(RedirectAttributes redirectAttributes,
                                   @RequestParam MultipartFile file) {

    String filename = file.getOriginalFilename();
    List<String> fileErrors = accountService.getFileErrors(file);

    if (fileErrors.isEmpty()) {
      accountService.saveProfilePicture(file);
      redirectAttributes.addFlashAttribute("successMessage", "Your profile picture has been saved!");
    }
    else {
      redirectAttributes.addFlashAttribute("fileErrors", fileErrors);
    }

    return "redirect:/account";
  }

  @PostMapping("/account/email")
  public String postEmail(Model model,
                          RedirectAttributes redirectAttributes,
                          @RequestParam String email) {

    List<String> emailErrors = accountService.getEmailErrors(email);

    if (emailErrors.isEmpty()) {
      accountService.saveEmail(email);
      accountService.saveAuthentication(email);
      redirectAttributes.addFlashAttribute("successMessage", "Your email has been saved!");
    }
    else {
      redirectAttributes.addFlashAttribute("emailNew", email);
      redirectAttributes.addFlashAttribute("emailErrors", emailErrors);
    }

    return "redirect:/account";
  }

  @PostMapping("/account/password")
  public String postPassword(Model model,
                             RedirectAttributes redirectAttributes,
                             @RequestParam String passwordOld,
                             @RequestParam String passwordNew,
                             @RequestParam String passwordConfirm) {

    List<String> passwordValidationErrors = accountService.getPasswordValidationErrors(passwordOld);
    List<String> passwordErrors = accountService.getPasswordErrors(passwordNew, passwordConfirm);

    if (passwordValidationErrors.isEmpty() && passwordErrors.isEmpty()) {
      accountService.savePassword(passwordNew);
      accountService.saveAuthentication(accountService.getLoggedInEmail());
      redirectAttributes.addFlashAttribute("successMessage", "Your password has been saved!");
    }
    else {
      redirectAttributes.addFlashAttribute("passwordValidationErrors", passwordValidationErrors);
      redirectAttributes.addFlashAttribute("passwordErrors", passwordErrors);
    }

    return "redirect:/account";
  }

  @GetMapping("/signup")
  public String getSignup(Model model) {
    return loadView(model, "accounts/signup");
  }

  @PostMapping("/signup")
  public String postSignup(Model model,
                           RedirectAttributes redirectAttributes,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String passwordConfirm) {

    List<String> emailErrors = accountService.getEmailErrors(email);
    List<String> passwordErrors = accountService.getPasswordErrors(password, passwordConfirm);

    if (emailErrors.isEmpty() && passwordErrors.isEmpty()) {
      accountService.saveAccount(email, password);
      accountService.saveAuthentication(email);
      return "redirect:/index";
    }
    else {
      redirectAttributes.addFlashAttribute("email", email);
      redirectAttributes.addFlashAttribute("emailErrors", emailErrors);
      redirectAttributes.addFlashAttribute("passwordErrors", passwordErrors);
      return "redirect:/signup";
    }
  }

  @GetMapping("/login")
  public String getLogin(Model model,
                         @RequestParam(required=false) String error) {

    if (error != null) {
      model.addAttribute("error", "Account not found!");
    }

    return loadView(model, "accounts/login");
  }

}
