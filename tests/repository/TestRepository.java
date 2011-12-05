package repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.homebroker.model.Active;
import br.com.homebroker.model.Intraday;
import br.com.homebroker.model.News;
import br.com.homebroker.repository.RepositoryInterface;
import br.com.homebroker.repository.dao.Dao;
import br.com.homebroker.repository.dao.DaoActive;
import br.com.homebroker.repository.dao.DaoDataDays;
import br.com.homebroker.repository.dao.DaoNews;
import br.com.homebroker.util.SearchParams;

public class TestRepository {

	private Dao<Active> repository;
	
	@Before
	public void initRepository(){
		this.repository = Mockito.mock(Dao.class);
	}

	@Test
	public void testRepositorySearch(){
		SearchParams params = Mockito.mock(SearchParams.class);
		this.repository.search(params);
	}

	@Test(expected=RuntimeException.class)
	public void testActiveRepository(){
		Session session = Mockito.mock(Session.class);		
		this.repository = new DaoActive(session);
		Active active = Mockito.mock(Active.class);
		Date begin = Mockito.mock(Date.class);
		Date end = Mockito.mock(Date.class);
		this.repository.search(active, begin, end);
		fail("Should be thrown a exception because ActiveRepository doesn't search by date");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDataDayRepository(){
		Session session = Mockito.mock(Session.class);
		Criteria criteria = Mockito.mock(Criteria.class);
		List<Intraday> datadays = Mockito.mock(List.class);
		Active active = Mockito.mock(Active.class);
		Mockito.when(active.getCode()).thenReturn("1A");
		Mockito.when(session.load(Active.class, "1A")).thenReturn(active);
		Mockito.when(datadays.size()).thenReturn(2);
		Mockito.when(criteria.list()).thenReturn(datadays);
		Mockito.when(session.createCriteria(Mockito.any(Class.class))).thenReturn(criteria);
		
		Dao<Intraday> repository = new DaoDataDays<Intraday>(session){};
		
		Date begin = Mockito.mock(Date.class);
		Date end = Mockito.mock(Date.class);
		assertEquals(2, repository.search(active, begin, end).size());
	}
	
	@Test
	public void list(){
		Session session = Mockito.mock(Session.class);
		List<Active> datadays = Mockito.mock(List.class);
		Mockito.when(datadays.size()).thenReturn(2);
		Criteria criteria = Mockito.mock(Criteria.class);
		Mockito.when(criteria.list()).thenReturn(datadays);
		Mockito.when(session.createCriteria(Mockito.any(Class.class))).thenReturn(criteria);
		Dao<Active> repository = new DaoActive(session);
		assertEquals(2, repository.listAll().size());
	}
	
	@Test(expected=RuntimeException.class)
	public void tryListAllDatadaysWithoutAnyArgumentToAFilter(){
		Session session = Mockito.mock(Session.class);
		RepositoryInterface<Intraday> repository = new DaoDataDays<Intraday>(session);
		repository.listAll();
		fail("Should have been thrown an exception because Intradays can not be list without an argument!");
	}
	
	
	@Test(expected=RuntimeException.class)
	public void testListAllButItCanNotRunBecauseNewsOnlyCanBeListedWithFilterParams(){
		Session session = Mockito.mock(Session.class);
		RepositoryInterface<News> repository = new DaoNews(session);
		repository.listAll();
		fail("Should have been trown an exception cause News can't be listed without params");
	}
}