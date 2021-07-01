package org.closedlibrary.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.closedlibrary.model.Account;

// I will eventually turn this into a real controller
public class AccountController {
	
	private static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("closedlibrary");  
	private static EntityManager em = emfactory.createEntityManager();  
	
	public static void main( String[ ] args ) {
		int id = createAccount();
		updateAccount(id);
		selectAccount(id);
		deleteAccount(id);
		em.close();
		emfactory.close();
	}
	
	public static int createAccount() {
		em.getTransaction().begin();  
		Account account = new Account();
		account.setAccountFirstName("Hee");
		account.setAccountLastName("Ho");
		account.setAccountPassword("Word");
		em.persist(account);
		em.getTransaction().commit();
		System.out.println("Added account");
		return account.getAccountId();
	}

	public static void updateAccount(int id) {
		em.getTransaction().begin();
		Account account = em.find(Account.class, id);
		account.setAccountEmail("bufula@mega.ten");
		em.getTransaction().commit( );
		System.out.println("Updated account");
	}

	public static void selectAccount(int id) {
		Account account = em.find(Account.class, id);
		System.out.println("Selected account");
	}

	public static void deleteAccount(int id) {
		em.getTransaction().begin();
		Account account = em.find(Account.class, id);
		em.remove(account);
		em.getTransaction().commit();
		System.out.println("Deleted account");
	}

}
