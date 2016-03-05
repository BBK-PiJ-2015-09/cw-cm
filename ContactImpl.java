public class ContactImpl implements Contact {
	private int id;

	public ContactImpl(int id, String name) {
		if (id == 0) {
			throw new IllegalArgumentException();
		} else {
			this.id = id;
		}
	}

	public int getId() {
		return id;
	}

}
