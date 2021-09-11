package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.service.SearchService;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@SessionAttributes({"resultsPerPage"})
public class SearchController extends TemplateView {

  private final SearchService searchService;

  @Autowired
  public SearchController(SearchService searchService) {
    this.searchService = searchService;
  }

  @GetMapping("/search")
  public String getSearch(Model model,
                          HttpServletRequest request,
                          @RequestParam(required=false) String terms,
                          @RequestParam(required=false) String filter,
                          @RequestParam(required=false) String order,
                          @RequestParam(required=false) Integer page) {

    Integer resultsPerPage = (Integer) model.getAttribute("resultsPerPage");

    if (resultsPerPage == null) {
      resultsPerPage = resultsPerPageInit();
    }

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

    List<?> results = searchService.getSearchResults(terms, filter, order);
    int resultCount = results.size();
    int lastPage = searchService.getLastPageNumber(resultCount, resultsPerPage);

    if (page > lastPage) {
      page = lastPage;
    }

    List<String> resultPages = searchService.getPageNumbers(resultCount, page, resultsPerPage);
    results = searchService.limitResultsByPage(results, page, resultsPerPage);
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
    model.addAttribute("terms", terms);
    model.addAttribute("filter", filter);
    model.addAttribute("order", order);
    model.addAttribute("resultsPerPage", resultsPerPage);
    model.addAttribute("results", results);
    model.addAttribute("resultCount", resultCount);
    model.addAttribute("resultPages", resultPages);
    return loadView(model, "search/search");
  }

  @PostMapping("/search")
  public String postSearch(RedirectAttributes redirectAttributes,
                           @RequestParam Integer resultsPerPage,
                           @RequestParam String terms,
                           @RequestParam String filter,
                           @RequestParam String order) {

    if (resultsPerPage == null) {
      resultsPerPage = resultsPerPageInit();
    }

    redirectAttributes.addFlashAttribute("resultsPerPage", resultsPerPage);

    terms = terms.trim().toLowerCase();
    filter = filter.trim().toLowerCase();
    order = order.trim().toLowerCase();

    String entry = String.format("Get %s results from %s for '%s' ordered alphabetically %s",
                                 resultsPerPage, filter,  terms, order);
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

  @ModelAttribute("resultsPerPage")
  public Integer resultsPerPageInit() {
    return 10;
  }

}
