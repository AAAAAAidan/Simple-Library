package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Author;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Catalog;
import org.simplelibrary.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchService {

  private final AccountService accountService;
  private final TableService tableService;

  @Autowired
  public SearchService(AccountService accountService,
                       TableService tableService) {
    this.accountService = accountService;
    this.tableService = tableService;
  }

  public List<?> getSearchResults(String terms, String filter, String order) {
    filter = filter.replace("list", "catalog"); // usage of "list" is for front end only
    String column = filter.toLowerCase().replaceAll("s$", "").replaceAll("ies$", "y");
    String sortColumn = column + "_name";
    String termsFilter = sortColumn + " contains " + terms;
    TableService searchTableService = tableService.filterBy(termsFilter).sortBy(sortColumn).inOrder(order);

    switch (filter) {
      case "authors":
        return searchTableService.select(Author.class);
      case "subjects":
        return searchTableService.select(Subject.class);
      case "catalogs":
        if (!accountService.isLoggedIn()) { return new ArrayList<Catalog>(); }
        String accountFilter = "account_id=" + accountService.getLoggedInId();
        String[] filters = {termsFilter, accountFilter};
        return searchTableService.filterBy(filters).select(Catalog.class);
      default:
        return searchTableService.select(Book.class);
    }
  }

  public Integer getLastPageNumber(int resultCount, int resultsPerPage) {
    int lastPage = resultCount / resultsPerPage;

    if (resultCount % resultsPerPage != 0) {
      lastPage++;
    }

    return lastPage;
  }

  public List<String> getPageNumbers(int resultCount, int currentPage, int resultsPerPage) {
    List<String> resultPages = new ArrayList<>();
    int lastPage = getLastPageNumber(resultCount, resultsPerPage);
    int pageCounter = 0;

    while (++pageCounter <= lastPage) {
      if  (
            pageCounter <= 3 || // First three pages
            pageCounter >= lastPage - 2 || // Last three pages
            // Up to five pages around and including the current page
            (pageCounter >= currentPage - 2 && pageCounter <= currentPage + 2)
          ) {
        resultPages.add(String.valueOf(pageCounter));
      }
      else if (
                pageCounter == currentPage - 3 ||
                pageCounter == currentPage + 3
              ) {
        resultPages.add("...");
      }
    }

    return resultPages;
  }

  public List<?> limitResultsByPage(List<?> results, int page, int resultsPerPage) {
    if (results.isEmpty()) {
      return results;
    }

    int fromIndex = page * resultsPerPage - resultsPerPage;

    if (fromIndex < 0) {
      fromIndex = 0;
    }

    if (fromIndex > results.size()) {
      fromIndex = results.size();
    }

    int toIndex = fromIndex + resultsPerPage;

    if (toIndex > results.size()) {
      toIndex = results.size();
    }

    return results.subList(fromIndex, toIndex);
  }

}
