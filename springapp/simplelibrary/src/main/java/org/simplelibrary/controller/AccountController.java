package org.simplelibrary.controller;

import org.simplelibrary.view.TemplateView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController extends TemplateView {

  @GetMapping("/account")
  public String account(Model model) {
    return TemplateView(model, "accounts/account");
  }

}
