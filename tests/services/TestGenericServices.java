package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.homebroker.model.Active;
import br.com.homebroker.model.DataDays;
import br.com.homebroker.model.Diary;
import br.com.homebroker.model.Intraday;
import br.com.homebroker.repository.RepositoryFactory;
import br.com.homebroker.repository.RepositoryInterface;
import br.com.homebroker.service.ActiveService;
import br.com.homebroker.service.DatayDaysService;
import br.com.homebroker.service.Service;
import br.com.homebroker.util.exceptions.NothingFoundException;

public class TestGenericServices {
	
	@SuppressWarnings("rawtypes")
	private RepositoryInterface repository;	
	
	private RepositoryFactory factory;
	@Before
	public void initService(){
		repository = Mockito.mock(RepositoryInterface.class);
		
		DataDays intraday = Mockito.mock(Intraday.class);
		Mockito.when(intraday.getId()).thenReturn(1L);
		Mockito.when(intraday.getActiveCode()).thenReturn("10A");
		
		DataDays diary = Mockito.mock(Diary.class);
		Mockito.when(diary.getId()).thenReturn(3L);
		Mockito.when(diary.getActiveCode()).thenReturn("15A");
		
		
		Active active = Mockito.mock(Active.class);
		Mockito.when(active.getCode()).thenReturn("12A");
		
		Mockito.when(repository.search(1L)).thenReturn(intraday);
		Mockito.when(repository.search(3L)).thenReturn(diary);
		
		Mockito.when(repository.seach("10A")).thenReturn(intraday);
		Mockito.when(repository.seach("15A")).thenReturn(diary);
		Mockito.when(repository.seach("12A")).thenReturn(active);
		
		this.factory = Mockito.mock(RepositoryFactory.class);				
		Mockito.when(factory.getRepository(Mockito.anyString())).thenReturn(this.repository);
	}
	
	@Test
	public void testGetOneIntradayFromDatabaseByIdAndActiveCode(){
		Service<Diary> service = new DatayDaysService<Diary>(factory){};
		assertEquals(Long.valueOf(3), (service.search(3L)).getId());
		assertEquals("15A", (service.search("15A")).getActiveCode());
	}
	
	@Test(expected=NothingFoundException.class)
	public void testNotFoundAnyIntradayByCode(){
		Service<Active> service = new ActiveService(factory);		
		service.search("11A");
		fail("Should have been thrown an exception cause no Intraday was found");
	}
	
	@Test(expected=NothingFoundException.class)
	public void testNotFoundAnyIntradayById(){
		Service<Intraday> service = new DatayDaysService<Intraday>(factory){};
		service.search(2L);
		fail("Should have been thrown an exception cause no Intraday was found");
	}
	
	@Test
	public void testGetOneActiveByCode(){
		Service<Active> service = new ActiveService(factory);		
		assertEquals("12A", service.search("12A").getCode());
	}
	
	@Test(expected=NothingFoundException.class)
	public void testNotFoundAnyActiveByCode(){
		Service<Active> service = new ActiveService(factory);		
		service.search("11A");
		fail("Should have been thrown an exception cause no Active was found");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteActive(){
		Service<Active> service = new ActiveService(factory);		
		Active active = Mockito.mock(Active.class);
		//Mockito.when(active.getCode()).thenReturn("11A");
		Mockito.when(this.repository.delete(active)).thenReturn(true);
		assertTrue(service.delete(active));		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSaveActive(){
		Active active = Mockito.mock(Active.class);
		Mockito.when(this.repository.save(active)).thenReturn(true);
		Service<Active> service = new ActiveService(factory);
		assertTrue(service.save(active));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSaveDatadays(){
		Intraday intraday = Mockito.mock(Intraday.class);
		Diary diary = Mockito.mock(Diary.class);
		Mockito.when(this.repository.save(Mockito.any(DataDays.class))).thenReturn(true);
		Service<Intraday> serviceIntraday = new DatayDaysService<Intraday>(factory){};
		Service<Diary> serviceDiary = new DatayDaysService<Diary>(factory){};
		assertTrue(serviceIntraday.save(intraday));
		assertTrue(serviceDiary.save(diary));
	}
	
		
}
