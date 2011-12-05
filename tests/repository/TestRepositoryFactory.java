package repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hibernate.Session;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.homebroker.repository.RepositoryFactory;
import br.com.homebroker.repository.dao.DaoActive;
import br.com.homebroker.repository.dao.DaoDataDays;
import br.com.homebroker.repository.dao.DaoFactory;

public class TestRepositoryFactory {

	@Test
	public void testFactory(){
		Session session = Mockito.mock(Session.class);
		
		RepositoryFactory factory = new DaoFactory(session);
		
		assertTrue(factory.getRepository("Active") instanceof DaoActive);
		
		assertTypeComparative("Active", "Diary", "Intraday", factory);
		
		assertFalse(factory.getRepository("Diary") instanceof DaoActive);
		
		assertTrue(factory.getRepository("Diary") instanceof DaoDataDays);
		
		assertTypeComparative("Diary", "Active", "Intraday", factory);
		
		assertTrue(factory.getRepository("Intraday") instanceof DaoDataDays);
		
		assertTypeComparative("Intraday", "Diary", "Active", factory);
	}
	
	private void assertTypeComparative(String type, String type2, String type3, RepositoryFactory factory){
		assertEquals(type, factory.getRepository(type).getObjectClassName());
		assertFalse(type2.equals(factory.getRepository(type).getObjectClassName()));
		assertFalse(type3.equals(factory.getRepository(type).getObjectClassName()));
	}

	@Test(expected=RuntimeException.class)
	public void testTryToGetARepositoryThatDoesNotExist(){
		Session session = Mockito.mock(Session.class);
		RepositoryFactory factory = new DaoFactory(session);
		factory.getRepository("Noone");		
		fail("Should have been thrown an exception cause this type of Repository does not exist");
	}
	
	@Test(expected=RuntimeException.class)
	public void testGetDatadayObjectButThisIsNotPossibleCauseItIsAAbstractClass(){
		Session session = Mockito.mock(Session.class);
		RepositoryFactory factory = new DaoFactory(session);
		factory.getRepository("DataDays");
		fail("Should have been thrown an exception!");
	}
	
	@Test
	public void testNewsRepository(){
		Session session = Mockito.mock(Session.class);
		RepositoryFactory factory = new DaoFactory(session);		
		assertEquals("News", factory.getRepository("News").getObjectClassName());
	}

}
