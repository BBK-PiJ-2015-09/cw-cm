import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;


public class PastMeetingTest {

	@Test
	public void testsConstructor() {
		Calendar date = Calendar.getInstance();
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl(1, "Jon"));
		try {
			new PastMeetingImpl(1, date, contacts, "Meeting note");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
