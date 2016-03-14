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
		contacts.add(new ContactImpl(1, "Jon", "Test notes"));
		manager = new ContactManagerImpl();
		manager.addNewContact("Jon", "Test notes");
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

	@Test(expected= IllegalArgumentException.class)
	public void testsAddFutureMeetingWithUnknownContact() {
		Set<Contact> unknownContacts = new HashSet<Contact>();
		unknownContacts.add(new ContactImpl(2, "Mike", "Test notes"));
		manager.addFutureMeeting(contacts, date);
	}

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
		int expected = 2;
		assertEquals(expected, output);
	}

	@Test
	public void testsAddAnotherNewContact() {
		manager.addNewContact("Bill", "Test notes");
		int output = manager.addNewContact("Emily", "Test notes");
		int expected = 3;
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

	@Test
	public void testsGetContactsString() {
		manager.addNewContact("Emily", "Test notes");
		int output = manager.getContacts("Emily").size();
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetContactsEmptyString() {
		manager.addNewContact("Emily", "Test notes");
		manager.addNewContact("Bobby", "Test notes");
		int output = manager.getContacts("").size();
		int expected = 3;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetContactsSubstring() {
		manager.addNewContact("Emily", "Test notes");
		manager.addNewContact("Bobby", "Test notes");
		manager.addNewContact("Amelia", "Test notes");
		int output = manager.getContacts("y").size();
		int expected = 2;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetContactsSubstringUpcase() {
		manager.addNewContact("Emily", "Test notes");
		manager.addNewContact("Bobby", "Test notes");
		int output = manager.getContacts("Y").size();
		int expected = 2;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetContactsId() {
		manager.addNewContact("Emily", "Test notes");
		manager.addNewContact("Bobby", "Test notes");
		manager.addNewContact("Amelia", "Test notes");
		int output = manager.getContacts(2).size();
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetContactsIds() {
		manager.addNewContact("Emily", "Test notes");
		manager.addNewContact("Bobby", "Test notes");
		manager.addNewContact("Amelia", "Test notes");
		int output = manager.getContacts(2, 3).size();
		int expected = 2;
		assertEquals(expected, output);
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsGetContactsNoIds() {
		manager.getContacts();
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsGetContactsUnfoundIds() {
		manager.addNewContact("Emily", "Test notes");
		manager.addNewContact("Bobby", "Test notes");
		manager.addNewContact("Amelia", "Test notes");
		manager.getContacts(93, 94);
	}

}
