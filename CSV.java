import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public class CSV {

	public static void save(List<PastMeeting> pastMeetings, List<FutureMeeting> futureMeetings, Set<Contact> contacts) {
		writePastMeetings(pastMeetings);
		writeFutureMeetings(futureMeetings);
		writeContacts(contacts);
	}

	private static void writeContacts(Set<Contact> contacts) {
		File output = new File("contacts.csv");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(output);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("id");
			stringBuilder.append(",");
			stringBuilder.append("name");
			stringBuilder.append(",");
			stringBuilder.append("notes");
			stringBuilder.append("\n");

			for (Contact contact : contacts) {
				stringBuilder.append(contact.getId());
				stringBuilder.append(",");
				stringBuilder.append(contact.getName());
				stringBuilder.append(",");
				stringBuilder.append(contact.getNotes());
				stringBuilder.append("\n");
			}

			writer.write(stringBuilder.toString());
		} catch (FileNotFoundException ex) { //
			System.out.println("Write to " + output + " failed.");
		} finally {
			writer.close();
		}
	}

	private static void writeFutureMeetings(List<FutureMeeting> futureMeetings) {
		File output = new File("futureMeetings.csv");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(output);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("id");
			stringBuilder.append(",");
			stringBuilder.append("date");
			stringBuilder.append(",");
			stringBuilder.append("contactIds");
			stringBuilder.append("\n");

			for (FutureMeeting meeting : futureMeetings) {
				stringBuilder.append(meeting.getId());
				stringBuilder.append(",");
				stringBuilder.append(meeting.getDate().getTime());
				stringBuilder.append(",");
				stringBuilder.append(getContactIds(meeting.getContacts()));
				stringBuilder.append("\n");
			}

			writer.write(stringBuilder.toString());
		} catch (FileNotFoundException ex) { //
			System.out.println("Write to " + output + " failed.");
		} finally {
			writer.close();
		}
	}

	private static void writePastMeetings(List<PastMeeting> pastMeetings) {
		File output = new File("pastMeetings.csv");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(output);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("id");
			stringBuilder.append(",");
			stringBuilder.append("date");
			stringBuilder.append(",");
			stringBuilder.append("contactIds");
			stringBuilder.append(",");
			stringBuilder.append("notes");
			stringBuilder.append("\n");

			for (PastMeeting meeting : pastMeetings) {
				stringBuilder.append(meeting.getId());
				stringBuilder.append(",");
				stringBuilder.append(meeting.getDate().getTime());
				stringBuilder.append(",");
				stringBuilder.append(getContactIds(meeting.getContacts()));
				stringBuilder.append(",");
				stringBuilder.append(meeting.getNotes());
				stringBuilder.append("\n");
			}

			writer.write(stringBuilder.toString());
		} catch (FileNotFoundException ex) { //
			System.out.println("Write to " + output + " failed.");
		} finally {
			writer.close();
		}
	}

	private static String getContactIds(Set<Contact> contacts) {
		String ids = "";
		for (Contact contact : contacts) {
			ids = ids + contact.getId() + ";";
		}
		return ids;
	}

}
