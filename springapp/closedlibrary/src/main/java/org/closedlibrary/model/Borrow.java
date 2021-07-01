package org.closedlibrary.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the borrow database table.
 * 
 */
@Entity
@NamedQuery(name="Borrow.findAll", query="SELECT b FROM Borrow b")
public class Borrow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int borrowId;

	private Timestamp borrowAddDate;

	private Timestamp borrowDueDate;

	private int borrowQueueNumber;

	private String borrowQueueStatus;

	private String borrowStatus;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="accountId")
	private Account account;

	//bi-directional many-to-one association to Book
	@ManyToOne
	@JoinColumn(name="bookId")
	private Book book;

	public Borrow() {
	}

	public int getBorrowId() {
		return this.borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}

	public Timestamp getBorrowAddDate() {
		return this.borrowAddDate;
	}

	public void setBorrowAddDate(Timestamp borrowAddDate) {
		this.borrowAddDate = borrowAddDate;
	}

	public Timestamp getBorrowDueDate() {
		return this.borrowDueDate;
	}

	public void setBorrowDueDate(Timestamp borrowDueDate) {
		this.borrowDueDate = borrowDueDate;
	}

	public int getBorrowQueueNumber() {
		return this.borrowQueueNumber;
	}

	public void setBorrowQueueNumber(int borrowQueueNumber) {
		this.borrowQueueNumber = borrowQueueNumber;
	}

	public String getBorrowQueueStatus() {
		return this.borrowQueueStatus;
	}

	public void setBorrowQueueStatus(String borrowQueueStatus) {
		this.borrowQueueStatus = borrowQueueStatus;
	}

	public String getBorrowStatus() {
		return this.borrowStatus;
	}

	public void setBorrowStatus(String borrowStatus) {
		this.borrowStatus = borrowStatus;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}