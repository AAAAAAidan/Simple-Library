package org.simplelibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
  
  // Index page
  
  @RequestMapping(value={"", "/", "index"})
  public String index() {
    return "default/index";
  }

  // Account links

  @RequestMapping("/signup")
  public String signup() {
    return "default/signup";
  }

  @RequestMapping("/login")
  public String login() {
    return "default/login";
  }

  // Sidebar links

  @RequestMapping("/search")
  public String search() {
    return "default/search";
  }
  
  @RequestMapping("/about")
  public String about() {
    return "default/about";
  }
  
  @RequestMapping("/help")
  public String help() {
    return "default/help";
  }

}
