package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Catalog;
import org.simplelibrary.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CatalogService {

  private final CatalogRepository catalogRepository;
  private final AccountService accountService;

  @Autowired
  public CatalogService(CatalogRepository catalogRepository,
                        AccountService accountService) {
    this.catalogRepository = catalogRepository;
    this.accountService = accountService;
  }

  public Catalog saveAndFlush(Catalog catalog) {
    catalog.setAccount(accountService.getLoggedInAccount());
    return catalogRepository.saveAndFlush(catalog);
  }

  public Catalog getById(Integer id) {
    return catalogRepository.getById(id);
  }

  public void deleteById(Integer id) {
    catalogRepository.deleteById(id);
  }

}
