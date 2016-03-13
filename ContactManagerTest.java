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

}
