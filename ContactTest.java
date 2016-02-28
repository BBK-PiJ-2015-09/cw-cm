import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest {
	Contact contact;

	@Before
	public void buildUp() {
		contact = new ContactImpl(1, "John Smith");
	}

	@Test
	public void testsGetId() {
		int output = contact.getId();
		int expected = 1;
		assertEquals(expected, output);
	}

}
