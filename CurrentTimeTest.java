import org.junit.*;
import static org.junit.Assert.*;

public class CurrentTimeTest {

	@Test
	public void testsConstructor() {
		try {
			new CurrentTimeImpl();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
