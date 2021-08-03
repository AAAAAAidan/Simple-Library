package org.simplelibrary.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name="book_id", length=12, nullable=false)
  private String id;

  @Column(name="book_add_date", nullable=false, updatable=false, insertable=false,
      columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="book_availability", nullable=false,
      columnDefinition="ENUM('Available', 'Unavailable') DEFAULT 'Available'")
  private String availability = "Available";

  @Column(name="book_description", length=3200)
  private String description = null;

  @Column(name="book_page_count")
  private int pageCount = 0;

  @Temporal(TemporalType.DATE)
  @Column(name="book_publish_date")
  private Date publishDate = null;

  @Column(name="book_status", nullable=false,
      columnDefinition="ENUM('Active', 'Inactive') DEFAULT 'Active'")
  private String status = "Active";

  @Column(name="book_title", length=320)
  private String title = null;

  @Column(name="book_total_borrows")
  private int totalBorrows = 0;

  //bi-directional many-to-one association to Borrow
  @OneToMany(mappedBy="book")
  private List<Borrow> borrows;

  //bi-directional many-to-one association to Isbn
  @OneToMany(mappedBy="book")
  private List<Isbn> isbns;

  //bi-directional many-to-many association to Catalog
  @ManyToMany(mappedBy="books")
  private List<Catalog> catalogs;

  //bi-directional many-to-many association to Category
  @ManyToMany(mappedBy="books")
  private List<Category> categories;

  public Borrow addBorrow(Borrow borrow) {
    getBorrows().add(borrow);
    borrow.setBook(this);

    return borrow;
  }

  public Borrow removeBorrow(Borrow borrow) {
    getBorrows().remove(borrow);
    borrow.setBook(null);

    return borrow;
  }

  public Isbn addIsbn(Isbn isbn) {
    getIsbns().add(isbn);
    isbn.setBook(this);

    return isbn;
  }

  public Isbn removeIsbn(Isbn isbn) {
    getIsbns().remove(isbn);
    isbn.setBook(null);

    return isbn;
  }

}