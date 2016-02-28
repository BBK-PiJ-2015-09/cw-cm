public class ContactImpl implements Contact {
	private int id;

	public ContactImpl(int id, String name) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
