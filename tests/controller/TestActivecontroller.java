package controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.util.test.MockSerializationResult;
import br.com.homebroker.controller.active.ActiveController;
import br.com.homebroker.model.Active;
import br.com.homebroker.service.ActiveService;
import br.com.homebroker.util.exceptions.NothingFoundException;

public class TestActivecontroller {

	@Mock
	private ActiveService service;
	private MockSerializationResult result;
	private ActiveController controller;
	
	@Before
	public void testActiveController(){
		MockitoAnnotations.initMocks(this);
		result = new MockSerializationResult();
		controller = new ActiveController(result, service);		
	}
	
	@Test
	public void listAllActive() throws Exception{
		List<Active> list = new ArrayList<Active>();
		Active active1 = new Active("1a", "p1", "c1");
		//active1.setId(1L);
		Active active2 = new Active("2a", "p2", "c2");
		//active2.setId(2L);
		list.add(active1); list.add(active2);
		Mockito.when(service.listAll()).thenReturn(list);
		//\"id\": 1,\"id\": 2,
		String json = "[{\"code\": \"1a\",\"pregao\": \"p1\",\"company\": \"c1\"},{\"code\": \"2a\",\"pregao\": \"p2\",\"company\": \"c2\"}]";
		
		controller.list();
		assertEquals(json, this.result.serializedResult());
	}

	@Test
	public void foundNoACtive() throws Exception{
		Mockito.when(service.listAll()).thenThrow(new NothingFoundException());
		String json = "{\"message\": \"Nenhum resultado encontrado\"}";
		controller.list();
		assertEquals(json, this.result.serializedResult());
	}
	
	@Test
	public void searchOneActive() throws Exception{		
		Mockito.when(this.service.search("2A")).thenReturn(new Active("2A", "P2", "C2"));
		
		controller.search("2A");
		
		String json = "{\"code\": \"2A\",\"pregao\": \"P2\",\"company\": \"C2\"}";
		assertEquals(json, this.result.serializedResult());
	}
	
	@Test
	public void saerchOneActiveAndNofound() throws Exception{
		Mockito.when(this.service.search("2A")).thenThrow(new NothingFoundException());
		controller.search("2A");
		String json = "{\"message\": \"Nenhum resultado encontrado\"}";
		assertEquals(json, this.result.serializedResult());
	}
	
	@Test
	public void save() throws Exception{
		Active active = Mockito.mock(Active.class);
		Mockito.when(this.service.save(active)).thenReturn(true);
		controller.save(active);
		assertEquals("{\"message\": \"Ativo inserido com sucesso\"}", this.result.serializedResult());
	}
}
