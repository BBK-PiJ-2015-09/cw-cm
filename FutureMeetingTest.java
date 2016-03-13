import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;

public class FutureMeetingTest {
	Calendar date;
	Set<Contact> contacts;
	String notes;

    @Before
	public void setup() {
		date = Calendar.getInstance();
		contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl(1, "Jon"));
    }

	@Test
	public void testsConstructor() {
		try {
			new FutureMeetingImpl(1, date, contacts);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
