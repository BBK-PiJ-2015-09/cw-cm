import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

public class ContactManagerTest {
	Calendar date;
	Set<Contact> contacts;
	ContactManager manager;

    @Before
	public void setup() {
		date = Calendar.getInstance();
		date.add(Calendar.MONTH, 1);
		contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl(1, "Jon"));
		manager = new ContactManagerImpl();
    }

	@Test
	public void testsConstructor() {
		try {
			new ContactManagerImpl();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testsAddFutureMeeting() {
		int output = manager.addFutureMeeting(contacts, date);
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test
	public void testsAddSecondFutureMeeting() {
		manager.addFutureMeeting(contacts, date);
		int output = manager.addFutureMeeting(contacts, date);
		int expected = 2;
		assertEquals(expected, output);
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsAddFutureMeetingInPast() {
		date.set(Calendar.YEAR, 2014);
		manager.addFutureMeeting(contacts, date);
	}

	// @Test(expected= IllegalArgumentException.class)
	// public void testsAddFutureMeetingWithUnknownContact() {
	// 	manager.addFutureMeeting(contacts, date);
	// }

	@Test(expected= NullPointerException.class)
	public void testsAddFutureMeetingNullContacts() {
		manager.addFutureMeeting(null, date);
	}

	@Test(expected= NullPointerException.class)
	public void testsAddFutureMeetingNullDate() {
		manager.addFutureMeeting(contacts, null);
	}

	@Test
	public void testsGetFutureMeeting() {
		manager.addFutureMeeting(contacts, date);
		int output = manager.getFutureMeeting(1).getId();
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test
	public void testsAddNewContact() {
		int output = manager.addNewContact("Emily", "Test notes");
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsAddNewContactEmptyName() {
		manager.addNewContact("", "Test notes");
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsAddNewContactEmptyNotes() {
		manager.addNewContact("Emily", "");
	}

	@Test(expected= NullPointerException.class)
	public void testsAddNewContactNullName() {
		manager.addNewContact(null, "Test notes");
	}

	@Test(expected= NullPointerException.class)
	public void testsAddNewContactNullNotes() {
		manager.addNewContact("Emily", null);
	}
}
