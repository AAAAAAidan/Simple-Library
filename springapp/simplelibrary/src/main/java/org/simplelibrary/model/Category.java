package org.simplelibrary.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int categoryId;

	private Timestamp categoryAddDate;

	private String categoryDescription;

	private String categoryName;

	private String categoryStatus;

	private String categoryType;

	//bi-directional many-to-many association to Book
	@ManyToMany
	@JoinTable(
		name="bookcategorymap"
		, joinColumns={
			@JoinColumn(name="categoryId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="bookId")
			}
		)
	private List<Book> books;

	public Category() {
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Timestamp getCategoryAddDate() {
		return this.categoryAddDate;
	}

	public void setCategoryAddDate(Timestamp categoryAddDate) {
		this.categoryAddDate = categoryAddDate;
	}

	public String getCategoryDescription() {
		return this.categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryStatus() {
		return this.categoryStatus;
	}

	public void setCategoryStatus(String categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public String getCategoryType() {
		return this.categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public List<Book> getBooks() {
		return this.books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}