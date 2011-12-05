package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.homebroker.model.util.DateConvertor;

public class TestDateConvertor {
	
	private static DateConvertor dateConvertor;
	
	@BeforeClass
	public static void init(){
		dateConvertor = new DateConvertor();
	}
	
	@Test
	public void testDateWithSlashesFormat(){
		String date = "11/10/2011";
		Date converted = dateConvertor.convertStringToDate(date);
		assertNotNull(converted);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(converted);
		assertEquals(Integer.valueOf(11), Integer.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
	}
	
	@Test
	public void testConvertStringToDate(){
		String date = "11-10-2011 09:02";
		Date converted = dateConvertor.convertStringToDate(date);
		assertNotNull(converted);
		assertTrue(converted instanceof Date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(converted);
		assertEquals(Integer.valueOf("11"), Integer.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		assertEquals(Integer.valueOf("10"), Integer.valueOf(calendar.get(Calendar.MONTH)+1));
		assertEquals(Integer.valueOf("2011"), Integer.valueOf(calendar.get(Calendar.YEAR)));	
		assertEquals(Integer.valueOf("02"), Integer.valueOf(calendar.get(Calendar.MINUTE)));
		assertEquals(Integer.valueOf("09"), Integer.valueOf(calendar.get(Calendar.HOUR)));
	}
}
