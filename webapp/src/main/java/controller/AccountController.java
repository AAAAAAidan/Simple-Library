package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Account;

public class AccountController {
	
	private static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("closedlibrary");  
	private static EntityManager em = emfactory.createEntityManager();  
	
	public static void main( String[ ] args ) {
		
		// Create
		
		em.getTransaction().begin();  
		
		Account account1 = new Account(); 
		account1.setAccountEmail("aaaa");;
		
		em.persist(account1);
		em.getTransaction().commit();
		em.close();
		
		// Update
		
		em.getTransaction( ).begin( );
		Account account = em.find(Account.class,1056);
		
		System.out.println("Before Updation");
		System.out.println("account email = " + account.getAccountEmail());
		
		System.out.println("After Updation");
		account.setAccountEmail("xyz01@prscholas.org");
		em.getTransaction().commit( );
		
		System.out.println("employee EMAIL = " + account.getAccountEmail());
		em.close();
		emfactory.close( );
		
		// Find
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "jpa" );
		EntityManager entitymanager = emfactory.createEntityManager();
		Account account3 = entitymanager.find(Account.class,1056);

		System.out.println("employee ID = " + account3.getAccountEmail());
		
		// Delete
		
		entitymanager.getTransaction( ).begin( );
		  
		Account account4 = entitymanager.find( Account.class, 24 );
		entitymanager.remove( account4 );
		entitymanager.getTransaction( ).commit( );
		
		entitymanager.close( );
		emfactory.close( );
		
	}

}
