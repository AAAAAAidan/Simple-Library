package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.service.HomeService;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class HomeController extends TemplateView {

  private HomeService homeService;

  @Autowired
  public HomeController(HomeService homeService) {
    this.homeService = homeService;
  }

  // Index page

  @GetMapping({"/", "/index"})
  public String getIndex(Model model) {
    // TODO - Get five random books and five random authors
    return loadView(model, "home/index");
  }

  // Footer links

  @GetMapping("/search")
  public String getSearch(Model model,
                          HttpServletRequest request,
                          @RequestParam(value="terms", required=false) String terms,
                          @RequestParam(value="filter", required=false) String filter,
                          @RequestParam(value="sort", required=false) String sort,
                          @RequestParam(value="order", required=false) String order,
                          @RequestParam(value="page", required=false) Integer page) {

    if (filter == null) {
      filter = "books";
    }

    if (sort == null) {
      sort = "name";
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

    List<?> results = homeService.getSearchResults(terms, filter, sort, order);
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
    model.addAttribute("sort", sort);
    model.addAttribute("order", order);

    return loadView(model, "home/search");
  }

  @PostMapping("/search")
  public String postSearch(Model model,
                           RedirectAttributes redirectAttributes,
                           @RequestParam("terms") String terms,
                           @RequestParam("filter") String filter,
                           @RequestParam("sort") String sort,
                           @RequestParam("order") String order) {

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

    if (!sort.equals("name")) {
      redirectAttributes.addAttribute("sort", sort);
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
