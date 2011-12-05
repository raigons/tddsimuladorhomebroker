package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.homebroker.model.Active;
import br.com.homebroker.model.News;
import br.com.homebroker.repository.RepositoryFactory;
import br.com.homebroker.repository.RepositoryInterface;
import br.com.homebroker.service.NewsService;
import br.com.homebroker.service.Service;
import br.com.homebroker.util.exceptions.NothingFoundException;

public class TestNewsService {

	private RepositoryFactory factory;
	
	@SuppressWarnings("rawtypes")
	private RepositoryInterface repository;
	
	@SuppressWarnings("unused")
	private Service<News> service;
	
	@SuppressWarnings("unchecked")
	@Before
	public void initObjects(){
		this.factory = Mockito.mock(RepositoryFactory.class);
		this.repository = Mockito.mock(RepositoryInterface.class);
		Active active = Mockito.mock(Active.class);
		Mockito.when(active.getCode()).thenReturn("2A");
		News news = Mockito.mock(News.class);
		Mockito.when(news.getActive()).thenReturn(active);
		Mockito.when(news.getActiveCode()).thenReturn("2A");
		
		Mockito.when(news.getId()).thenReturn(1L);		
		Mockito.when(repository.save(Mockito.any(News.class))).thenReturn(true);
		Mockito.when(repository.listAll()).thenThrow(new RuntimeException());
		Mockito.when(repository.search(1L)).thenReturn(news);
		Mockito.when(repository.seach(Mockito.anyString())).thenReturn(news);
		
		List<News> listNews = Mockito.mock(List.class);
		Mockito.when(listNews.get(0)).thenReturn(news);
		Mockito.when(repository.search(Mockito.any(Active.class))).thenReturn(listNews);
		Mockito.when(factory.getRepository(Mockito.anyString())).thenReturn(this.repository);		
	}
	
	@Test
	public void testSave(){
		Service<News> service = new NewsService(this.factory);
		News news = Mockito.mock(News.class);
		assertTrue(service.save(news));
	}
	
	@Test(expected=RuntimeException.class) 
	public void testListAll(){
		Service<News> service = new NewsService(factory);
		service.listAll();
		fail("Should have been thrown an exception cause News cannot be listed without a filter");
	}
	
	@Test(expected=NothingFoundException.class)
	public void testGetANews(){
		Service<News> service = new NewsService(factory);
		service.search(2L);
		fail("Should have been thrown an exception cause no News with this id exists.");
	}
	
	@Test
	public void searchByActive(){
		Service<News> service = new NewsService(factory);
		Active active = Mockito.mock(Active.class);
		Mockito.when(active.getCode()).thenReturn("2A");
		List<News> list = service.searchByActive(active);
		assertEquals(active.getCode(), list.get(0).getActiveCode());
		assertEquals(active.getCode(), list.get(0).getActive().getCode());
	}
}
