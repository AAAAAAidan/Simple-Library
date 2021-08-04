package org.simplelibrary.controller;

import org.simplelibrary.view.TemplateView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DefaultController extends TemplateView {
  
  // Index page
  
  @GetMapping({"/", "/index"})
  public String index(Model model) {
    return TemplateView(model, "default/index");
  }

  // Account links

  @GetMapping("/signup")
  public String getSignup(Model model) {
    return TemplateView(model, "default/signup");
  }

  @PostMapping("/signup")
  public String postSignup(Model model) {
    return TemplateView(model, "default/index");
  }

  @GetMapping("/login")
  public String getLogin(Model model) {
    return TemplateView(model, "default/login");
  }

  @PostMapping("/login")
  public String postLogin(Model model) {
    return TemplateView(model, "default/index");
  }

  // Sidebar links

  @GetMapping("/search")
  public String getSearch(Model model) {
    return TemplateView(model, "default/search");
  }

  @PostMapping("/search")
  public String postSearch(Model model) {
    return TemplateView(model, "default/search");
  }

  @GetMapping("/about")
  public String about(Model model) {
    return TemplateView(model, "default/about");
  }
  
  @GetMapping("/help")
  public String help(Model model) {
    return TemplateView(model, "default/help");
  }

}
