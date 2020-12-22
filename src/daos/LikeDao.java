package daos;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Like;
import utils.JDBCConnectionUtil;

public class LikeDao extends AbstractDao {
	public LikeDao() {
		connectDB = new JDBCConnectionUtil();
	}

	public int getCount(int idSong) {
		int count = 0;
		conn = connectDB.getConnection();
		String sql = "SELECT COUNT(id) FROM likes WHERE id_song = ? AND status = 1";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idSong);
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

	public ArrayList<Integer> getLikeUser(int idUser) {
		ArrayList<Integer> listLike = new ArrayList<Integer>();
		conn = connectDB.getConnection();
		String sql = "SELECT id_song FROM likes WHERE id_user = ? AND status = 1";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idUser);
			result = pstate.executeQuery();
			while (result.next()) {
				int idSong = result.getInt("id_song");
				listLike.add(idSong);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listLike;
	}

	// kiểm tra xem bài viết đã được like bởi user có idUser hay chưa
	public boolean checkLiked(int idSong, int idUser) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM likes WHERE id_song = ? AND id_user = ? AND status = 1";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idSong);
			pstate.setInt(2, idUser);
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

	// Xem bài viết nếu chưa like thì sẽ thực hiện thay đổi trạng thái like, chứ
	// không cần phải add thêm vào database
	public boolean checkLikedStatus(int idSong, int idUser) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM likes WHERE id_song = ? AND id_user = ? AND status = 0";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idSong);
			pstate.setInt(2, idUser);
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

	// Kiểm tra đã có trong database chưa
	public boolean check(int idSong, int idUser) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM likes WHERE id_song = ? AND id_user = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idSong);
			pstate.setInt(2, idUser);
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

	public int addLike(Like objLike) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "INSERT INTO likes (id_user,id_song) VALUES (?,?)";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, objLike.getIdUser());
			pstate.setInt(2, objLike.getIdSong());
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}

	public int editLikeStatus(Like objLike) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "";
		if (objLike.getStatus() == 1) {
			sql = "UPDATE likes SET status = 0 WHERE id_song = ? AND id_user = ?";
		} else {
			sql = "UPDATE likes SET status = 1 WHERE id_song = ? AND id_user = ?";
		}
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, objLike.getIdSong());
			pstate.setInt(2, objLike.getIdUser());
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}

	public boolean checkLikeUser(int idUser) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM likes WHERE id_user = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idUser);
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

	public boolean checkLikeSong(int idSong) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM likes WHERE id_song = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idSong);
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

	public int deleteLikeUser(int idUser) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "DELETE FROM likes WHERE id_user = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idUser);
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}

	public int deleteLikeSong(int idSong) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "DELETE FROM likes WHERE id_song = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idSong);
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}
}
