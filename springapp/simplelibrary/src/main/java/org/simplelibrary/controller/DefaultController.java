package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Catalog;
import org.simplelibrary.model.Category;
import org.simplelibrary.util.Table;
import org.simplelibrary.view.TemplateView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class DefaultController extends TemplateView {

  // These will be used by the search page

  private final Table<Book> BOOK_TABLE = new Table<>(Book.class);
  private final Table<Category> CATEGORY_TABLE = new Table<>(Category.class);
  private final Table<Catalog> CATALOG_TABLE = new Table<>(Catalog.class);

  // Index page
  
  @GetMapping({"/", "/index"})
  public String index(Model model) {
    return getView(model, "default/index");
  }

  // Account links

  @GetMapping("/signup")
  public String getSignup(Model model) {
    return getView(model, "default/signup");
  }

  @PostMapping("/signup")
  public String postSignup(Model model) {
    return "redirect:/index";
  }

  @GetMapping("/login")
  public String getLogin(Model model) {
    return getView(model, "default/login");
  }

  @PostMapping("/login")
  public String postLogin(Model model) {
    return "redirect:/index";
  }

  // Sidebar links

  @GetMapping("/search")
  public String getSearch(Model model) {
    return getView(model, "default/search");
  }

  @PostMapping("/search")
  public String postSearch(Model model,
                           @RequestParam("searchTerms") String searchTerms,
                           @RequestParam("filter") String filter,
                           @RequestParam("sort") String sort,
                           @RequestParam("order") String order,
                           RedirectAttributes redirectAttributes) {

    searchTerms = searchTerms.trim().toLowerCase();
    filter = filter.trim().toLowerCase();
    sort = sort.trim().toLowerCase();
    order = order.trim().toLowerCase();

    String entry = String.format("Search %s for '%s' ordered by %s %s", filter,  searchTerms, sort, order);
    log.info(entry);

    if (!searchTerms.equals("")) {
      redirectAttributes.addAttribute("searchTerms", searchTerms);
    }

    if (!filter.equals("unfiltered")) {
      redirectAttributes.addAttribute("filter", filter);
    }

    if (!sort.equals("date")) {
      redirectAttributes.addAttribute("sort", sort);
    }

    if (order.equals("desc")) {
      redirectAttributes.addAttribute("order", order);
    }

    return "redirect:/search";
  }

  @GetMapping("/about")
  public String about(Model model) {
    return getView(model, "default/about");
  }
  
  @GetMapping("/help")
  public String help(Model model) {
    return getView(model, "default/help");
  }

}
