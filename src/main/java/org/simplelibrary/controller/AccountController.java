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

import java.util.ArrayList;
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
    model.addAttribute("account", accountService.getLoggedInAccount());
    return loadView(model, "accounts/account");
  }

  @PostMapping("/account/profilepicture")
  public String postProfilePicture(RedirectAttributes redirectAttributes,
                                   @RequestParam MultipartFile file) {

    String filename = file.getOriginalFilename();

    if (Pattern.matches(".*.(png|jpg|jpeg)", filename)) {
      accountService.saveProfilePicture(file);
      redirectAttributes.addFlashAttribute("message", "Successfully uploaded " + file.getOriginalFilename());
    }
    else {
      redirectAttributes.addFlashAttribute("message", "File must be .png or .jpg");
    }

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

    List<String> emailMessages = new ArrayList<>();

    if (accountService.getByEmail(email) != null) {
      emailMessages.add("That email is already taken!");
    }

    if (!Pattern.matches("[A-Za-z0-9]+@+[A-Za-z0-9]+.+[A-Za-z]+", email)) {
      emailMessages.add("The email must contain a valid email address!");
    }

    List<String> passwordMessages = new ArrayList<>();

    if (!password.equals(passwordConfirm)) {
      passwordMessages.add("The passwords entered do not match!");
    }

    if (password.length() < 8) {
      passwordMessages.add("The password must contain 8 or more characters!");
    }

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
