import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl implements PastMeeting {
	private int id;

	/**
	 * Constructor
	 *
	 * @param id the id of the new meeting
	 * @param date the date of the new meeting
	 * @param contacts the contacts attending
	 */
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
		if (date == null) {
			throw new NullPointerException();
		} else {
			this.id = id;
		}
	}

	public int getId() {
		return id;
	}

	@Override
	public Calendar getDate() {
		return null;
	}

	@Override
	public Set<Contact> getContacts() {
		return null;
	}

	@Override
	public String getNotes() {
		return null;
	}

}
