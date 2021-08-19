package org.simplelibrary.model;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * The persistent class for the isbn database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Isbn implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @NonNull
  @Column(name="isbn_id", nullable=false)
  private String id;

  @NonNull
  @Column(name="isbn_type", length=32, nullable=false)
  private String type;

  @Column(name="isbn_add_date", nullable=false, updatable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="isbn_status", nullable=false,
          columnDefinition="ENUM('Active', 'Inactive') DEFAULT 'Active'")
  private String status = "Active";

  // Bi-directional many-to-one association to Book
  @ManyToOne
  @JoinColumn(name="book_id")
  private Book book;

}
