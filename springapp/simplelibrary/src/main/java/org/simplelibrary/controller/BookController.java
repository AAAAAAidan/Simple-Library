package org.simplelibrary.controller;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Book;
import org.simplelibrary.service.BookService;
import org.simplelibrary.util.Table;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class BookController extends TemplateView {

  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/books")
  public String books() {
    return "redirect:search";
  }

  @GetMapping("/books/{id}")
  public String book(Model model, @PathVariable String id) {
    Optional<Book> book = bookService.findBookById(id);

    if (book.isPresent()) {
      model.addAttribute("book", book.get());
      return getView(model, "books/book");
    }
    else {
      return getView(model, "errors/404");
    }
  }

}
