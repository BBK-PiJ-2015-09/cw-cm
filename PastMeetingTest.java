import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;


public class PastMeetingTest {
	Calendar date;
	Set<Contact> contacts;
	String notes;
	PastMeeting meeting;

    @Before
	public void setup() {
		date = Calendar.getInstance();
		contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl(1, "Jon"));
		notes = "Meeting note";
		meeting = new PastMeetingImpl(1, date, contacts, notes);
    }

	@Test
	public void testsConstructor() {
		try {
			new PastMeetingImpl(1, date, contacts, notes);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(expected= NullPointerException.class)
	public void testsConstructorNullDate() {
		new PastMeetingImpl(1, null, contacts, notes);
	}

	@Test(expected= NullPointerException.class)
	public void testsConstructorNullContacts() {
		new PastMeetingImpl(1, date, null, notes);
	}

	@Test(expected= NullPointerException.class)
	public void testsConstructorNullNotes() {
		new PastMeetingImpl(1, date, contacts, null);
	}

	@Test
	public void testsGetId() {
		int output = meeting.getId();
		int expected = 1;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetDate() {
		Calendar output = meeting.getDate();
		Calendar expected = date;
		assertEquals(expected, output);
	}

	@Test
	public void testsGetContacts() {
		Set<Contact> output = meeting.getContacts();
		Set<Contact> expected = contacts;
		assertEquals(expected, output);
	}
}
