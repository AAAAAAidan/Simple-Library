package org.simplelibrary.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the isbn database table.
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Isbn.findAll", query="SELECT i FROM Isbn i"),
    @NamedQuery(name="Isbn.findById", query="SELECT i FROM Isbn i WHERE i.isbnId = :id")
})
public class Isbn implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private String isbnId;

  private String isbnType;

  //bi-directional many-to-one association to Book
  @ManyToOne
  @JoinColumn(name="bookId")
  private Book book;

  public Isbn() {
  }

  public String getIsbnId() {
    return isbnId;
  }

  public void setIsbnId(String isbnId) {
    this.isbnId = isbnId;
  }

  public String getIsbnType() {
    return isbnType;
  }

  public void setIsbnType(String isbnType) {
    this.isbnType = isbnType;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

}