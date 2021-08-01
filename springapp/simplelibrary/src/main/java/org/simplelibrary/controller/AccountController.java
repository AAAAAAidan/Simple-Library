package org.simplelibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {
  
  @RequestMapping("/signup")
  public String signup() {
    return "signup";
  }
  
  @RequestMapping("/login")
  public String login() {
    return "login";
  }
  
  @RequestMapping("/account")
  public String account() {
    return "account";
  }
  
}
