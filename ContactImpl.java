public class ContactImpl implements Contact {
	private int id;

	/**
	 * Constructor
	 *
	 * @param id the id of the new contact
	 * @param name the name of the new contact
	 */
	public ContactImpl(int id, String name) {
		if (name == null) {
			throw new NullPointerException();
		} else if (id <= 0) {
			throw new IllegalArgumentException();
		} else {
			this.id = id;
		}
	}

	@Override
	public int getId() {
		return id;
	}

}
