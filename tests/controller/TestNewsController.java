package controller;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.util.test.MockSerializationResult;
import br.com.homebroker.controller.NewsController;
import br.com.homebroker.model.Active;
import br.com.homebroker.model.News;
import br.com.homebroker.model.NewsSearcher;
import br.com.homebroker.model.util.DateConvertor;
import br.com.homebroker.service.NewsService;
import br.com.homebroker.util.exceptions.NothingFoundException;

public class TestNewsController {

	@Mock
	private NewsService service;
	private Active active;
	private News news1;
	private News news2;
	private MockSerializationResult result;
	private NewsController controller;
	private String json;
	@Mock
	private NewsSearcher searcherService;
	
	@Before
	public void testNewsController(){
		MockitoAnnotations.initMocks(this);
		mocks();
		DateConvertor convertor = Mockito.mock(DateConvertor.class);
		Mockito.when(convertor.convertStringToDate(Mockito.anyString())).thenReturn(Calendar.getInstance().getTime());
		this.controller = new NewsController(service, searcherService, convertor, result);
	}
	
	private void mockService(){
		Mockito.when(service.search(1L)).thenReturn(news1);
		Mockito.when(service.search(2L)).thenReturn(news2);
		Mockito.when(service.search(3L)).thenThrow(new NothingFoundException());
		List<News> listNews = new ArrayList<News>();
		listNews.add(news1);
		listNews.add(news2);
		Mockito.when(service.searchByActive(active)).thenReturn(listNews);
		Mockito.when(service.search(Mockito.any(Active.class), Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(listNews);
	}
	
	private void mocks(){
		active = new Active("1A", "pa", "ca");
		news1 = new News(active, Calendar.getInstance().getTime(), "Manchete", "Texto", "link");
		news1.setId(1L);
		news2 = new News(active, Calendar.getInstance().getTime(), "Manchete", "Texto", "link");
		news2.setId(2L);
		mockService();
		result = new MockSerializationResult();
		json = "{\"id\": 1,\"active\": {\"code\": \"1A\",\"pregao\": \"pa\",\"company\": \"ca\"},\"headline\": \"Manchete\",\"text\": \"Texto\",\"link\": \"link\"}";
	}
	
	@Test
	public void testSearchById() throws Exception{
		this.controller.search(1L);
		assertEquals(json, this.result.serializedResult());
	}
	
	@Test
	public void testSecondSearchById() throws Exception{
		this.controller.search(2L);
		json = json.replace("\"id\": 1", "\"id\": 2");
		assertEquals(json, this.result.serializedResult());
	}
	
	@Test
	public void testSearchByIdAndFoundNoNews() throws Exception{
		this.controller.search(3L);
		json = "{\"message\": \"Nenhum resultado encontrado\"}";
		assertEquals(json, this.result.serializedResult());
	}
	
	@Test
	public void searchNewsByActive() throws Exception{
		this.controller.search(active);
		json = "["+ json + "," + json.replace("\"id\": 1", "\"id\": 2") + "]";
		assertEquals(json, this.result.serializedResult());
	}
	
	@Test
	public void searchByActiveButFoundAnyNews() throws Exception{
		Mockito.when(service.searchByActive(active)).thenThrow(new NothingFoundException());
		this.controller.search(active);
		json = "{\"message\": \"Nenhum resultado encontrado\"}";
		assertEquals(json, this.result.serializedResult());
	}
	
	@Test
	public void searchByPeriod() throws Exception{
		this.controller.search(active, "10-11-2011", "12-11-2011");
		json = "["+ json + "," + json.replace("\"id\": 1", "\"id\": 2") + "]";
		assertEquals(json, this.result.serializedResult());
	}
	
	@Test
	public void searchByPeriodAndDoNotFound() throws Exception{
		Mockito.when(service.search(Mockito.any(Active.class), Mockito.any(Date.class), Mockito.any(Date.class))).thenThrow(new NothingFoundException());
		controller.search(active, "10-11-2011", "12-11-2011");
		json = "{\"message\": \"Nenhum resultado encontrado\"}";
		assertEquals(json, this.result.serializedResult());
	}
	
	@Test
	public void searchExternalNewsListAndSave(){
		List<News> listNews = new ArrayList<News>();
		listNews.add(news1);
		listNews.add(news2);
		Mockito.when(searcherService.getNews()).thenReturn(listNews);
		controller.searchExternalNews();
	}
}
