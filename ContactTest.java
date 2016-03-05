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

	@Test(expected= IllegalArgumentException.class)
	public void testsZeroId() {
		new ContactImpl(0, "John Smith");
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsNegativeId() {
		new ContactImpl(-1, "John Smith");
	}

	@Test(expected= NullPointerException.class)
	public void testsNullName() {
		new ContactImpl(1, null);
	}

}
