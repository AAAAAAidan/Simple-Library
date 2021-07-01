package org.closedlibrary.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int accountId;

	private int accountActiveBorrows;

	private Timestamp accountAddDate;

	private String accountEmail;

	private String accountFirstName;

	private Timestamp accountLastLoginDate;

	private String accountLastName;

	private String accountPassword;

	private String accountStatus;

	private int accountTotalBorrows;

	//bi-directional many-to-one association to Borrow
	@OneToMany(mappedBy="account")
	private List<Borrow> borrows;

	//bi-directional many-to-one association to Card
	@OneToMany(mappedBy="account")
	private List<Card> cards;

	//bi-directional many-to-one association to Catalog
	@OneToMany(mappedBy="account")
	private List<Catalog> catalogs;

	//bi-directional many-to-one association to Setting
	@OneToMany(mappedBy="account")
	private List<Setting> settings;

	public Account() {
	}

	public int getAccountId() {
		return this.accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getAccountActiveBorrows() {
		return this.accountActiveBorrows;
	}

	public void setAccountActiveBorrows(int accountActiveBorrows) {
		this.accountActiveBorrows = accountActiveBorrows;
	}

	public Timestamp getAccountAddDate() {
		return this.accountAddDate;
	}

	public void setAccountAddDate(Timestamp accountAddDate) {
		this.accountAddDate = accountAddDate;
	}

	public String getAccountEmail() {
		return this.accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public String getAccountFirstName() {
		return this.accountFirstName;
	}

	public void setAccountFirstName(String accountFirstName) {
		this.accountFirstName = accountFirstName;
	}

	public Timestamp getAccountLastLoginDate() {
		return this.accountLastLoginDate;
	}

	public void setAccountLastLoginDate(Timestamp accountLastLoginDate) {
		this.accountLastLoginDate = accountLastLoginDate;
	}

	public String getAccountLastName() {
		return this.accountLastName;
	}

	public void setAccountLastName(String accountLastName) {
		this.accountLastName = accountLastName;
	}

	public String getAccountPassword() {
		return this.accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public String getAccountStatus() {
		return this.accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public int getAccountTotalBorrows() {
		return this.accountTotalBorrows;
	}

	public void setAccountTotalBorrows(int accountTotalBorrows) {
		this.accountTotalBorrows = accountTotalBorrows;
	}

	public List<Borrow> getBorrows() {
		return this.borrows;
	}

	public void setBorrows(List<Borrow> borrows) {
		this.borrows = borrows;
	}

	public Borrow addBorrow(Borrow borrow) {
		getBorrows().add(borrow);
		borrow.setAccount(this);

		return borrow;
	}

	public Borrow removeBorrow(Borrow borrow) {
		getBorrows().remove(borrow);
		borrow.setAccount(null);

		return borrow;
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Card addCard(Card card) {
		getCards().add(card);
		card.setAccount(this);

		return card;
	}

	public Card removeCard(Card card) {
		getCards().remove(card);
		card.setAccount(null);

		return card;
	}

	public List<Catalog> getCatalogs() {
		return this.catalogs;
	}

	public void setCatalogs(List<Catalog> catalogs) {
		this.catalogs = catalogs;
	}

	public Catalog addCatalog(Catalog catalog) {
		getCatalogs().add(catalog);
		catalog.setAccount(this);

		return catalog;
	}

	public Catalog removeCatalog(Catalog catalog) {
		getCatalogs().remove(catalog);
		catalog.setAccount(null);

		return catalog;
	}

	public List<Setting> getSettings() {
		return this.settings;
	}

	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}

	public Setting addSetting(Setting setting) {
		getSettings().add(setting);
		setting.setAccount(this);

		return setting;
	}

	public Setting removeSetting(Setting setting) {
		getSettings().remove(setting);
		setting.setAccount(null);

		return setting;
	}

}