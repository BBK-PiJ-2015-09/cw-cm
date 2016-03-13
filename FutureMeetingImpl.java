import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
	/**
	 * Constructor
	 *
	 * @param id the id of the new meeting
	 * @param date the date of the new meeting
	 * @param contacts the contacts attending
	 */
	public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		super(id, date, contacts);
	}
}
