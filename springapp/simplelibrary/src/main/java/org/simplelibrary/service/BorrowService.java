package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BorrowService {

  private BorrowRepository borrowRepository;

  @Autowired
  public BorrowService(BorrowRepository borrowRepository) {
    this.borrowRepository = borrowRepository;
  }
}
