package org.simplelibrary.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="category_id", nullable=false)
  private Integer id;

  @Column(name="category_add_date", nullable=false, updatable=false, insertable=false,
      columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="category_description", length=320)
  private String description = null;

  @Column(name="category_name", length=320)
  private String name = null;

  @Column(name="category_status", nullable=false,
      columnDefinition="ENUM('Active', 'Inactive') DEFAULT 'Active'")
  private String status = "Active";

  @Column(name="category_type",
      columnDefinition="ENUM('Author', 'Publisher', 'Subject')")
  private String type = null;

  //bi-directional many-to-many association to Account
  @ManyToMany
  @JoinTable(
    name="book_category_map",
    joinColumns={ @JoinColumn(name="category_id") },
    inverseJoinColumns={ @JoinColumn(name="book_id") }
  )
  private List<Book> books;

}