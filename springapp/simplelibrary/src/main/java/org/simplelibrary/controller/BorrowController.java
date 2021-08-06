package org.simplelibrary.controller;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.service.BorrowService;
import org.simplelibrary.view.TemplateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class BorrowController extends TemplateView {

  private BorrowService borrowService;

  @Autowired
  public BorrowController(BorrowService borrowService) {
    this.borrowService = borrowService;
  }

}
