package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.service.IsbnService;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class IsbnController extends TemplateView {

  private IsbnService isbnService;

  @Autowired
  public IsbnController(IsbnService isbnService) {
    this.isbnService = isbnService;
  }

}
