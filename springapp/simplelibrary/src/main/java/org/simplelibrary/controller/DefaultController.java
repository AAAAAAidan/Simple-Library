package org.simplelibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DefaultController {
  
  // Index page
  
  @GetMapping({"/", "/index"})
  public String index() {
    return "default/index";
  }

  // Account links

  @GetMapping("/signup")
  public String getSignup() {
    return "default/signup";
  }

  @PostMapping("/signup")
  public String postSignup() {
    return "default/index";
  }

  @GetMapping("/login")
  public String getLogin() {
    return "default/login";
  }

  @PostMapping("/login")
  public String postLogin() {
    return "default/index";
  }

  // Sidebar links

  @GetMapping("/search")
  public String getSearch() {
    return "default/search";
  }

  @PostMapping("/search")
  public String postSearch() {
    return "default/search";
  }

  @GetMapping("/about")
  public String about() {
    return "default/about";
  }
  
  @GetMapping("/help")
  public String help() {
    return "default/help";
  }

}
