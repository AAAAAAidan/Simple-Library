package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Author;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Subject;
import org.simplelibrary.util.Table;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class HomeService {

  private final Table<Author> authorTable = new Table<>(Author.class);
  private final Table<Book> bookTable = new Table<>(Book.class);
  private final Table<Subject> subjectTable = new Table<>(Subject.class);

  public List<?> getSearchResults(String terms, String filter, String order) {
    String column = filter.toLowerCase().replaceAll("s$", "").replaceAll("ies$", "y");
    String sortColumn = column + "_name";
    String termsFilter = sortColumn + " contains " + terms;

    switch(filter) {
      case "authors":
        return authorTable.filterBy(termsFilter)
                          .sortBy(sortColumn)
                          .inOrder(order)
                          .select();
      case "subjects":
        return subjectTable.filterBy(termsFilter)
                           .sortBy(sortColumn)
                           .inOrder(order)
                           .select();
      default:
        return bookTable.filterBy(termsFilter)
                        .sortBy(sortColumn)
                        .inOrder(order)
                        .select();
    }
  }

  public Integer getLastPage(int resultCount) {
    int lastPage = resultCount / 10;

    if (resultCount % 10 != 0) {
      lastPage++;
    }

    return lastPage;
  }

  public List<String> getSearchResultPages(int resultCount, int currentPage) {
    List<String> resultPages = new ArrayList<>();
    int lastPage = getLastPage(resultCount);
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

  public List<?> limitSearchResultsByPage(List<?> results, int page) {
    if (results.size() == 0) {
      return results;
    }

    int fromIndex = page * 10 - 10;

    if (fromIndex < 0) {
      fromIndex = 0;
    }

    if (fromIndex > results.size()) {
      fromIndex = results.size();
    }

    int toIndex = fromIndex + 10;

    if (toIndex > results.size()) {
      toIndex = results.size();
    }

    return results.subList(fromIndex, toIndex);
  }

}
