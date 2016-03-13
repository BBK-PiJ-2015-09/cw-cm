import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl implements PastMeeting {
	private int id;
	private Calendar date;
	private Set<Contact> contacts;

	/**
	 * Constructor
	 *
	 * @param id the id of the new meeting
	 * @param date the date of the new meeting
	 * @param contacts the contacts attending
	 */
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
		if (date == null || contacts == null || notes == null) {
			throw new NullPointerException();
		} else {
			this.id = id;
			this.date = date;
			this.contacts = contacts;
		}
	}

	public int getId() {
		return id;
	}

	@Override
	public Calendar getDate() {
		return date;
	}

	@Override
	public Set<Contact> getContacts() {
		return contacts;
	}

	@Override
	public String getNotes() {
		return null;
	}

}
