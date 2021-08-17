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

@Slf4j
@Controller
public class DefaultController extends TemplateView {

  // Index page

  @GetMapping({"/", "/index"})
  public String index(Model model) {
    return loadView(model, "default/index");
  }

  // Sidebar links

  @GetMapping("/search")
  public String getSearch(Model model,
                          @RequestParam(value="terms", required=false) String terms,
                          @RequestParam(value="filter", required=false) String filter,
                          @RequestParam(value="sort", required=false) String sort,
                          @RequestParam(value="order", required=false) String order,
                          @RequestParam(value="page", required=false) Integer page) {

    final Table<Book> bookTable = new Table<>(Book.class);
    final Table<Category> categoryTable = new Table<>(Category.class);
    final Table<Catalog> catalogTable = new Table<>(Catalog.class);

    if (filter == null) {
      filter = "books";
    }

    if (sort == null) {
      sort = "date";
    }

    if (terms == null) {
      terms = "";
    }

    if (order == null || !order.equals("asc")) {
      order = "desc";
    }

    String termsFilter;
    String sortColumn;

    switch(filter) {
      case "authors":
      case "publishers":
      case "subjects":
        termsFilter = "category_name contains " + terms;

        if (sort.equals("title")) {
          sortColumn = "category_name";
        }
        else {
          sortColumn = "category_add_date";
        }

        String categoryType = filter.substring(0, filter.length() - 1);
        String[] filters = new String[2];
        filters[0] = termsFilter;
        filters[1] = "category_type contains " + categoryType;
        List<Category> categories = categoryTable.filterBy(filters)
                                                 .sortBy(sortColumn)
                                                 .inOrder(order)
                                                 .select();

        model.addAttribute("categories", categories);
        break;

      case "lists":
        termsFilter = "catalog_name contains " + terms;

        if (sort.equals("title")) {
          sortColumn = "catalog_name";
        }
        else {
          sortColumn = "catalog_last_update";
        }

        List<Catalog> lists = catalogTable.filterBy(termsFilter)
                                          .sortBy(sortColumn)
                                          .inOrder(order)
                                          .select();

        model.addAttribute("lists", lists);
        break;

      default:
        termsFilter = "book_title contains " + terms;

        if (sort.equals("title")) {
          sortColumn = "book_title";
        }
        else {
          sortColumn = "book_publish_date";
        }

        List<Book> books = bookTable.filterBy(termsFilter)
                                    .sortBy(sortColumn)
                                    .inOrder(order)
                                    .select();

        model.addAttribute("books", books);
    }

    if (page == null) {
      page = 1;
    }

    // Use subList https://docs.oracle.com/javase/7/docs/api/java/util/List.html#subList(int,%20int)

    model.addAttribute("terms", terms);
    model.addAttribute("filter", filter);
    model.addAttribute("sort", sort);
    model.addAttribute("order", order);
    model.addAttribute("page", page);

    return loadView(model, "default/search");
  }

  @PostMapping("/search")
  public String postSearch(Model model,
                           @RequestParam("terms") String terms,
                           @RequestParam("filter") String filter,
                           @RequestParam("sort") String sort,
                           @RequestParam("order") String order,
                           RedirectAttributes redirectAttributes) {

    terms = terms.trim().toLowerCase();
    filter = filter.trim().toLowerCase();
    sort = sort.trim().toLowerCase();
    order = order.trim().toLowerCase();

    String entry = String.format("Search %s for '%s' ordered by %s %s", filter,  terms, sort, order);
    log.info(entry);

    if (!terms.equals("")) {
      redirectAttributes.addAttribute("terms", terms);
    }

    if (!filter.equals("books")) {
      redirectAttributes.addAttribute("filter", filter);
    }

    if (!sort.equals("date")) {
      redirectAttributes.addAttribute("sort", sort);
    }

    if (!order.equals("desc")) {
      redirectAttributes.addAttribute("order", order);
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
