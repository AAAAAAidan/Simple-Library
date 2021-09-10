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
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

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

    if (Pattern.matches(".*.(png|jpg|jpeg)", filename)) {
      accountService.saveProfilePicture(file);
      redirectAttributes.addFlashAttribute("successMessage", "Your profile picture has been saved!");
    }
    else {
      redirectAttributes.addFlashAttribute("fileMessage", "File must be .png or .jpg");
    }

    return "redirect:/account";
  }

  @PostMapping("/account/email")
  public String postEmail(Model model,
                          RedirectAttributes redirectAttributes,
                          @RequestParam String email) {

    List<String> emailMessages = accountService.getEmailMessages(email);

    if (emailMessages.size() == 0) {
      accountService.saveEmail(email);
      accountService.saveAuthentication(email);
      redirectAttributes.addFlashAttribute("successMessage", "Your email has been saved!");
    }
    else {
      redirectAttributes.addFlashAttribute("emailMessages", emailMessages);
    }

    return "redirect:/account";
  }

  @PostMapping("/account/password")
  public String postPassword(Model model,
                             RedirectAttributes redirectAttributes,
                             @RequestParam String passwordOld,
                             @RequestParam String passwordNew,
                             @RequestParam String passwordConfirm) {

    List<String> passwordMessages = accountService.getPasswordMessages(passwordNew, passwordConfirm);

    if (passwordMessages.size() == 0) {
      accountService.savePassword(passwordNew);
      accountService.saveAuthentication(accountService.getLoggedInEmail());
      redirectAttributes.addFlashAttribute("successMessage", "Your password has been saved!");
    }
    else {
      redirectAttributes.addFlashAttribute("passwordMessages", passwordMessages);
    }

    return "redirect:/account";
  }

  @PostMapping("/account/settings")
  public String postSettings(Model model,
                             RedirectAttributes redirectAttributes) {

    redirectAttributes.addFlashAttribute("successMessage", "Your settings have been saved!");
    return "redirect:/account";
  }

  @GetMapping("/signup")
  public String getSignup(Model model) {
    return loadView(model, "accounts/signup");
  }

  @PostMapping("/signup")
  public String postSignup(Model model,
                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String passwordConfirm) {

    List<String> emailMessages = accountService.getEmailMessages(email);
    List<String> passwordMessages = accountService.getPasswordMessages(password, passwordConfirm);

    if (emailMessages.size() == 0 && passwordMessages.size() == 0) {
      accountService.signUp(email, password);
      accountService.logIn(request, email, password);
      return "redirect:/index";
    }
    else {
      redirectAttributes.addFlashAttribute("emailMessages", emailMessages);
      redirectAttributes.addFlashAttribute("passwordMessages", passwordMessages);
      return "redirect:/signup";
    }
  }

  @GetMapping("/login")
  public String getLogin(Model model,
                         @RequestParam(required=false) String error) {

    if (error != null) {
      model.addAttribute("message", "Account not found!");
    }

    return loadView(model, "accounts/login");
  }

}
