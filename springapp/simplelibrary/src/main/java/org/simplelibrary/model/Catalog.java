package org.simplelibrary.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the catalog database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Catalog.findAll", query="SELECT c FROM Catalog c"),
	@NamedQuery(name="Catalog.findById", query="SELECT c FROM Catalog c WHERE c.catalogId = :id")
})
public class Catalog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int catalogId;

	private Timestamp catalogAddDate;

	private String catalogDescription;

	private Timestamp catalogLastUpdate;

	private String catalogName;

	private String catalogPrivacy;

	private String catalogStatus;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="accountId")
	private Account account;

	//bi-directional many-to-many association to Book
	@ManyToMany
	@JoinTable(
		name="bookcatalogmap"
		, joinColumns={
			@JoinColumn(name="catalogId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="bookId")
			}
		)
	private List<Book> books;

	public Catalog() {
	}

	public int getCatalogId() {
		return this.catalogId;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}

	public Timestamp getCatalogAddDate() {
		return this.catalogAddDate;
	}

	public void setCatalogAddDate(Timestamp catalogAddDate) {
		this.catalogAddDate = catalogAddDate;
	}

	public String getCatalogDescription() {
		return this.catalogDescription;
	}

	public void setCatalogDescription(String catalogDescription) {
		this.catalogDescription = catalogDescription;
	}

	public Timestamp getCatalogLastUpdate() {
		return this.catalogLastUpdate;
	}

	public void setCatalogLastUpdate(Timestamp catalogLastUpdate) {
		this.catalogLastUpdate = catalogLastUpdate;
	}

	public String getCatalogName() {
		return this.catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getCatalogPrivacy() {
		return this.catalogPrivacy;
	}

	public void setCatalogPrivacy(String catalogPrivacy) {
		this.catalogPrivacy = catalogPrivacy;
	}

	public String getCatalogStatus() {
		return this.catalogStatus;
	}

	public void setCatalogStatus(String catalogStatus) {
		this.catalogStatus = catalogStatus;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Book> getBooks() {
		return this.books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}