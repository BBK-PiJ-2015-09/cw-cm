import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;

public class CurrentTimeTest {

	@Test
	public void testsConstructor() {
		try {
			new CurrentTimeImpl();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testsNow() {
		CurrentTime currentTime = new CurrentTimeImpl();
		Calendar output = currentTime.now();
		Calendar expected = Calendar.getInstance();
		assertEquals(expected, output);
	}

}
