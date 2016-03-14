import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;

public class ContactManagerImpl implements ContactManager {
	private List<PastMeeting> pastMeetings = new ArrayList<PastMeeting>();
	private List<FutureMeeting> futureMeetings = new ArrayList<FutureMeeting>();
	private int maxMeetingId = 0;
	private Set<Contact> contacts = new HashSet<Contact>();
	private int maxContactId = 0;
	private CurrentTime calendar;

	/**
	 * Constructor
	 */
	public ContactManagerImpl(CurrentTime calendar) {
		this.calendar = calendar;
	}

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
		for (PastMeeting meeting : pastMeetings) {
			if (meeting.getId() == id) {
				if (meeting.getDate().after(calendar.now())) {
					throw new IllegalStateException();
				} else {
					return meeting;
				}
			}
		}
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		for (FutureMeeting meeting : futureMeetings) {
			if (meeting.getId() == id) {
				if (meeting.getDate().before(calendar.now())) {
					throw new IllegalArgumentException();
				} else {
					return meeting;
				}
			}
		}
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		for (Meeting meeting : futureMeetings) {
			if (meeting.getId() == id) {
				return meeting;
			}
		}
		for (Meeting meeting : pastMeetings) {
			if (meeting.getId() == id) {
				return meeting;
			}
		}
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		if (contact == null) {
			throw new NullPointerException();
		} else if (!this.contacts.contains(contact)) {
			throw new IllegalArgumentException();
		} else {
			List<Meeting> meetings = new ArrayList<Meeting>();
			for (Meeting meeting : futureMeetings) {
				if (meeting.getContacts().contains(contact)) {
					meetings.add(meeting);
				}
			}
			Collections.sort(meetings, new Comparator<Meeting>() {
			  public int compare(Meeting last, Meeting next) {
			      return last.getDate().compareTo(next.getDate());
			  }
			});
			return meetings;
		}
	}

	@Override
	public List<Meeting> getMeetingListOn(Calendar date) {
		int day = date.get(Calendar.DAY_OF_YEAR);
		List<Meeting> meetings = new ArrayList<Meeting>();
		for (Meeting meeting : pastMeetings) {
			if (meeting.getDate().get(Calendar.DAY_OF_YEAR) == day) {
				meetings.add(meeting);
			}
		}
		for (Meeting meeting : futureMeetings) {
			if (meeting.getDate().get(Calendar.DAY_OF_YEAR) == day) {
				meetings.add(meeting);
			}
		}
		return meetings;
	}

	@Override
	public List<PastMeeting> getPastMeetingListFor(Contact contact) {
		return null;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		if (contacts == null || date == null || text == null) {
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
