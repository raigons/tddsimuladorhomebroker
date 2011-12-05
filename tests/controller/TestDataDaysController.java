package controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.util.test.MockSerializationResult;
import br.com.homebroker.controller.DatadaysController;
import br.com.homebroker.model.Active;
import br.com.homebroker.model.DataDays;
import br.com.homebroker.model.Diary;
import br.com.homebroker.model.Intraday;
import br.com.homebroker.model.util.DateConvertor;
import br.com.homebroker.service.Service;
import br.com.homebroker.service.ServiceFactory;
import br.com.homebroker.util.exceptions.NothingFoundException;

public class TestDataDaysController {

	private DatadaysController controller;
	
	private MockSerializationResult result;
	
	private List<Intraday> intradays;
	
	private List<Diary> diaryList;
	
	@Mock
	private ServiceFactory serviceFactory;
	
	@Mock
	private Service<Intraday> serviceIntraday;
	
	@Mock
	private Service<Diary> serviceDiary;
	
	@Mock
	private Active active;
	
	private String json;
	
	@Before
	public void initController(){
		this.result = new MockSerializationResult();

		MockitoAnnotations.initMocks(this);
		
		this.intradays = new ArrayList<Intraday>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, 10, 10, 0, 0, 0);
		
		this.intradays.add(new Intraday(4.0, 1.0, 3.0, 3.0, 500L, new Active("2A", "pregao", "bonitura"), calendar.getTime()));
		
		this.diaryList = new ArrayList<Diary>();
		this.diaryList.add(new Diary(4.0, 1.0, 3.0, 3.0, 500L, new Active("2A", "pregao", "bonitura"), calendar.getTime()));
		
		this.json = "[{\"max\": 4.0,\"min\": 1.0,\"close\": 3.0,\"open\": 3.0,\"volume\": 500,\"active\": {\"code\": \"2A\",\"pregao\": \"pregao\",\"company\": \"bonitura\"}}]";
		
		Mockito.when(serviceIntraday.searchByActive(active)).thenReturn(null);
		Mockito.when(serviceIntraday.search(Mockito.any(Active.class), Mockito.any(Date.class), Mockito.any(Date.class)))
		.thenReturn(this.intradays);
		Mockito.when(serviceIntraday.save(Mockito.any(Intraday.class))).thenReturn(true);
		
		Mockito.when(serviceDiary.searchByActive(active)).thenReturn(null);
		Mockito.when(serviceDiary.search(Mockito.any(Active.class), Mockito.any(Date.class), Mockito.any(Date.class)))
		       .thenReturn(diaryList);
		Mockito.when(serviceDiary.save(Mockito.any(Diary.class))).thenReturn(false);
		
		DateConvertor convertor = Mockito.mock(DateConvertor.class);
		Mockito.when(convertor.convertStringToDate(Mockito.anyString())).thenReturn(Calendar.getInstance().getTime());
		this.controller = new DatadaysController(this.serviceFactory, convertor, result);		
	}
	
	@Test
	public void testDataDayControllerSearchingAIntradayByActive() throws Exception{
		Mockito.when(this.serviceFactory.getService(Mockito.anyString())).thenReturn(serviceIntraday);		
		Mockito.when(active.getCode()).thenReturn("2A");
		String beginDate = "01-10-2011";
		String endDate = "12-10-2011";
		this.controller.search(active, "intraday", beginDate, endDate);
		String serialized = this.result.serializedResult();
		assertEquals(json, serialized);
	}
	
	@Test
	public void testDataDayControllerSearchingDiaryByActive() throws Exception{
		Mockito.when(this.serviceFactory.getService(Mockito.anyString())).thenReturn(serviceDiary);
		String beginDate = "01-10-2011";
		String endDate = "12-10-2011";
		this.controller.search(active, "diary", beginDate, endDate);
		String serialized = this.result.serializedResult();
		assertEquals(json, serialized);
	}

	@Test
	public void testNotFoundIntraday() throws Exception{
		this.intradays.clear();
		Mockito.when(this.serviceFactory.getService(Mockito.anyString())).thenReturn(serviceIntraday);
		Mockito.when(serviceIntraday.search(Mockito.any(Active.class), Mockito.any(Date.class), Mockito.any(Date.class)))
		.thenThrow(new NothingFoundException());		
		String type = "intraday";
		String beginDate = "01-10-2011";
		String endDate = "13-10-2011";
		this.controller.search(active, type, beginDate, endDate);
		String serialized = this.result.serializedResult();
		assertEquals("{\"message\": \"Nenhum resultado encontrado\"}", serialized);
	}
	
	@Test
	public void testControllerMethodSave() throws Exception{
		Mockito.when(this.serviceFactory.getService(Mockito.anyString())).thenReturn(serviceIntraday);
		String type = "intraday";
		DataDays dataday = Mockito.mock(Intraday.class);
		this.controller.save(type, dataday);
		String serializedResult = this.result.serializedResult();
		assertEquals("{\"message\": \"Dados armazenados com sucesso\"}", serializedResult);		
	}

	@Test
	public void verifyInvalidType() throws Exception{
		String beginDate = "01-10-2011";
		String endDate = "12-10-2011";
		this.controller.search(active, "teste", beginDate, endDate);
		String serializedResult = this.result.serializedResult();
		assertEquals("{\"typeError\": \"O link deve ser no formato /diary/ ou /intraday/\"}", serializedResult);
	}
	

	
	@Test
	public void testControllerSaveDiaryFail() throws Exception{	
		Mockito.when(this.serviceFactory.getService(Mockito.anyString())).thenReturn(serviceDiary);
		String type = "diary";
		DataDays dataday = Mockito.mock(Diary.class);
		this.controller.save(type, dataday);
		String serializedResult = this.result.serializedResult();
		assertEquals("{\"message\": \"Falha na inserção dos dados\"}", serializedResult);
	}

}
