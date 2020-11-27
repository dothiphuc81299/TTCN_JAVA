package daos;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Category;
import models.Song;
import utils.JDBCConnectionUtil;

public class SongDao extends AbstractDao {
	public SongDao() {
		connectDB = new JDBCConnectionUtil();
	}

	// Lấy all data trong database
	public ArrayList<Song> getAll() {
		ArrayList<Song> listSong = new ArrayList<Song>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM songs INNER JOIN categories ON songs.cat_id = categories.id ORDER BY songs.id DESC";
		try {
			state = conn.createStatement();
			result = state.executeQuery(sql);
			while (result.next()) {
				Category objCat = new Category(result.getInt("cat_id"), result.getString("categories.name"));
				Song objSong = new Song(result.getInt("songs.id"), result.getString("songs.name"),
						result.getString("preview_text"), result.getString("detail_text"),
						result.getTimestamp("date_create"), result.getString("picture"), result.getString("mp3"),
						result.getInt("counter"), objCat);
				listSong.add(objSong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, state, conn);
		}
		return listSong;
	}

	public int getCount(ArrayList<Integer> listLike) {
		int count = 0;
		conn = connectDB.getConnection();
		String sql = "SELECT COUNT(id) FROM songs WHERE 0";
		if (listLike.size() > 0) {
			for (int i = 1; i <= listLike.size(); i++) {
				sql += " OR id = ?";
			}
		}
		try {
			pstate = conn.prepareStatement(sql);
			int j = 1;
			if (listLike.size() > 0) {
				for (Integer idSong : listLike) {
					pstate.setInt(j++, idSong);
				}
			}
			result = pstate.executeQuery();
			while (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return count;
	}

	// Tìm kiếm bài hát
	public ArrayList<Song> getSearchSong(String searchContent, int catId) {
		ArrayList<Song> listSong = new ArrayList<Song>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM songs INNER JOIN categories ON songs.cat_id = categories.id WHERE 1";
		if (!searchContent.equals("")) {
			sql += " AND songs.name LIKE ?";
		}
		if (catId != 0) {
			sql += " AND cat_id = ?";
		}
		sql += " ORDER BY songs.id DESC";
		try {
			pstate = conn.prepareStatement(sql);
			if (!searchContent.equals("") && catId != 0) {
				pstate.setString(1, "%" + searchContent + "%");
				pstate.setInt(2, catId);
			} else {
				if (!searchContent.equals("") && catId == 0) {
					pstate.setString(1, "%" + searchContent + "%");
				} else if (searchContent.equals("") && catId != 0) {
					pstate.setInt(1, catId);
				}
			}
			result = pstate.executeQuery();
			while (result.next()) {
				Category objCat = new Category(result.getInt("cat_id"), result.getString("categories.name"));
				Song objSong = new Song(result.getInt("songs.id"), result.getString("songs.name"),
						result.getString("preview_text"), result.getString("detail_text"),
						result.getTimestamp("date_create"), result.getString("picture"), result.getString("mp3"),
						result.getInt("counter"), objCat);
				listSong.add(objSong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listSong;
	}

	// đếm số lượng bài hát
	public int getCount() {
		int count = 0;
		conn = connectDB.getConnection();
		String sql = "SELECT COUNT(id) FROM songs";
		try {
			state = conn.createStatement();
			result = state.executeQuery(sql);
			while (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, state, conn);
		}
		return count;
	}

	public boolean checkId(int id) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM songs WHERE id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, id);
			result = pstate.executeQuery();
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return false;
	}

	// Lấy data theo category
	public ArrayList<Song> getSongByCat(int catId) {
		ArrayList<Song> listSong = new ArrayList<Song>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM songs INNER JOIN categories ON songs.cat_id = categories.id WHERE songs.cat_id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, catId);
			result = pstate.executeQuery();
			while (result.next()) {
				Category objCat = new Category(result.getInt("cat_id"), result.getString("categories.name"));
				Song objSong = new Song(result.getInt("songs.id"), result.getString("songs.name"),
						result.getString("preview_text"), result.getString("detail_text"),
						result.getTimestamp("date_create"), result.getString("picture"), result.getString("mp3"),
						result.getInt("counter"), objCat);
				listSong.add(objSong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listSong;
	}

	// Lấy 1 bài hát theo id
	public Song getSong(int id) {
		Song objSong = null;
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM songs INNER JOIN categories ON songs.cat_id = categories.id WHERE songs.id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, id);
			result = pstate.executeQuery();
			if (result.next()) {
				Category objCat = new Category(result.getInt("cat_id"), result.getString("categories.name"));
				objSong = new Song(result.getInt("songs.id"), result.getString("songs.name"),
						result.getString("preview_text"), result.getString("detail_text"),
						result.getTimestamp("date_create"), result.getString("picture"), result.getString("mp3"),
						result.getInt("counter"), objCat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return objSong;
	}

	public int addSong(Song objSong) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "INSERT INTO songs (name,preview_text,detail_text,picture,mp3,cat_id) VALUES (?,?,?,?,?,?)";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, objSong.getName());
			pstate.setString(2, objSong.getDescription());
			pstate.setString(3, objSong.getDetail());
			pstate.setString(4, objSong.getPicture());
			pstate.setString(5, objSong.getMp3());
			pstate.setInt(6, objSong.getCat().getId());
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}

	public int editSong(Song objSong) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "UPDATE songs SET name = ?,preview_text = ?,detail_text = ?,picture = ?,mp3 = ?,cat_id = ? WHERE id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, objSong.getName());
			pstate.setString(2, objSong.getDescription());
			pstate.setString(3, objSong.getDetail());
			pstate.setString(4, objSong.getPicture());
			pstate.setString(5, objSong.getMp3());
			pstate.setInt(6, objSong.getCat().getId());
			pstate.setInt(7, objSong.getId());
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}

	public int deleteSong(int id) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "DELETE FROM songs WHERE id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, id);
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}

	public int deleteSongByCat(int catId) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "DELETE FROM songs WHERE cat_id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, catId);
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}
}
