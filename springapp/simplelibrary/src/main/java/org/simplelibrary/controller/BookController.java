package org.simplelibrary.controller;

import org.simplelibrary.model.Book;
import org.simplelibrary.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookController {
	
    @RequestMapping("/books")
	public String books() {
		return "search";
	}
	
    @RequestMapping("/books/{id}")
	public String book(Model model, @PathVariable String id) {
    	BookService bookService = new BookService();
		Book book = bookService.findById(id);
    	model.addAttribute("book", book);
		return "book";
	}
	
}
