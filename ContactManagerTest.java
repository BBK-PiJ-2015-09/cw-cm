import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
// import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class ContactManagerTest {
	Calendar date;
	Set<Contact> contacts;
	ContactManager manager;

    @Before
	public void setup() {
		date = Calendar.getInstance();
		date.add(Calendar.MONTH, 2);
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
		date = Calendar.getInstance();
		date.add(Calendar.MONTH, -1);
		manager.addNewPastMeeting(manager.getContacts(1), date, "Test notes");
		int output = manager.getPastMeeting(1).getId();
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test(expected= IllegalStateException.class)
	public void testsGetPastMeetingWithFutureDate() {
		// create a manager with a current time in the past
		manager = new ContactManagerImpl(new CurrentTimePastMock());
		manager.addNewContact("Jon", "Test notes");
		// addPastMeeting uses the actual non-injected present for its check
		manager.addNewPastMeeting(manager.getContacts(1), date, "Test notes");
		// getPastMeeting thinks it's the past, therefore the meeting is in the future, therefore throws the exception
		manager.getPastMeeting(1);
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
		manager.addNewContact("Jon", "Test notes");
		// addFutureMeeting uses the actual non-injected present for its check
		manager.addFutureMeeting(manager.getContacts(1), date);
		// getFutureMeeting thinks it's the future, therefore the meeting is in the past, therefore throws the exception
		manager.getFutureMeeting(1);
	}

	@Test
	public void testsGetMeetingFuture() {
		manager.addFutureMeeting(manager.getContacts(1), date);
		int output = manager.getMeeting(1).getId();
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetMeetingPast() {
		date.add(Calendar.YEAR, -1);
		manager.addNewPastMeeting(manager.getContacts(1), date, "Test notes");
		int output = manager.getMeeting(1).getId();
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetFutureMeetingList() {
		manager.addFutureMeeting(manager.getContacts(1), date);
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.MONTH, 1);
		manager.addFutureMeeting(manager.getContacts(1), date2);
		Contact contact = manager.getContacts(1).iterator().next();
		int output = manager.getFutureMeetingList(contact).size();
		int expected = 2;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetFutureMeetingListChronological() {
		// Add the meetings, out of chronological order
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.MONTH, 3);
		manager.addFutureMeeting(manager.getContacts(1), date2);
		manager.addFutureMeeting(manager.getContacts(1), date);
		Calendar date3 = Calendar.getInstance();
		date3.add(Calendar.MONTH, 1);
		manager.addFutureMeeting(manager.getContacts(1), date3);

		// Get the contact
		Contact contact = manager.getContacts(1).iterator().next();

		// Get the meetings
		List<Meeting> meetings = manager.getFutureMeetingList(contact);
		Date first = meetings.get(0).getDate().getTime();
		Date second = meetings.get(1).getDate().getTime();
		Date third = meetings.get(2).getDate().getTime();

		// Assert that they are return in chronological order
		assertTrue((first.before(second)));
		assertTrue((second.before(third)));
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsGetFutureMeetingListWithUnknownContact() {
		Contact unknownContact = new ContactImpl(2, "Mike", "Test notes");
		manager.getFutureMeetingList(unknownContact);
	}

	@Test(expected= NullPointerException.class)
	public void testsGetFutureMeetingListWithNullContact() {
		manager.getFutureMeetingList(null);
	}

	@Test
	public void testsGetMeetingListOn() {
		manager.addFutureMeeting(manager.getContacts(1), date);
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.MONTH, 1);
		manager.addFutureMeeting(manager.getContacts(1), date2);
		Calendar date3 = Calendar.getInstance();
		date3.add(Calendar.MONTH, 1);
		int output = manager.getMeetingListOn(date3).size();
		int expected = 1;
		assertEquals(expected, output);
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
