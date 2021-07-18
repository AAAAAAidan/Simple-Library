package org.simplelibrary.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPQL {
	
	private static final EntityManagerFactory EM_FACTORY = Persistence.createEntityManagerFactory("library");
	private static final EntityManager EM = EM_FACTORY.createEntityManager();
	
	public static EntityManager getEntityManager() {
		return EM;
	}
	
}
