package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Catalog;
import org.simplelibrary.model.Category;
import org.simplelibrary.util.Table;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class HomeService {

  private final Table<Book> bookTable = new Table<>(Book.class);
  private final Table<Category> categoryTable = new Table<>(Category.class);
  private final Table<Catalog> catalogTable = new Table<>(Catalog.class);

  public List<?> getSearchResults(String terms, String filter, String sort, String order) {
    String termsFilter;
    String sortColumn;
    int resultCount;

    switch(filter) {
      case "authors":
      case "subjects":
        termsFilter = "category_name contains " + terms;
        sortColumn = "category_name";

        String categoryType = filter.substring(0, filter.length() - 1);
        String[] filters = new String[2];
        filters[0] = termsFilter;
        filters[1] = "category_type contains " + categoryType;

        List<Category> categories = categoryTable.filterBy(filters)
                                                 .sortBy(sortColumn)
                                                 .inOrder(order)
                                                 .select();
        return categories;

      case "lists":
        termsFilter = "catalog_name contains " + terms;
        sortColumn = "catalog_name";

        List<Catalog> lists = catalogTable.filterBy(termsFilter)
                                          .sortBy(sortColumn)
                                          .inOrder(order)
                                          .select();
        return lists;

      default:
        termsFilter = "book_name contains " + terms;
        sortColumn = "book_name";

        List<Book> books = bookTable.filterBy(termsFilter)
                                    .sortBy(sortColumn)
                                    .inOrder(order)
                                    .select();
        return books;
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
