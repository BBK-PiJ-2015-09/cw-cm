import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class ContactManagerImpl implements ContactManager {
	private List<FutureMeeting> futureMeetings = new ArrayList<FutureMeeting>();
	private int maxId = 0;
	private Set<Contact> contacts = new HashSet<Contact>();

	/**
	 * Constructor
	 */
	public ContactManagerImpl() {}

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (contacts == null || date == null) {
			throw new NullPointerException();
		} else if (date.before(Calendar.getInstance())) {
			throw new IllegalArgumentException();
		} else {
			FutureMeeting meeting = new FutureMeetingImpl(++maxId, date, contacts);
			futureMeetings.add(meeting);
			return meeting.getId();
		}
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		for (FutureMeeting meeting : futureMeetings) {
			if (meeting.getId() == id) {
				return meeting;
			}
		}
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
		if (name == null || notes == null) {
			throw new NullPointerException();
		} else if (name == "" || notes == "") {
			throw new IllegalArgumentException();
		} else {
			Contact contact = new ContactImpl(1, name, notes);
			contacts.add(contact);
			return contact.getId();
		}
	}

	@Override
	public Set<Contact> getContacts(String name) {
		if (name == "") {
			return contacts;
		} else {
			Set<Contact> contactSet = new HashSet<Contact>();
			for (Contact contact : contacts) {
				if (contact.getName() == name) {
					contactSet.add(contact);
				}
			}
			return contactSet;
		}
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		return null;
	}

	@Override
	public void flush() {

	}
}
