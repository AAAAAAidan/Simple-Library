package org.simplelibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
  
  // Home page
  
  @RequestMapping(value={"", "/", "index"})
  public String index() {
    return "index";
  }
  
  // Sidebar links
  
  @RequestMapping("/search")
  public String search() {
    return "search";
  }
  
  @RequestMapping("/about")
  public String about() {
    return "about";
  }
  
  @RequestMapping("/help")
  public String help() {
    return "help";
  }
  
}
