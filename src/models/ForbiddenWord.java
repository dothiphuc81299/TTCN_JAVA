package models;


public class ForbiddenWord {
	private int id;
	private String word;
	
	public ForbiddenWord() {
		// TODO Auto-generated constructor stub
	}

	public ForbiddenWord(int id, String word) {
		super();
		this.id = id;
		this.word = word;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	
}
