import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	private String notes;

	/**
	 * Constructor
	 *
	 * @param id the id of the new meeting
	 * @param date the date of the new meeting
	 * @param contacts the contacts attending
   	 * @param notes the notes on the meeting
	 * @throws NullPointerException if notes is null
	 */
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
		super(id, date, contacts);
		if (notes == null) {
			throw new NullPointerException();
		} else {
			this.notes = notes;
		}
	}

	@Override
	public String getNotes() {
		return notes;
	}

}
