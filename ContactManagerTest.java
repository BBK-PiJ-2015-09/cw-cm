import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.io.File;

public class ContactManagerTest {
	Calendar date;
	Set<Contact> contacts;
	ContactManager manager;

    @Before
	public void setup() {
		date = Calendar.getInstance();
		date.add(Calendar.MONTH, 2);
		manager = new ContactManagerImpl();
		manager.addNewContact("Jon", "Test notes");
    }

    @After
	public void teardown() {
		File file = new File("pastMeetings.csv");
		file.delete();
		file = new File("futureMeetings.csv");
		file.delete();
		file = new File("contacts.csv");
		file.delete();
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
		manager = new ContactManagerImpl();
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

	@Test
	public void testsGetFutureMeetingListEmpty() {
		// Add meetings for existing contact
		manager.addFutureMeeting(manager.getContacts(1), date);
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.MONTH, 1);
		manager.addFutureMeeting(manager.getContacts(1), date2);

		// Add a new contact without any meetings
		manager.addNewContact("Jon", "New contact");
		Contact contact = manager.getContacts(2).iterator().next();

		// Make sure the method doesn't return any meetings for him
		int output = manager.getFutureMeetingList(contact).size();
		int expected = 0;
		assertEquals(expected, output);
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
		date2.set(Calendar.HOUR_OF_DAY, 6);
		manager.addFutureMeeting(manager.getContacts(1), date2);
		Calendar date3 = Calendar.getInstance();
		date3.add(Calendar.MONTH, 1);
		date3.set(Calendar.HOUR_OF_DAY, 9);
		int output = manager.getMeetingListOn(date3).size();
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetMeetingListOnChronological() {
		// Add the meetings, out of chronological order
		Calendar date1 = Calendar.getInstance();
		date1.add(Calendar.HOUR, 1);
		date1.set(Calendar.HOUR_OF_DAY, 9);
		manager.addFutureMeeting(manager.getContacts(1), date1);
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.HOUR, 1);
		date2.set(Calendar.HOUR_OF_DAY, 6);
		manager.addFutureMeeting(manager.getContacts(1), date2);
		Calendar date3 = Calendar.getInstance();
		date3.add(Calendar.HOUR, 1);
		date3.set(Calendar.HOUR_OF_DAY, 3);
		manager.addFutureMeeting(manager.getContacts(1), date3);

		// Get the meetings
		List<Meeting> meetings = manager.getMeetingListOn(date1);
		Date first = meetings.get(0).getDate().getTime();
		Date second = meetings.get(1).getDate().getTime();
		Date third = meetings.get(2).getDate().getTime();

		// Assert that they are return in chronological order
		assertTrue((first.before(second)));
		assertTrue((second.before(third)));
	}

	@Test
	public void testsGetMeetingListOnEmptyDate() {
		manager.addFutureMeeting(manager.getContacts(1), date);
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.MONTH, 1);
		manager.addFutureMeeting(manager.getContacts(1), date2);
		Calendar date3 = Calendar.getInstance();
		date3.add(Calendar.MONTH, 6);
		int output = manager.getMeetingListOn(date3).size();
		int expected = 0;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetPastMeetingListFor() {
		date.add(Calendar.YEAR, -1);
		manager.addNewPastMeeting(manager.getContacts(1), date, "Test notes");
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.YEAR, -2);
		manager.addNewPastMeeting(manager.getContacts(1), date2, "Test notes");
		Contact contact = manager.getContacts(1).iterator().next();
		int output = manager.getPastMeetingListFor(contact).size();
		int expected = 2;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetPastMeetingListForChronological() {
		// Add the meetings, out of chronological order
		Calendar date1 = Calendar.getInstance();
		date1.add(Calendar.MONTH, -1);
		manager.addNewPastMeeting(manager.getContacts(1), date1, "Test notes");
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.MONTH, -2);
		manager.addNewPastMeeting(manager.getContacts(1), date2, "Test notes");
		Calendar date3 = Calendar.getInstance();
		date3.add(Calendar.MONTH, -3);
		manager.addNewPastMeeting(manager.getContacts(1), date3, "Test notes");

		// Get the contact
		Contact contact = manager.getContacts(1).iterator().next();

		// Get the meetings
		List<PastMeeting> meetings = manager.getPastMeetingListFor(contact);
		Date first = meetings.get(0).getDate().getTime();
		Date second = meetings.get(1).getDate().getTime();
		Date third = meetings.get(2).getDate().getTime();

		// Assert that they are return in chronological order
		assertTrue((first.before(second)));
		assertTrue((second.before(third)));
	}

	@Test
	public void testsGetPastMeetingListEmpty() {
		// Add meetings for existing contact
		Calendar date1 = Calendar.getInstance();
		date1.add(Calendar.MONTH, -1);
		manager.addNewPastMeeting(manager.getContacts(1), date1, "Test notes");
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.MONTH, -2);
		manager.addNewPastMeeting(manager.getContacts(1), date2, "Test notes");

		// Add a new contact without any meetings
		manager.addNewContact("Jon", "New contact");
		Contact contact = manager.getContacts(2).iterator().next();

		// Make sure the method doesn't return any meetings for him
		int output = manager.getPastMeetingListFor(contact).size();
		int expected = 0;
		assertEquals(expected, output);
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsGetPastMeetingListForWithUnknownContact() {
		Contact unknownContact = new ContactImpl(2, "Mike", "Test notes");
		manager.getPastMeetingListFor(unknownContact);
	}

	@Test(expected= NullPointerException.class)
	public void testsPastMeetingListForWithNullContact() {
		manager.getPastMeetingListFor(null);
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
	public void testsAddMeetingNotesToPastMeeting() {
		date.add(Calendar.YEAR, -1);
		manager.addNewPastMeeting(manager.getContacts(1), date, "");
		manager.addMeetingNotes(1, "This meeting occurred.");
		String output = manager.getPastMeeting(2).getNotes();
		String expected = "This meeting occurred.";
		assertEquals(expected, output);
	}

	@Test
	public void testsAddMeetingNotesRemovesCopiedMeeting() {
		date.add(Calendar.YEAR, -1);
		manager.addNewPastMeeting(manager.getContacts(1), date, "");
		manager.addMeetingNotes(1, "This meeting occurred.");
		PastMeeting output = manager.getPastMeeting(1);
		PastMeeting expected = null;
		assertEquals(expected, output);
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsAddMeetingNotesNonExistentMeeting() {
		manager.addMeetingNotes(2, "This meeting occurred.");
	}

	@Test(expected= IllegalStateException.class)
	public void testsAddMeetingNotesPastMeetingFutureDate() {
		// create a manager with a current time in the past
		manager = new ContactManagerImpl();
		manager.addNewContact("Jon", "Test notes");
		// addPastMeeting uses the actual non-injected present for its check
		manager.addNewPastMeeting(manager.getContacts(1), date, "Test notes");
		// addMeetingNotes thinks it's the past, therefore the meeting is in the future, therefore throws the exception
		manager.addMeetingNotes(1, "This meeting occurred.");
	}

	@Test(expected= NullPointerException.class)
	public void testsAddMeetingNotesNullNotes() {
		manager.addMeetingNotes(1, null);
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

	@Test
	public void testsFlush() {
		// add contacts
		manager.addNewContact("Emily", "Test notes");
		manager.addNewContact("Bobby", "Test notes");
		manager.addNewContact("Amelia", "Test notes");

		// add dates
		Calendar date1 = Calendar.getInstance();
		date1.add(Calendar.YEAR, -1);
		Calendar date2 = Calendar.getInstance();
		date2.add(Calendar.MONTH, -6);
		Calendar date3 = Calendar.getInstance();
		date3.add(Calendar.MONTH, -1);
		Calendar date4 = Calendar.getInstance();
		date4.add(Calendar.YEAR, 1);
		Calendar date5 = Calendar.getInstance();
		date5.add(Calendar.MONTH, 6);
		Calendar date6 = Calendar.getInstance();
		date6.add(Calendar.MONTH, 1);

		// add meetings
		manager.addNewPastMeeting(manager.getContacts(1,2,3), date1, "Past Notes");
		manager.addNewPastMeeting(manager.getContacts(1,2), date2, "Past Notes");
		manager.addNewPastMeeting(manager.getContacts(1), date3, "Past Notes");
		manager.addFutureMeeting(manager.getContacts(1,2,3), date4);
		manager.addFutureMeeting(manager.getContacts(1,2), date5);
		manager.addFutureMeeting(manager.getContacts(1), date6);

		// save to csv
		manager.flush();

		File pastMeetings = new File("pastMeetings.csv");
		assertTrue(pastMeetings.exists());

		File futureMeetings = new File("futureMeetings.csv");
		assertTrue(futureMeetings.exists());

		File contacts = new File("contacts.csv");
		assertTrue(contacts.exists());
	}
}
