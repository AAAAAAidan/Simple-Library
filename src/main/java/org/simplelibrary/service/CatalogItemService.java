package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.CatalogItem;
import org.simplelibrary.repository.CatalogItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* Service class for the catalog item entity.
*/
@Slf4j
@Service
public class CatalogItemService {

  private final CatalogItemRepository catalogItemRepository;

  @Autowired
  public CatalogItemService(CatalogItemRepository catalogItemRepository) {
    this.CatalogItemRepository = catalogItemRepository;
  }

  /**
   * Saves and returns a catalog item.
   *
   * @param catalogItem the catalog item to save
   * @return the saved catalog item
   */
  public CatalogItem saveAndFlush(CatalogItem catalogItem) {
    return CatalogItemRepository.saveAndFlush(catalogItem);
  }

  /**
   * Gets a catalog item based on the provided ID.
   *
   * @param id the ID of the catalog item
   * @return the catalog item found
   */
  public CatalogItem getById(Integer id) {
    return CatalogItemRepository.getById(id);
  }

  /**
   * Deletes a catalog item based on the provided ID.
   *
   * @param id the ID of the catalog item
   */
  public void deleteById(Integer id) {
    CatalogItemRepository.deleteById(id);
  }

}
