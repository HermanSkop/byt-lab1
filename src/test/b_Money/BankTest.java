package test.b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import src.b_Money.*;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, NoSuchFieldException, IllegalAccessException {
		SweBank.openAccount("Test");
		Field privateField = Bank.class.getDeclaredField("accountlist");
		privateField.setAccessible(true);
		Hashtable<String, Account> hashtable = (Hashtable<String, Account>) privateField.get(SweBank);
		assertTrue(hashtable.contains("Test"));
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		assertEquals(10000, SweBank.getBalance("Ulrika"), 0.0001);
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.withdraw("Ulrika", new Money(10000, SEK));
		assertEquals(-10000, SweBank.getBalance("Ulrika"), 0.0001);
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(0, SweBank.getBalance("Ulrika"), 0.0001);
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.transfer("Ulrika", "Bob", new Money(10000, SEK));
		assertEquals(-10000, SweBank.getBalance("Ulrika"), 0.0001);
		assertEquals(10000, SweBank.getBalance("Bob"), 0.0001);
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Ulrika", "1", 1, 1, new Money(10000, SEK), Nordea, "Bob");
		SweBank.tick();
		assertEquals(-10000, SweBank.getBalance("Ulrika"), 0.0001);
		assertEquals(10000, Nordea.getBalance("Bob"), 0.0001);
		SweBank.tick();
		assertEquals(-20000, SweBank.getBalance("Ulrika"), 0.0001);
		assertEquals(20000, Nordea.getBalance("Bob"), 0.0001);
		SweBank.tick();
		assertEquals(-30000, SweBank.getBalance("Ulrika"), 0.0001);
		assertEquals(30000, Nordea.getBalance("Bob"), 0.0001);
		SweBank.tick();
		assertEquals(-40000, SweBank.getBalance("Ulrika"), 0.0001);
		assertEquals(40000, Nordea.getBalance("Bob"), 0.0001);
		SweBank.tick();
		assertEquals(-50000, SweBank.getBalance("Ulrika"), 0.0001);
		assertEquals(50000, Nordea.getBalance("Bob"), 0.0001);
	}
}
