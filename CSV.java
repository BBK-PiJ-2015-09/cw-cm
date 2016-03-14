import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public class CSV {

	public static void save(List<PastMeeting> pastMeetings, List<FutureMeeting> futureMeetings, Set<Contact> contacts) {
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
			writer.write(stringBuilder.toString());
		} catch (FileNotFoundException ex) { //
			System.out.println("Write to " + output + " failed.");
		} finally {
			writer.close();
		}
	}

}
