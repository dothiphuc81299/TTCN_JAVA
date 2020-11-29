package models;


public class Like {
	private int id;
	private int idUser;
	private int idSong;
	private int status;
	
	public Like(int idUser, int idSong) {
		super();
		this.idUser = idUser;
		this.idSong = idSong;
	}
	
	public Like() {
		// TODO Auto-generated constructor stub
	}

	public Like(int id, int idUser, int idSong, int status) {
		super();
		this.id = id;
		this.idUser = idUser;
		this.idSong = idSong;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
