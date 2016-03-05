public class ContactImpl implements Contact {
	private int id;

	public ContactImpl(int id, String name) {
		if (name == null) {
			throw new NullPointerException();
		} else if (id <= 0) {
			throw new IllegalArgumentException();
		} else {
			this.id = id;
		}
	}

	public int getId() {
		return id;
	}

}
