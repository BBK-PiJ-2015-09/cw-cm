import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;

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
		Date output = currentTime.now().getTime();
		Date expected = Calendar.getInstance().getTime();
		assertTrue((expected.getTime() - output.getTime()) < 1000);
	}

}
