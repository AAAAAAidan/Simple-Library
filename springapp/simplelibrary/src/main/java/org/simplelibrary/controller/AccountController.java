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

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class AccountController extends TemplateView {

  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/account")
  public String getAccount(Model model) {
    model.addAttribute("filepath", accountService.getProfilePicturePath());
    return loadView(model, "accounts/account");
  }

  @PostMapping("/account/profilepicture")
  public String postProfilePicture(RedirectAttributes redirectAttributes,
                                   @RequestParam("file") MultipartFile file) {

    String fileName = file.getOriginalFilename();

    if (Pattern.matches(".*.(png|jpg|jpeg)", fileName)) {
      accountService.uploadProfilePicture(file);
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
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("passwordConfirm") String passwordConfirm) {

    if (password.equals(passwordConfirm)) {
      accountService.signUpAccount(email, password);
      accountService.logInAccount(request, email, password);
      return "redirect:/index";
    }
    else {
      return "redirect:/signup";
    }
  }

  @GetMapping("/login")
  public String getLogin(Model model) {
    return loadView(model, "accounts/login");
  }

}
