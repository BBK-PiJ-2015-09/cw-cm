import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {
	private List<Meeting> meetings = new ArrayList<Meeting>();
	private int maxId = 0;

	/**
	 * Constructor
	 */
	public ContactManagerImpl() {}

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (contacts == null) {
			throw new NullPointerException();
		} else if (date.before(Calendar.getInstance())) {
			throw new IllegalArgumentException();
		} else {
			FutureMeeting meeting = new FutureMeetingImpl(++maxId, date, contacts);
			meetings.add(meeting);
			return meeting.getId();
		}
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		return null;
	}

	@Override
	public List<Meeting> getMeetingListOn(Calendar date) {
		return null;
	}

	@Override
	public List<PastMeeting> getPastMeetingListFor(Contact contact) {
		return null;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {

	}

	@Override
	public PastMeeting addMeetingNotes(int id, String text) {
		return null;
	}

	@Override
	public int addNewContact(String name, String notes) {
		return 0;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		return null;
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		return null;
	}

	@Override
	public void flush() {

	}
}
