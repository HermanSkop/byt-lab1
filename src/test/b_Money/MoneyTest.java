package test.b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import src.b_Money.Currency;
import src.b_Money.Money;

import java.awt.geom.QuadCurve2D;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		// check that the amount is correct
		assertEquals(100, SEK100.getAmount(), 0.0001);
		// check that 0 amount is correct
		assertEquals(0, SEK0.getAmount(), 0.0001);
		// check that negative amount is correct
		assertEquals(-100, SEKn100.getAmount(), 0.0001);
	}

	@Test
	public void testGetCurrency() {
		// check that the currency is correct
		assertEquals(SEK, SEK100.getCurrency());
		// check that the currency is correct
		assertEquals(EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		// check that the string is correct
		assertEquals("100.0 SEK", SEK100.toString());
		// check that the string is correct
		assertEquals("0.0 SEK", SEK0.toString());
		// check that the string is correct
		assertEquals("-100.0 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		// check that the global value is correct
		assertEquals(1500, SEK100.universalValue(), 0.0001);
		// check that the global value is correct
		assertEquals(1500, EUR10.universalValue(), 0.0001);
		// check that the global value is correct
		assertEquals(3000, SEK200.universalValue(), 0.0001);
		// check that the global value is correct
		assertEquals(3000, EUR20.universalValue(), 0.0001);
		// check that the global value is correct
		assertEquals(0, SEK0.universalValue(), 0.0001);
		// check that the global value is correct
		assertEquals(0, EUR0.universalValue(), 0.0001);
		// check that the global value is correct
		assertEquals(-1500, SEKn100.universalValue(), 0.0001);
	}

	@Test
	public void testEqualsMoney() {
		// check that the money of the same currency is equal
		assertTrue(SEK100.equals(new Money(10000, SEK)));
		assertTrue(EUR10.equals(new Money(1000, EUR)));
		assertTrue(SEK0.equals(new Money(0, SEK)));
		assertTrue(EUR0.equals(new Money(0, EUR)));
		assertTrue(SEKn100.equals(new Money(-10000, SEK)));
	}

	@Test
	public void testAdd() {
		// check that the money is equal
		assertEquals(new Money(30000, SEK).getAmount(), SEK100.add(SEK200).getAmount());
		assertEquals(new Money(3000, EUR).getAmount(), EUR10.add(EUR20).getAmount());
		assertEquals(new Money(10000, SEK).getAmount(), SEK0.add(SEK100).getAmount());
		assertEquals(new Money(1000, EUR).getAmount(), EUR0.add(EUR10).getAmount());
		assertEquals(new Money(0, SEK).getAmount(), SEKn100.add(SEK100).getAmount());
		assertEquals(new Money(2000, EUR).getAmount(), EUR0.add(EUR20).getAmount());
		assertEquals(new Money(0, SEK).getAmount(), SEK0.add(SEK0).getAmount());
		assertEquals(new Money(-20000, SEK).getAmount(), SEKn100.add(SEKn100).getAmount());
		// check different currencies
		assertEquals(new Money(-1000, EUR).getAmount(), EUR0.add(SEKn100).getAmount());
	}

	@Test
	public void testSub() {
		// check that the money is equal
		assertEquals(new Money(10000, SEK).getAmount(), SEK100.sub(SEK0).getAmount());
		assertEquals(new Money(1000, EUR).getAmount(), EUR10.sub(EUR0).getAmount());
		assertEquals(new Money(0, SEK).getAmount(), SEK0.sub(SEK0).getAmount());
		assertEquals(new Money(0, EUR).getAmount(), EUR0.sub(EUR0).getAmount());
		assertEquals(new Money(-10000, SEK).getAmount(), SEK0.sub(SEK100).getAmount());
		assertEquals(new Money(-1000, EUR).getAmount(), EUR0.sub(EUR10).getAmount());
		assertEquals(new Money(-20000, SEK).getAmount(), SEKn100.sub(SEK100).getAmount());
		assertEquals(new Money(-2000, EUR).getAmount(), EUR0.sub(EUR20).getAmount());
		// check different currencies
		assertEquals(new Money(-20000, SEK).getAmount(), SEKn100.sub(EUR10).getAmount());

	}

	@Test
	public void testIsZero() {
		// check that money is zero
		assertTrue(SEK0.isZero());
		assertTrue(EUR0.isZero());
		assertFalse(SEK100.isZero());
		assertFalse(EUR10.isZero());
		assertFalse(SEKn100.isZero());
		// check that negative money is not zero
		assertFalse(new Money(-1, SEK).isZero());
	}

	@Test
	public void testNegate() {
		// check that the money is equal
		assertEquals(new Money(-10000, SEK).getAmount(), SEK100.negate().getAmount());
		assertEquals(new Money(-1000, EUR).getAmount(), EUR10.negate().getAmount());
		assertEquals(new Money(0, SEK).getAmount(), SEK0.negate().getAmount());
		assertEquals(new Money(0, EUR).getAmount(), EUR0.negate().getAmount());
		assertEquals(new Money(10000, SEK).getAmount(), SEKn100.negate().getAmount());
	}

	@Test
	public void testCompareTo() {
		// check that the money is equal
		assertEquals(0, SEK100.compareTo(SEK100));
		assertEquals(0, EUR10.compareTo(EUR10));
		assertEquals(0, SEK0.compareTo(SEK0));
		assertEquals(0, EUR0.compareTo(EUR0));
		assertEquals(0, SEKn100.compareTo(SEKn100));
		assertEquals(1, SEK100.compareTo(SEK0));
		assertEquals(1, EUR10.compareTo(EUR0));
		assertEquals(1, SEK100.compareTo(SEKn100));
		assertEquals(1, EUR10.compareTo(new Money(-1000, EUR)));
		assertEquals(-1, SEK0.compareTo(SEK100));
		assertEquals(-1, EUR0.compareTo(EUR10));
		assertEquals(-1, SEKn100.compareTo(SEK100));
		assertEquals(0, EUR10.compareTo(new Money(1000, EUR)));
	}
}
