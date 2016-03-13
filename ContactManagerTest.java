import org.junit.*;
import static org.junit.Assert.*;

public class ContactManagerTest {

	@Test
	public void testsConstructor() {
		try {
			new ContactManagerImpl();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
