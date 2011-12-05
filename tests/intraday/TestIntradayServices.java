package intraday;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.homebroker.model.Active;
import br.com.homebroker.model.Intraday;
import br.com.homebroker.repository.dao.Dao;
import br.com.homebroker.service.IntradayService;
import br.com.homebroker.util.Param;
import br.com.homebroker.util.SearchParams;

public class TestIntradayServices {
	
	private IntradayService service;
	
	private Dao repository;
	
	private void mockRepository(){
		this.repository = Mockito.mock(Dao.class);
	}
	
	@Before
	public void initController(){
		mockRepository();
		this.service = new IntradayService(repository);
	}
	
	@Test
	public void testIntradayInit(){
		assertNotNull(service);
	}
	
	private Intraday mockIntraday(String activeCode){
		Intraday intraday = Mockito.mock(Intraday.class);
		Mockito.when(intraday.getActiveCode()).thenReturn(activeCode);
		Mockito.when(intraday.getOpen()).thenReturn(10.01);
		Mockito.when(intraday.getClose()).thenReturn(12.0);
		Mockito.when(intraday.getMax()).thenReturn(13.0);
		Mockito.when(intraday.getMin()).thenReturn(9.5);
		Mockito.when(intraday.getVolume()).thenReturn(15303L);
		return intraday;
	}
	
	@Test
	public void testSearchIntradayService(){
		Active active = Mockito.mock(Active.class);
		Mockito.when(active.getCode()).thenReturn("10A");
		String activeCode = active.getCode();
		Intraday intraday = mockIntraday(activeCode);
		
		Param<Active> param = Mockito.mock(Param.class);
		Mockito.when(param.getParam()).thenReturn(active);
		Calendar date1 = Mockito.mock(Calendar.class);
		Calendar date2 = Mockito.mock(Calendar.class);
		
		SearchParams searchParams = Mockito.mock(SearchParams.class);
		
		Mockito.when(this.repository.search(searchParams)).thenReturn(intraday);
		
		assertSame(intraday, this.service.search(searchParams));
		assertEquals(active.getCode(), this.service.search(searchParams).getActiveCode());
		assertEquals(10.01, this.service.search(searchParams).getOpen(), 0.0000001);
	}

}
