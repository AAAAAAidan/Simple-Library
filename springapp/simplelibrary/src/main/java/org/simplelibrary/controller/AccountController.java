package org.simplelibrary.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.simplelibrary.model.Account;
import org.simplelibrary.util.JPQL;
import org.simplelibrary.util.Table;
import org.springframework.stereotype.Controller;

// I will eventually turn this into a real controller
@Controller
public class AccountController {
	/*
	private static final EntityManager EM = JPQL.getEntityManager();  
	
	public static void main( String[ ] args ) throws SQLException {
		
		// Example usage of Table.java and JDBC
		
		Table table = new Table("Book");
		table.insert(new String[] {"bookId", "bookTitle"}, new String[] {"ID", "Title"});
		table = table.filter("bookId = 'ID'");
		table.update("bookTitle", "New Title");
		ResultSet result = table.select("bookTitle");
		result.next();
		System.out.println(result.getString(1));
		table.delete();
		
		// Example usage of JPA (TODO REMOVE)
		
		int id = createAccount();
		updateAccount(id);
		selectAccount(id);
		deleteAccount(id);
		
		// Example usage of Entity.java and JPQL (TODO ADD)
		
		
		
		EM.close();
	}
	
	public static int createAccount() {
		EM.getTransaction().begin();
		Account account = new Account();
		account.setAccountFirstName("Hee");
		account.setAccountLastName("Ho");
		account.setAccountPassword("Word");
		EM.persist(account);
		EM.getTransaction().commit();
		System.out.println("Added account");
		return account.getAccountId();
	}

	public static void updateAccount(int id) {
		EM.getTransaction().begin();
		Account account = EM.find(Account.class, id);
		account.setAccountEmail("bufula@mega.ten");
		EM.getTransaction().commit( );
		System.out.println("Updated account");
	}

	public static void selectAccount(int id) {
		EM.find(Account.class, id);
		System.out.println("Selected account");
	}

	public static void deleteAccount(int id) {
		EM.getTransaction().begin();
		Account account = EM.find(Account.class, id);
		EM.remove(account);
		EM.getTransaction().commit();
		System.out.println("Deleted account");
	}
	*/

}
