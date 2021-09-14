package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.CatalogItem;
import org.simplelibrary.repository.CatalogItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CatalogItemService {

  private final CatalogItemRepository CatalogItemRepository;

  @Autowired
  public CatalogItemService(CatalogItemRepository CatalogItemRepository) {
    this.CatalogItemRepository = CatalogItemRepository;
  }

  public CatalogItem saveAndFlush(CatalogItem CatalogItem) {
    return CatalogItemRepository.saveAndFlush(CatalogItem);
  }

  public CatalogItem getById(Integer id) {
    return CatalogItemRepository.getById(id);
  }

  public void deleteById(Integer id) {
    CatalogItemRepository.deleteById(id);
  }

}
