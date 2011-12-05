package active;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.homebroker.model.Active;
import br.com.homebroker.repository.RepositoryFactory;
import br.com.homebroker.repository.dao.Dao;
import br.com.homebroker.service.ActiveService;
import br.com.homebroker.util.Param;
import br.com.homebroker.util.SearchParams;
import br.com.homebroker.util.exceptions.NothingFoundException;

public class TestActiveServices {

	private ActiveService service;	
	
	private Dao respository;
	
	private RepositoryFactory factory;
	
	private void mockito(){
		mockingRepository();
	}
	
	@SuppressWarnings("unchecked")
	private void mockingRepository(){
		this.respository = Mockito.mock(Dao.class);		
		Active active = Mockito.mock(Active.class);
		String code = "10A";
		Param<String> param = Mockito.mock(Param.class);
		Mockito.when(param.getParam()).thenReturn(code);
		
		Mockito.when(active.getCode()).thenReturn(code);
		SearchParams params = Mockito.mock(SearchParams.class);
		
		Mockito.when(this.respository.search(params)).thenReturn(active);
		
		this.factory = Mockito.mock(RepositoryFactory.class);
		Mockito.when(this.factory.getRepository(Mockito.anyString())).thenReturn(this.respository);		
	}
	
	@Before
	public void initController(){
		mockito();
		this.service = new ActiveService(this.factory);
	}
	
	@Test
	public void testServicesAccess(){
		SearchParams params = Mockito.mock(SearchParams.class);
		Active active = Mockito.mock(Active.class);
		Mockito.when(active.getCode()).thenReturn("10A");
		Mockito.when(this.respository.search(params)).thenReturn(active);
		assertEquals("10A", this.service.search(params).getCode());
	}
	
	@Test(expected=NothingFoundException.class)
	public void testServiceSearchNotFound(){
		SearchParams params = Mockito.mock(SearchParams.class);
		Mockito.when(this.respository.search(params)).thenReturn(null);
		this.service.search(params);
		fail("It should have been thrown an exception!");
	}
	
}
