package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.repository.IsbnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IsbnService {

  private IsbnRepository isbnRepository;

  @Autowired
  public IsbnService(IsbnRepository isbnRepository) {
    this.isbnRepository = isbnRepository;
  }

}
