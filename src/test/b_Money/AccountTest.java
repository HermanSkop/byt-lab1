package test.b_Money;

import org.junit.Before;
import org.junit.Test;
import src.b_Money.*;

import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		SweBank.addTimedPayment("Alice", "1", 1, 1, new Money(1000000, SEK), DanskeBank, "Bob");
		assertTrue(testAccount.timedPaymentExists("1"));
		SweBank.removeTimedPayment("Alice", "1");
		assertFalse(testAccount.timedPaymentExists("1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Alice", "1", 1, 1, new Money(1000000, SEK), DanskeBank, "Bob");
		SweBank.tick();
		assertEquals(9999999, testAccount.getBalance().getAmount(), 0.0001);
		SweBank.tick();
		assertEquals(9999998, testAccount.getBalance().getAmount(), 0.0001);
	}

	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(1000000, SEK));
		assertEquals(11000000, testAccount.getBalance().getAmount(), 0.0001);
		testAccount.withdraw(new Money(1000000, SEK));
		assertEquals(10000000, testAccount.getBalance().getAmount(), 0.0001);
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(10000000, testAccount.getBalance().getAmount(), 0.0001);
	}
}
