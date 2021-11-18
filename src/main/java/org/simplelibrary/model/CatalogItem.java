package org.simplelibrary.model;

import lombok.*;
// import org.springframework.data.annotation.

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Persistent class for the catalog item database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="catalog_item")
public class CatalogItem implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="catalog_item_id", nullable=false)
  private Integer id;

  @NonNull
  @Column(name="catalog_item_source_id", nullable=false)
  private Integer sourceId;

  @NonNull
  @Column(name="catalog_item_source_filter", length=32, nullable=false)
  private String sourceFilter;

  @Transient
  private String sourceName;

  @Column(name="catalog_item_add_date", nullable=false, updatable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="catalog_item_status", length=12, nullable=false)
  private String status = "ACTIVE";

  // Bi-directional many-to-one association to Catalog
  @ManyToOne
  @JoinColumn(name="catalog_id")
  private Catalog catalog;

}
