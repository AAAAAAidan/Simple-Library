package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Author;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Subject;
import org.simplelibrary.service.AuthorService;
import org.simplelibrary.service.BookService;
import org.simplelibrary.service.SubjectService;
import org.simplelibrary.service.HomeService;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class HomeController extends TemplateView {

  private AuthorService authorService;
  private BookService bookService;
  private HomeService homeService;
  private SubjectService subjectService;

  @Autowired
  public HomeController(AuthorService authorService,
                        BookService bookService,
                        HomeService homeService,
                        SubjectService subjectService) {
    this.authorService = authorService;
    this.bookService = bookService;
    this.homeService = homeService;
    this.subjectService = subjectService;
  }

  // Index page

  @GetMapping({"/", "/index"})
  public String getIndex(Model model) {
    List<Book> books = new ArrayList<>();
    List<Author> authors = new ArrayList<>();
    List<Subject> subjects = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      Book book = bookService.getByRandom();
      books.add(book);
    }

    for (int i = 0; i < 15; i++) {
      authors.add(authorService.getByRandom());
      subjects.add(subjectService.getByRandom());
    }

    model.addAttribute("authors", authors);
    model.addAttribute("books", books);
    model.addAttribute("subjects", subjects);
    return loadView(model, "home/index");
  }

  // Footer links

  @GetMapping("/search")
  public String getSearch(Model model,
                          HttpServletRequest request,
                          @RequestParam(required=false) String terms,
                          @RequestParam(required=false) String filter,
                          @RequestParam(required=false) String order,
                          @RequestParam(required=false) Integer page) {

    if (filter == null) {
      filter = "books";
    }

    if (terms == null) {
      terms = "";
    }

    if (order == null || !order.equals("desc")) {
      order = "asc";
    }

    if (page == null || page < 1) {
      page = 1;
    }

    List<?> results = homeService.getSearchResults(terms, filter, order);
    int resultCount = results.size();
    int lastPage = homeService.getLastPage(resultCount);

    if (page > lastPage) {
      page = lastPage;
    }

    results = homeService.limitSearchResultsByPage(results, page);
    List<String> resultPages = homeService.getSearchResultPages(resultCount, page);

    String currentUrl = request.getContextPath();
    String parameters = request.getQueryString();

    if (parameters != null ) {
      parameters = parameters.replaceAll("[&]*page=[-+]*[0-9]*", "");

      if (parameters.length() != 0) {
        currentUrl += "?" + parameters;
      }
    }

    model.addAttribute("currentUrl", currentUrl);
    model.addAttribute("page", page);

    model.addAttribute("results", results);
    model.addAttribute("resultCount", resultCount);
    model.addAttribute("resultPages", resultPages);

    model.addAttribute("terms", terms);
    model.addAttribute("filter", filter);
    model.addAttribute("order", order);

    return loadView(model, "home/search");
  }

  @PostMapping("/search")
  public String postSearch(RedirectAttributes redirectAttributes,
                           @RequestParam String terms,
                           @RequestParam String filter,
                           @RequestParam String order) {

    terms = terms.trim().toLowerCase();
    filter = filter.trim().toLowerCase();
    order = order.trim().toLowerCase();

    String entry = String.format("Search %s for '%s' ordered alphabetically %s", filter,  terms, order);
    log.info(entry);

    if (!terms.equals("")) {
      redirectAttributes.addAttribute("terms", terms);
    }

    if (!filter.equals("books")) {
      redirectAttributes.addAttribute("filter", filter);
    }

    if (!order.equals("asc")) {
      redirectAttributes.addAttribute("order", order);
    }

    return "redirect:/search";
  }

  @GetMapping("/about")
  public String getAbout(Model model) {
    return loadView(model, "home/about");
  }

  @GetMapping("/help")
  public String getHelp(Model model) {
    return loadView(model, "home/help");
  }

}
