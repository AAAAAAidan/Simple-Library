package org.simplelibrary.controller;

import java.util.List;

import org.simplelibrary.model.Book;
import org.simplelibrary.util.Table;
import org.simplelibrary.view.TemplateView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController extends TemplateView {
  
  private static final Table<Book> TABLE = new Table<>(Book.class);
  
  @GetMapping("/books")
  public String books(Model model) {
    return TemplateView(model, "default/search");
  }

  @GetMapping("/books/{id}")
  public String book(Model model, @PathVariable String id) {
    List<Book> books = TABLE.filter("book_id="+id).select();
    Book book = books.size() == 0 ? null : books.get(0);
    model.addAttribute("book", book);
    return TemplateView(model, "books/book");
  }

}
