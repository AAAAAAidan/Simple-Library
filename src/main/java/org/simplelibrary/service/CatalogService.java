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

  @Autowired
  public CatalogService(CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

  public Catalog saveAndFlush(Catalog catalog) {
    return catalogRepository.saveAndFlush(catalog);
  }

  public Catalog getById(Integer id) {
    return catalogRepository.getById(id);
  }

  public void deleteById(Integer id) {
    catalogRepository.deleteById(id);
  }

}
