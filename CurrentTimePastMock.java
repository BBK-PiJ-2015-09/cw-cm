import java.util.Calendar;

public class CurrentTimePastMock implements CurrentTime {
	public Calendar now() {
		Calendar thePast = Calendar.getInstance();
		thePast.add(Calendar.YEAR, -1);
		return thePast;
	}
}
