package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.homebroker.model.Active;
import br.com.homebroker.model.DataDays;
import br.com.homebroker.model.Intraday;
import br.com.homebroker.repository.RepositoryFactory;
import br.com.homebroker.repository.dao.Dao;
import br.com.homebroker.service.DatayDaysService;
import br.com.homebroker.util.exceptions.NothingFoundException;

public class TestIntradayService {
	
	@Mock
	private Intraday datadays;	
	
	@Mock
	private Date begin;
	
	@Mock
	private Date end;
	
	@Mock
	private Active active;
	
	@Mock
	private Dao repository;
	
	@Mock
	private RepositoryFactory factory;
	
	private DatayDaysService<Intraday> service;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		Mockito.when(this.datadays.getActiveCode()).thenReturn("10A");
		Mockito.when(active.getCode()).thenReturn("10A");
		Mockito.when(factory.getRepository(Mockito.anyString())).thenReturn(repository);
		service = new DatayDaysService<Intraday>(factory){};
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testDataFilter(){
		List<DataDays> listDatadays = Mockito.mock(List.class);
		Mockito.when(listDatadays.get(0)).thenReturn(this.datadays);
		Mockito.when(repository.search(active, begin, end)).thenReturn(listDatadays);
	
		assertEquals(active.getCode(), service.search(active, begin, end).get(0).getActiveCode());
	}
	
	@Test(expected=NothingFoundException.class)
	public void testFoundNoIntraday(){
		List<DataDays> list = Mockito.mock(List.class);
		Mockito.when(list.isEmpty()).thenReturn(true);
		Mockito.when(repository.search(active, begin, end)).thenReturn(list);
		service.search(active, begin, end);
		fail("Should have been thrown an NothingFoundException cause list is empty");
	}
	
}
 