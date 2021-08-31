package org.simplelibrary.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the book database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="book_id", length=12, nullable=false)
  private Integer id;

  @NonNull
  @Column(name="book_name", unique=true, length=640, nullable=false)
  private String name;

  @Column(name="book_view_count")
  private Integer viewCount = 0;

  @Column(name="book_download_count")
  private Integer downloadCount = 0;

  @Column(name="book_add_date", nullable=false, updatable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="book_status", nullable=false,
          columnDefinition="ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'")
  private String status = "ACTIVE";

  // Bi-directional many-to-many association to Catalog
  @ManyToMany(mappedBy="books")
  private List<Catalog> catalogs;

  // Bi-directional many-to-many association to Author
  @ManyToMany(mappedBy="books")
  private List<Author> authors;

  // Bi-directional many-to-many association to Subject
  @ManyToMany(mappedBy="books")
  private List<Subject> subjects;

}
