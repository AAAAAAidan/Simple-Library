package org.closedlibrary.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the key database table.
 * 
 */
@Entity
@NamedQuery(name="Key.findAll", query="SELECT k FROM Key k")
public class Key implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int keyId;

	private Timestamp keyAddDate;

	private String keyStatus;

	//bi-directional many-to-one association to Book
	@ManyToOne
	@JoinColumn(name="bookId")
	private Book book;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;

	//bi-directional many-to-one association to Catalog
	@ManyToOne
	@JoinColumn(name="catalogId")
	private Catalog catalog;

	public Key() {
	}

	public int getKeyId() {
		return this.keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public Timestamp getKeyAddDate() {
		return this.keyAddDate;
	}

	public void setKeyAddDate(Timestamp keyAddDate) {
		this.keyAddDate = keyAddDate;
	}

	public String getKeyStatus() {
		return this.keyStatus;
	}

	public void setKeyStatus(String keyStatus) {
		this.keyStatus = keyStatus;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Catalog getCatalog() {
		return this.catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

}