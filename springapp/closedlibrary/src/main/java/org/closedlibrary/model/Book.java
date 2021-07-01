package org.closedlibrary.model;

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
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String bookId;

	private Timestamp bookAddDate;

	private String bookAvailability;

	private String bookDescription;

	private String bookIdentifiers;

	private int bookPageCount;

	@Temporal(TemporalType.DATE)
	private Date bookPublishDate;

	private String bookStatus;

	private String bookTitle;

	private int bookTotalBorrows;

	//bi-directional many-to-one association to Borrow
	@OneToMany(mappedBy="book")
	private List<Borrow> borrows;

	//bi-directional many-to-one association to Catalog
	@OneToMany(mappedBy="book")
	private List<Catalog> catalogs;

	//bi-directional many-to-one association to Key
	@OneToMany(mappedBy="book")
	private List<Key> keys;

	public Book() {
	}

	public String getBookId() {
		return this.bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Timestamp getBookAddDate() {
		return this.bookAddDate;
	}

	public void setBookAddDate(Timestamp bookAddDate) {
		this.bookAddDate = bookAddDate;
	}

	public String getBookAvailability() {
		return this.bookAvailability;
	}

	public void setBookAvailability(String bookAvailability) {
		this.bookAvailability = bookAvailability;
	}

	public String getBookDescription() {
		return this.bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public String getBookIdentifiers() {
		return this.bookIdentifiers;
	}

	public void setBookIdentifiers(String bookIdentifiers) {
		this.bookIdentifiers = bookIdentifiers;
	}

	public int getBookPageCount() {
		return this.bookPageCount;
	}

	public void setBookPageCount(int bookPageCount) {
		this.bookPageCount = bookPageCount;
	}

	public Date getBookPublishDate() {
		return this.bookPublishDate;
	}

	public void setBookPublishDate(Date bookPublishDate) {
		this.bookPublishDate = bookPublishDate;
	}

	public String getBookStatus() {
		return this.bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getBookTitle() {
		return this.bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public int getBookTotalBorrows() {
		return this.bookTotalBorrows;
	}

	public void setBookTotalBorrows(int bookTotalBorrows) {
		this.bookTotalBorrows = bookTotalBorrows;
	}

	public List<Borrow> getBorrows() {
		return this.borrows;
	}

	public void setBorrows(List<Borrow> borrows) {
		this.borrows = borrows;
	}

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

	public List<Catalog> getCatalogs() {
		return this.catalogs;
	}

	public void setCatalogs(List<Catalog> catalogs) {
		this.catalogs = catalogs;
	}

	public Catalog addCatalog(Catalog catalog) {
		getCatalogs().add(catalog);
		catalog.setBook(this);

		return catalog;
	}

	public Catalog removeCatalog(Catalog catalog) {
		getCatalogs().remove(catalog);
		catalog.setBook(null);

		return catalog;
	}

	public List<Key> getKeys() {
		return this.keys;
	}

	public void setKeys(List<Key> keys) {
		this.keys = keys;
	}

	public Key addKey(Key key) {
		getKeys().add(key);
		key.setBook(this);

		return key;
	}

	public Key removeKey(Key key) {
		getKeys().remove(key);
		key.setBook(null);

		return key;
	}

}