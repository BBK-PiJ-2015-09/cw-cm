import java.util.Calendar;

public class CurrentTimeMock implements CurrentTime {
	public Calendar now() {
		Calendar theFuture = Calendar.getInstance();
		theFuture.add(Calendar.YEAR, 1);
		return theFuture;
	}
}
