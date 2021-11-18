package org.simplelibrary.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Persistent class for the subject database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="subject")
public class Subject implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="subject_id", nullable=false)
  private Integer id;

  @NonNull
  @Column(name="subject_name", length=320, nullable=false)
  private String name;

  @Column(name="subject_add_date", nullable=false, updatable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="subject_status", length=12, nullable=false)
  private String status = "ACTIVE";

  // Bi-directional many-to-many association to Account
  @ManyToMany
  @JoinTable(
    name="book_subject_map",
    joinColumns={ @JoinColumn(name="subject_id") },
    inverseJoinColumns={ @JoinColumn(name="book_id") }
  )
  private List<Book> books;

}
