package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CatalogService {

  private CatalogRepository catalogRepository;

  @Autowired
  public CatalogService(CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

}
