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

@Slf4j
@Controller
public class AccountController extends TemplateView {

  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/account")
  public String account(Model model) {
    return loadView(model, "accounts/account");
  }

  @GetMapping("/signup")
  public String getSignup(Model model) {
    return loadView(model, "default/signup");
  }

  @PostMapping("/signup")
  public String postSignup(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("passwordConfirm") String passwordConfirm) {

    if (password.equals(passwordConfirm)) {
      accountService.addAccount(email, password);
      return "redirect:/index";
    }
    else {
      return "redirect:/signup";
    }
  }

  @GetMapping("/login")
  public String getLogin(Model model) {
    return loadView(model, "default/login");
  }

}
