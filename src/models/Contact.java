package models;





public class Contact {
	private int id;
	private String name;
	private String email;
	private String website;
	private String content;
	
	public Contact(String name, String email, String content) {
		super();
		this.name = name;
		this.email = email;
		this.content = content;
	}
	
	public Contact() {
		// TODO Auto-generated constructor stub
	}

	public Contact(int id, String name, String email, String website, String content) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.website = website;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
