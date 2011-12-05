package searchparams;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;
import org.mockito.Mockito;

import antlr.collections.List;
import br.com.homebroker.model.Active;
import br.com.homebroker.util.ActiveSearchParams;
import br.com.homebroker.util.IntradaySearchParams;
import br.com.homebroker.util.Param;
import br.com.homebroker.util.SearchParams;

public class TestSearchParams {

	private SearchParams params;
	
	private void initIntradaySearchParams(){
		this.params = new IntradaySearchParams();
	}
	
	private void initActiveSearchParams(){
		this.params = new ActiveSearchParams();
	}
	
	@Test
	public void testIntradayActiveParam(){		
		Active active = Mockito.mock(Active.class);
		Mockito.when(active.getCode()).thenReturn("10A");		
		
		Param<Active> activeParam = new Param<Active>(active);
		assertSame(active, activeParam.getParam());
	}
	
	@Test
	public void tesIntradayDateParam(){
		Calendar date = Mockito.mock(Calendar.class);
		Param<Calendar> calendarParam = new Param<Calendar>(date);
		assertSame(date, calendarParam.getParam());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testIntradayParams(){
		initIntradaySearchParams();
		Active active = Mockito.mock(Active.class);
		Calendar date1 = Mockito.mock(Calendar.class);
		Calendar date2 = Mockito.mock(Calendar.class);
		Param<Active> paramActive = Mockito.mock(Param.class);
		Mockito.when(paramActive.getParam()).thenReturn(active);
		Param<Calendar> paramDate1 = Mockito.mock(Param.class);
		Mockito.when(paramDate1.getParam()).thenReturn(date1);
		Param<Calendar> paramDate2 = Mockito.mock(Param.class);
		Mockito.when(paramDate2.getParam()).thenReturn(date2);
		
		ArrayList<Param> listParams = Mockito.mock(ArrayList.class);
		Mockito.when(listParams.get(0)).thenReturn(paramActive);
		Mockito.when(listParams.get(1)).thenReturn(paramDate1);
		Mockito.when(listParams.get(2)).thenReturn(paramDate2);
		Mockito.when(listParams.size()).thenReturn(3);
	}
	
	@SuppressWarnings({"unchecked" })
	@Test
	public void testActiveSearchParams(){
		initActiveSearchParams();
		Param<String> paramString = Mockito.mock(Param.class);
		Mockito.when(paramString.getParam()).thenReturn("Ramon");
		this.params.setParams(paramString);
		assertEquals(Integer.valueOf(1), this.params.size());
		assertTrue(this.params.validParams());
	}
		
	@Test(expected=RuntimeException.class)
	public void testReadIntradayParamsInsideSearchParams(){
		initIntradaySearchParams();
		Param<Active> paramActive = Mockito.mock(Param.class);
		Mockito.when(paramActive.getParam()).thenReturn(new Active());
		Param<Calendar> paramDate1 = Mockito.mock(Param.class);
		Mockito.when(paramDate1.getParam()).thenReturn(Calendar.getInstance());
		Param<Calendar> paramDate2 = Mockito.mock(Param.class);
		Mockito.when(paramDate2.getParam()).thenReturn(Calendar.getInstance());

		ArrayList<Param> list = Mockito.mock(ArrayList.class);
		Mockito.when(list.get(0)).thenReturn(paramActive);
		Mockito.when(list.get(1)).thenReturn(paramDate1);
		Mockito.when(list.get(2)).thenReturn(paramActive);
		Mockito.when(list.size()).thenReturn(3);
		
		this.params.setParams(list);
		fail("It should have thrown one exception cause the third Intraday Param isn't a Calendar");		
	}
	
	@Test
	public void testGetAllParams(){
		initIntradaySearchParams();
		Param<Active> paramActive = Mockito.mock(Param.class);
		Mockito.when(paramActive.getParam()).thenReturn(new Active());
		Param<Calendar> paramDate1 = Mockito.mock(Param.class);
		Mockito.when(paramDate1.getParam()).thenReturn(Calendar.getInstance());
		Param<Calendar> paramDate2 = Mockito.mock(Param.class);
		Mockito.when(paramDate2.getParam()).thenReturn(Calendar.getInstance());
		ArrayList<Param> list = Mockito.mock(ArrayList.class);
		Mockito.when(list.get(0)).thenReturn(paramActive);
		Mockito.when(list.get(1)).thenReturn(paramDate1);
		Mockito.when(list.get(2)).thenReturn(paramDate2);
		Mockito.when(list.size()).thenReturn(3);
		
		this.params.setParams(list);
		assertSame(paramActive, this.params.getParams().get(0));
		assertSame(paramDate1, this.params.getParams().get(1));
		this.params = null;
		initActiveSearchParams();
		Param<String> paramString = Mockito.mock(Param.class);
		Mockito.when(paramString.getParam()).thenReturn("Ramon");
		this.params.setParams(paramString);	
		assertEquals("Ramon", this.params.getParams().get(0).getParam());
	}
	
}
