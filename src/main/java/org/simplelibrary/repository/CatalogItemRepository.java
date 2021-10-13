package org.simplelibrary.repository;

import org.simplelibrary.model.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for the catalog item database table.
 */
@Repository
public interface CatalogItemRepository extends JpaRepository<CatalogItem, Integer> {

}
