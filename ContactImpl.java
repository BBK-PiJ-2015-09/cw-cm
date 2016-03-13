public class ContactImpl implements Contact {
	private int id;
	private String name;
	private String notes;

	/**
	 * Constructor
	 *
	 * @param id the id of the new contact
	 * @param name the name of the new contact
   	 * @throws NullPointerException if name is null
	 * @throws IllegalArgumentException if id is less than 1
	 */
	public ContactImpl(int id, String name) {
		if (name == null) {
			throw new NullPointerException();
		} else if (id <= 0) {
			throw new IllegalArgumentException();
		} else {
			this.id = id;
			this.name = name;
		}
	}

	/**
	 * Constructor
	 *
	 * @param id the id of the new contact
	 * @param name the name of the new contact
	 * @param note an initial note on the new contact
  	 * @throws NullPointerException if name or note is null
   	 * @throws IllegalArgumentException if id is zero
	 */
	public ContactImpl(int id, String name, String note) {
		if (name == null || note == null) {
			throw new NullPointerException();
		} else if (id <= 0) {
			throw new IllegalArgumentException();
		} else {
			this.id = id;
			this.name = name;
			this.notes = note;
		}
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	@Override
	public void addNotes(String note) {
		notes = note;
	}
}
