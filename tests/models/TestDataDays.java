package models;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;
import org.mockito.Mockito;

import br.com.homebroker.model.Active;
import br.com.homebroker.model.DataDays;
import br.com.homebroker.model.Diary;
import br.com.homebroker.model.Intraday;

public class TestDataDays {
	
	private DataDays datadays;	
		
	private void initDataday(String type){
		Double max = 10.0;
		Double min = 7.0;
		Double close = 8.5;
		Double open = 9.0;
		Long volume = 10000L;		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, 10, 10);
		Active active = Mockito.mock(Active.class);
		Mockito.when(active.getCode()).thenReturn("10A");
		if(type.equals("Intraday"))
			datadays = new Intraday(max, min, close, open, volume, active, calendar.getTime());
		else if(type.equals("Diary"))
			datadays = new Diary(max, min, close, open, volume, active, calendar.getTime());
	}
	
	@Test
	public void testGetValues(){
		test("Intraday");
	}
	
	@Test
	public void testGetValuesOfDiary(){
		test("Diary");
	}
	
	private void test(String type){
		initDataday(type);
		assertEquals(10.0, this.datadays.getMax(), 0.00001);
		assertEquals(7.0, this.datadays.getMin(), 0.00001);
		assertEquals(8.5, this.datadays.getClose(), 0.00001);
		assertEquals(9.0, this.datadays.getOpen(), 0.00001);
		assertEquals(Long.valueOf(10000), this.datadays.getVolume());
		assertEquals("10A", this.datadays.getActiveCode());
	}
}
