package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.homebroker.repository.RepositoryFactory;
import br.com.homebroker.service.ActiveService;
import br.com.homebroker.service.DatayDaysService;
import br.com.homebroker.service.NewsService;
import br.com.homebroker.service.ServiceFactory;

public class TestServiceFactory {
	
	private static ServiceFactory serviceFactory;
	
	@BeforeClass
	public static void initServiceFactory(){
		RepositoryFactory factory = Mockito.mock(RepositoryFactory.class);
		serviceFactory = new ServiceFactory(factory);	
	}
	
	@Test
	public void testCreateIntradayService(){
		assertTrue(serviceFactory.getService("intraday") instanceof DatayDaysService);
		assertEquals("Intraday", serviceFactory.getService("intraday").getClassTypeName());
	}
	
	@Test
	public void testCreateDiaryService(){
		assertTrue(serviceFactory.getService("Diary") instanceof DatayDaysService);
		assertEquals("Diary", serviceFactory.getService("diary").getClassTypeName());
	}
	
	@Test
	public void testCreateActiveService(){
		assertTrue(serviceFactory.getService("Ativo") instanceof ActiveService);
		assertEquals("Active", serviceFactory.getService("AtiVo").getClassTypeName());
	}
	
	@Test
	public void testCreateNewsService(){
		assertTrue(serviceFactory.getService("news") instanceof NewsService);
		assertEquals("News", serviceFactory.getService("news").getClassTypeName());
	}
	
	@Test(expected=RuntimeException.class)
	public void testTryToInstantiateATypeOfServiceThatDoesNotExist(){
		serviceFactory.getService("Try");
		fail("Should have been thrown an exception cause no Service of type Try exists");
	}
}
