package models;

import java.sql.Timestamp;




public class Song {
	private int id;
	private String name;
	private String description;
	private String detail;
	private Timestamp createdAt;
	private String picture;
	private String mp3;
	private int counter;
	private Category cat;
	
	public Song(String name, String description, String detail, String picture, String mp3, Category cat) {
		super();
		this.name = name;
		this.description = description;
		this.detail = detail;
		this.picture = picture;
		this.mp3 = mp3;
		this.cat = cat;
	}
	
	public Song() {
		// TODO Auto-generated constructor stub
	}

	public Song(int id, String name, String description, String detail, Timestamp createdAt, String picture, String mp3,
			int counter, Category cat) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.detail = detail;
		this.createdAt = createdAt;
		this.picture = picture;
		this.mp3 = mp3;
		this.counter = counter;
		this.cat = cat;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getMp3() {
		return mp3;
	}

	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public Category getCat() {
		return cat;
	}

	public void setCat(Category cat) {
		this.cat = cat;
	}
	
	
	
}
