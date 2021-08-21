package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Category;
import org.simplelibrary.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CategoryService {

  private CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public void save(Category category) {
    categoryRepository.save(category);
  }

  public void saveAll(List<Category> categories) {
    categoryRepository.saveAll(categories);
  }

  public boolean existsByName(String name) {
    return categoryRepository.existsByName(name);
  }

  public Category getByName(String name) {
    return categoryRepository.getByName(name);
  }

  public void mapToBook(Category category, Book book) {
    List<Book> books = category.getBooks();
    boolean alreadyPresent = false;

    if (books == null) {
      books = new ArrayList<>();
    }

    for (Book b : books) {
      if (b.getName().equals(book.getName())) {
        alreadyPresent = true;
        break;
      }
    }

    if (!alreadyPresent) {
      books.add(book);
    }

    category.setBooks(books);
    categoryRepository.save(category);
  }

}
