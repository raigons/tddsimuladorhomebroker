package models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.homebroker.model.Active;

public class TestActive {

	private Active active;
	
	@Before
	public void initActive(){
		String pregao = "pregao";
		String company = "company";
		String code = "code";
		this.active = new Active(code, pregao, company);
	}
	
	@Test
	public void testActiveNotNull(){
		assertNotNull(active);
	}
	
	@Test
	public void testBodyOfAnActive(){
		assertEquals("pregao", active.getPregao());
		assertEquals("company", active.getCompany());
		assertEquals("code", active.getCode());
	}
	
	
}
