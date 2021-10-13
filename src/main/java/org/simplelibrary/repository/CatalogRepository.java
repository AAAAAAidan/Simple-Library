package org.simplelibrary.repository;

import org.simplelibrary.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for the catalog database table.
 */
@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {

}
