package org.simplelibrary.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the catalog database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Catalog implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="catalog_id", nullable=false)
  private Integer id;

  @NonNull
  @Column(name="catalog_name", length=320, nullable=false)
  private String name;

  @Column(name="catalog_privacy", nullable=false,
          columnDefinition="ENUM('PRIVATE', 'PUBLIC') DEFAULT 'PRIVATE'")
  private String privacy = "PRIVATE";

  @Column(name="catalog_last_update", nullable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp lastUpdate;

  @Column(name="catalog_add_date", nullable=false, updatable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="catalog_status", nullable=false,
          columnDefinition="ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'")
  private String status = "ACTIVE";

  // Bi-directional many-to-one association to Account
  @ManyToOne
  @JoinColumn(name="account_id")
  private Account account;

  // Bi-directional many-to-one association to CatalogItem
  @OneToMany(mappedBy="catalog")
  private List<CatalogItem> catalogItems;

  public CatalogItem addCatalogItem(CatalogItem catalogItem) {
    getCatalogItems().add(catalogItem);
    catalogItem.setCatalog(this);
    return catalogItem;
  }

  public CatalogItem removeCatalogItem(CatalogItem catalogItem) {
    getCatalogItems().remove(catalogItem);
    catalogItem.setCatalog(null);
    return catalogItem;
  }

}
