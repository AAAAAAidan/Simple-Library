package org.simplelibrary.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class DatabaseConnectionUtilTests extends DatabaseConnection {
	
	@Test
	@Order(1)
	void testConnect() {
		this.connect();
		assertNotNull(entityManager);
	}

	@Test
	@Order(1)
	void testDisconnect() {
		this.disconnect();
		assertNull(entityManager);
	}

}
