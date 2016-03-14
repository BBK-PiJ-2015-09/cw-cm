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
		manager = new ContactManagerImpl(new CurrentTimeImpl());
		manager.addNewContact("Jon", "Test notes");
    }

	@Test
	public void testsConstructor() {
		try {
			new ContactManagerImpl(new CurrentTimeImpl());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testsAddFutureMeeting() {
		int output = manager.addFutureMeeting(manager.getContacts(1), date);
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test
	public void testsAddSecondFutureMeeting() {
		manager.addFutureMeeting(manager.getContacts(1), date);
		int output = manager.addFutureMeeting(manager.getContacts(1), date);
		int expected = 2;
		assertEquals(expected, output);
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsAddFutureMeetingInPast() {
		date.set(Calendar.YEAR, 2014);
		manager.addFutureMeeting(manager.getContacts(1), date);
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsAddFutureMeetingWithUnknownContact() {
		Set<Contact> unknownContacts = new HashSet<Contact>();
		unknownContacts.add(new ContactImpl(2, "Mike", "Test notes"));
		manager.addFutureMeeting(unknownContacts, date);
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
	public void testsGetPastMeeting() {
		manager.addNewPastMeeting(manager.getContacts(1), date, "Test notes");
		int output = manager.getPastMeeting(1).getId();
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetFutureMeeting() {
		manager.addFutureMeeting(manager.getContacts(1), date);
		int output = manager.getFutureMeeting(1).getId();
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsGetFutureMeetingWithPastDate() {
		// create a manager with a current time in the future
		manager = new ContactManagerImpl(new CurrentTimeFutureMock());
		// addFutureMeeting uses the actual non-injected present for its check
		manager.addFutureMeeting(manager.getContacts(1), date);
		// getFutureMeeting thinks it's the future, therefore the meeting is in the past, therefore throws the exception
		manager.getFutureMeeting(1);
	}

	@Test
	public void testsAddNewPastMeeting() {
		date.set(Calendar.YEAR, 2014);
		try {
			manager.addNewPastMeeting(manager.getContacts(1), date, "Test notes");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsAddNewPastMeetingWithEmptyContacts() {
		date.set(Calendar.YEAR, 2014);
		Set<Contact> emptyContacts = new HashSet<Contact>();
		manager.addNewPastMeeting(emptyContacts, date, "Test notes");
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsAddNewPastMeetingWithUnknownContact() {
		date.set(Calendar.YEAR, 2014);
		Set<Contact> unknownContacts = new HashSet<Contact>();
		unknownContacts.add(new ContactImpl(2, "Mike", "Test notes"));
		manager.addNewPastMeeting(unknownContacts, date, "Test notes");
	}

	@Test(expected= NullPointerException.class)
	public void testsAddNewPastMeetingNullContacts() {
		date.set(Calendar.YEAR, 2014);
		manager.addNewPastMeeting(null, date, "Test notes");
	}

	@Test(expected= NullPointerException.class)
	public void testsAddNewPastMeetingNullDate() {
		manager.addNewPastMeeting(manager.getContacts(1), null, "Test notes");
	}

	@Test(expected= NullPointerException.class)
	public void testsAddNewPastMeetingNullText() {
		date.set(Calendar.YEAR, 2014);
		manager.addNewPastMeeting(manager.getContacts(1), date, null);
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
