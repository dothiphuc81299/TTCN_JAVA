package models;

import java.sql.Timestamp;




public class Comment {
	private int id;
	private int idUser;
	private int idSong;
	private String comment;
	private Timestamp dateCreate;
	private int status;
	
	public Comment(int idUser, int idSong, String comment) {
		super();
		this.idUser = idUser;
		this.idSong = idSong;
		this.comment = comment;
	}
	
	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(int id, int idUser, int idSong, String comment, Timestamp dateCreate, int status) {
		super();
		this.id = id;
		this.idUser = idUser;
		this.idSong = idSong;
		this.comment = comment;
		this.dateCreate = dateCreate;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdSong() {
		return idSong;
	}

	public void setIdSong(int idSong) {
		this.idSong = idSong;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Timestamp dateCreate) {
		this.dateCreate = dateCreate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
