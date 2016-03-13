import java.util.Calendar;
import java.util.Set;

public abstract class MeetingImpl implements Meeting {
	private int id;
	private Calendar date;
	private Set<Contact> contacts;

	/**
	 * Constructor
	 *
	 * @param id the id of the new meeting
	 * @param date the date of the new meeting
	 * @param contacts the contacts attending
  	 * @throws NullPointerException if date or contacts is null
   	 * @throws IllegalArgumentException if id is less than 1 or contacts is empty
	 */
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		if (date == null || contacts == null) {
			throw new NullPointerException();
		} else if (id <= 0 || contacts.isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			this.id = id;
			this.date = date;
			this.contacts = contacts;
		}
	}

	@Override
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

}
