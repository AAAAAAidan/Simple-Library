package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Author;
import org.simplelibrary.model.Book;
import org.simplelibrary.model.Subject;
import org.simplelibrary.service.AccountService;
import org.simplelibrary.service.AuthorService;
import org.simplelibrary.service.BookService;
import org.simplelibrary.service.SubjectService;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for index and static pages.
 */
@Slf4j
@Controller
public class HomeController extends TemplateView {

  private final AccountService accountService;
  private final AuthorService authorService;
  private final BookService bookService;
  private final SubjectService subjectService;

  @Autowired
  public HomeController(AccountService accountService,
                        AuthorService authorService,
                        BookService bookService,
                        SubjectService subjectService) {
    this.accountService = accountService;
    this.authorService = authorService;
    this.bookService = bookService;
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
    model.addAttribute("isLoggedIn", accountService.isLoggedIn());
    return loadView(model, "home/index");
  }

  // Footer links

  @GetMapping("/about")
  public String getAbout(Model model) {
    return loadView(model, "home/about");
  }

  @GetMapping("/help")
  public String getHelp(Model model) {
    return loadView(model, "home/help");
  }

}
