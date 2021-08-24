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

  @NonNull
  @Column(name="catalog_description", length=3200, nullable=false)
  private String description;

  @Column(name="catalog_privacy", nullable=false,
          columnDefinition="ENUM('Private', 'Public') DEFAULT 'Private'")
  private String privacy = "Private";

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

  // Bi-directional many-to-many association to Book
  @ManyToMany
  @JoinTable(
    name="book_catalog_map",
    joinColumns={ @JoinColumn(name="catalog_id") },
    inverseJoinColumns={ @JoinColumn(name="book_id") }
  )
  private List<Book> books;

}
