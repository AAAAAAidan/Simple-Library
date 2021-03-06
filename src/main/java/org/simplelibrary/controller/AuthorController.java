package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Author;
import org.simplelibrary.service.AccountService;
import org.simplelibrary.service.AuthorService;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller class for author related services.
 */
@Slf4j
@Controller
public class AuthorController extends TemplateView {

  private final AccountService accountService;
  private final AuthorService authorService;

  @Autowired
  public AuthorController(AccountService accountService,
                          AuthorService authorService) {
    this.accountService = accountService;
    this.authorService = authorService;
  }

  @GetMapping("/authors")
  public String getAuthors(RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("filter", "authors");
    return "redirect:/search";
  }

  @GetMapping("/authors/{id}")
  public String getAuthor(Model model, @PathVariable Integer id) {
    Author author = authorService.getById(id);
    model.addAttribute("author", author);

    if (accountService.isLoggedIn()) {
      model.addAttribute("lists", accountService.getLoggedInAccount().getCatalogs());
    }

    return loadView(model, "authors/author");
  }

}
