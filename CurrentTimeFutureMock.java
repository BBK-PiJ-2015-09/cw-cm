import java.util.Calendar;

public class CurrentTimeFutureMock implements CurrentTime {
	public Calendar now() {
		Calendar theFuture = Calendar.getInstance();
		theFuture.add(Calendar.YEAR, 1);
		return theFuture;
	}
}
