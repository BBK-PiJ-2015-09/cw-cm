import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class ContactManagerImpl implements ContactManager {
	private List<PastMeeting> pastMeetings = new ArrayList<PastMeeting>();
	private List<FutureMeeting> futureMeetings = new ArrayList<FutureMeeting>();
	private int maxMeetingId = 0;
	private Set<Contact> contacts = new HashSet<Contact>();
	private int maxContactId = 0;

	/**
	 * Constructor
	 */
	public ContactManagerImpl() {}

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (contacts == null || date == null) {
			throw new NullPointerException();
		} else if (date.before(Calendar.getInstance()) || !this.contacts.containsAll(contacts)) {
			throw new IllegalArgumentException();
		} else {
			FutureMeeting meeting = new FutureMeetingImpl(++maxMeetingId, date, contacts);
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
		if (contacts == null) {
			throw new NullPointerException();
		} else if (contacts.isEmpty() || !this.contacts.containsAll(contacts)) {
			throw new IllegalArgumentException();
		} else {
			PastMeeting meeting = new PastMeetingImpl(++maxMeetingId, date, contacts, text);
			pastMeetings.add(meeting);
		}
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
			Contact contact = new ContactImpl(++maxContactId, name, notes);
			contacts.add(contact);
			return contact.getId();
		}
	}

	@Override
	public Set<Contact> getContacts(String name) {
		if (name == null) {
			throw new NullPointerException();
		} else if (name == "") {
			return contacts;
		} else {
			name = name.toLowerCase();
			Set<Contact> contactSet = new HashSet<Contact>();
			for (Contact contact : contacts) {
				if (contact.getName().toLowerCase().contains(name)) {
					contactSet.add(contact);
				}
			}
			return contactSet;
		}
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		if (ids.length == 0) {
			throw new IllegalArgumentException();
		} else {
			Set<Contact> contactSet = new HashSet<Contact>();
			for (int id : ids) {
				for (Contact contact : contacts) {
					if (contact.getId() == id) {
						contactSet.add(contact);
					}
				}
			}
			if (contactSet.isEmpty()) {
				throw new IllegalArgumentException();
			} else {
				return contactSet;
			}
		}
	}

	@Override
	public void flush() {

	}
}
