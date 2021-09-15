package org.simplelibrary.repository;

import org.simplelibrary.model.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogItemRepository extends JpaRepository<CatalogItem, Integer> {

}
