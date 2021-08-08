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

import java.util.List;
import java.util.regex.Pattern;

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
    return loadView(model, "default/index");
  }

  // Account links

  @GetMapping("/signup")
  public String getSignup(Model model) {
    return loadView(model, "default/signup");
  }

  @PostMapping("/signup")
  public String postSignup(Model model) {
    return "redirect:/index";
  }

  @GetMapping("/login")
  public String getLogin(Model model) {
    return loadView(model, "default/login");
  }

  @PostMapping("/login")
  public String postLogin(Model model) {
    return "redirect:/index";
  }

  // Sidebar links

  @GetMapping("/search")
  public String getSearch(Model model,
                          @RequestParam(value="search", required=false) String searchTerms,
                          @RequestParam(value="filter", required=false) String filter,
                          @RequestParam(value="sort", required=false) String sort,
                          @RequestParam(value="order", required=false) String order,
                          @RequestParam(value="page", required=false) Integer page) {

    if (searchTerms != null) {
      searchTerms = searchTerms.trim();
    }
    else {
      searchTerms = "";
    }

    searchTerms = "book_title contains " + searchTerms;

    if (sort != null && Pattern.matches("[name|title](?i)", sort.trim())) {
      sort = "book_title";
    }
    else {
      sort = "book_publish_date";
    }

    if (order == null || Pattern.matches("[descending|desc](?i)", sort.trim())) {
      order = "desc";
    }
    else {
      order = "asc";
    }

    if (filter != null) {
      switch(filter) {
        case "authors":
          break;
        case "publishers":
          break;
        case "genres":
          break;
        case "lists":
          break;
        default:
          List<Book> books = BOOK_TABLE.filterBy(searchTerms).sortBy(sort).inOrder(order).select();
          model.addAttribute("books", books);
      }
    }
    else {
      List<Book> books = BOOK_TABLE.filterBy(searchTerms).sortBy(sort).inOrder(order).select();
      model.addAttribute("books", books);
    }

    return loadView(model, "default/search");
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
      redirectAttributes.addAttribute("search", searchTerms);
    }

    if (!filter.equals("books")) {
      redirectAttributes.addAttribute("filter", filter);
    }

    if (!sort.equals("date")) {
      redirectAttributes.addAttribute("sort", sort);
    }

    if (!order.equals("descending")) {
      redirectAttributes.addAttribute("order", "asc");
    }

    return "redirect:/search";
  }

  @GetMapping("/about")
  public String about(Model model) {
    return loadView(model, "default/about");
  }
  
  @GetMapping("/help")
  public String help(Model model) {
    return loadView(model, "default/help");
  }

}
