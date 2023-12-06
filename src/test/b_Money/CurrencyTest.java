package test.b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import src.b_Money.Currency;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		// check that the name is correct
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		// check that the rate is correct
		assertEquals(0.15, SEK.getRate(), 0.0001);
		assertEquals(0.20, DKK.getRate(), 0.0001);
		assertEquals(1.5, EUR.getRate(), 0.0001);
	}
	
	@Test
	public void testSetRate() {
		// check that the rate is correct
		SEK.setRate(0.16);
		assertEquals(0.16, SEK.getRate(), 0.0001);
		DKK.setRate(0.21);
		assertEquals(0.21, DKK.getRate(), 0.0001);
		EUR.setRate(1.6);
		assertEquals(1.6, EUR.getRate(), 0.0001);
	}
	
	@Test
	public void testUniversalValue() {
		// check that the global value is correct
		assertEquals(1500, SEK.universalValue(10000), 0.0001);
		assertEquals(2000, DKK.universalValue(10000), 0.0001);
		assertEquals(15000, EUR.universalValue(10000), 0.0001);
	}
	
	@Test
	public void testValueInThisCurrency() {
		// check that the value in this currency is correct
		assertEquals(15000, SEK.valueInThisCurrency(1500, EUR), 0.0001);
		assertEquals(11250, DKK.valueInThisCurrency(1500, EUR), 0.0001);
		assertEquals(1500, EUR.valueInThisCurrency(1500, EUR), 0.0001);
	}

}
