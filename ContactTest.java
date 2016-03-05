import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest {

	@Test
	public void testsGeneralConstructor() {
		Contact contact = new ContactImpl(1, "John Smith", "Initial note");
		testsGetId(contact);
		testsGetName(contact);
		testsGetNotes(contact);
		testsAddNotes(contact);
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsGeneralZeroId() {
		new ContactImpl(0, "John Smith", "Initial note");
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsGeneralNegativeId() {
		new ContactImpl(-1, "John Smith", "Initial note");
	}

	@Test(expected= NullPointerException.class)
	public void testsGeneralNullName() {
		new ContactImpl(1, null, "Initial note");
	}

	@Test(expected= NullPointerException.class)
	public void testsGeneralNullNote() {
		new ContactImpl(1, "John Smith", null);
	}

	@Test
	public void testsRestrictedConstructor() {
		Contact contact = new ContactImpl(1, "John Smith");
		testsGetId(contact);
		testsGetName(contact);
		testsAddNotes(contact);
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsRestrictedZeroId() {
		new ContactImpl(0, "John Smith");
	}

	@Test(expected= IllegalArgumentException.class)
	public void testsRestrictedNegativeId() {
		new ContactImpl(-1, "John Smith");
	}

	@Test(expected= NullPointerException.class)
	public void testsRestrictedNullName() {
		new ContactImpl(1, null);
	}

	private void testsGetId(Contact contact) {
		int output = contact.getId();
		int expected = 1;
		assertEquals(expected, output);
	}

	private void testsGetName(Contact contact) {
		String output = contact.getName();
		String expected = "John Smith";
		assertEquals(expected, output);
	}

	private void testsGetNotes(Contact contact) {
		String output = contact.getNotes();
		String expected = "Initial note";
		assertEquals(expected, output);
	}

	private void testsAddNotes(Contact contact) {
		contact.addNotes("New note");
		String output = contact.getNotes();
		String expected = "New note";
		assertEquals(expected, output);
	}

}
