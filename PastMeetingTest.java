import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;


public class PastMeetingTest {
	Calendar date;
	Set<Contact> contacts;
	PastMeeting meeting;

    @Before
	public void setup() {
		date = Calendar.getInstance();
		contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl(1, "Jon"));
		meeting = new PastMeetingImpl(1, date, contacts, "Meeting note");
    }

	@Test
	public void testsConstructor() {
		try {
			new PastMeetingImpl(1, date, contacts, "Meeting note");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsConstructorZeroId() {
		new PastMeetingImpl(0, date, contacts, "Meeting note");
	}

	@Test
	public void getId() {
		int output = meeting.getId();
		int expected = 1;
		assertEquals(expected, output);
	}
}
