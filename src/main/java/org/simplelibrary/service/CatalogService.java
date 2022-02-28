package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.model.Catalog;
import org.simplelibrary.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for the catalog entity.
 */
@Slf4j
@Service
public class CatalogService {

  private final CatalogRepository catalogRepository;

  @Autowired
  public CatalogService(CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

  /**
   * Saves and returns a catalog.
   *
   * @param catalog the catalog to save
   * @return the saved catalog
   */
  public Catalog saveAndFlush(Catalog catalog) {
    return catalogRepository.saveAndFlush(catalog);
  }

  /**
   * Gets a catalog based on the provided ID.
   *
   * @param id the ID of the catalog
   * @return the catalog found
   */
  public Catalog getById(Integer id) {
    return catalogRepository.getById(id);
  }

  /**
   * Gets a list of catalogs based on the provided name.
   *
   * @param name the name to use as a search term
   * @param sort the sort order for the results
   * @return the catalogs found
   */
  public List<Catalog> getCatalogByNameContainingIgnoreCaseAndAccount_Id(String name, Sort sort, Integer accountId) {
    return catalogRepository.getCatalogByNameContainingIgnoreCaseAndAccount_Id(name, sort, accountId);
  }

  /**
   * Deletes a catalog based on the provided ID.
   *
   * @param id the ID of the catalog
   */
  public void deleteById(Integer id) {
    catalogRepository.deleteById(id);
  }

}
