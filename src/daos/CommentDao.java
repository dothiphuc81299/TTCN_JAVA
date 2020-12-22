package daos;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Comment;
import utils.JDBCConnectionUtil;

public class CommentDao extends AbstractDao {
	public CommentDao() {
		connectDB = new JDBCConnectionUtil();
	}

	public ArrayList<Comment> getAll() {
		ArrayList<Comment> listComment = new ArrayList<Comment>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM comments ORDER BY id DESC";
		try {
			state = conn.createStatement();
			result = state.executeQuery(sql);
			while (result.next()) {
				Comment objComment = new Comment(result.getInt("id"), result.getInt("id_user"),
						result.getInt("id_song"), result.getString("comment"), result.getTimestamp("date_create"),
						result.getInt("status"));
				listComment.add(objComment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, state, conn);
		}
		return listComment;
	}
	
	// Phân trang
	public ArrayList<Comment> getCmt(int first, int last) {
		ArrayList<Comment> listComment = new ArrayList<Comment>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM comments ORDER BY id DESC LIMIT ?,?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, first);
			pstate.setInt(2, last);
			result = pstate.executeQuery();
			while (result.next()) {
				Comment objComment = new Comment(result.getInt("id"), result.getInt("id_user"),
						result.getInt("id_song"), result.getString("comment"), result.getTimestamp("date_create"),
						result.getInt("status"));
				listComment.add(objComment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listComment;
	}
	
	public ArrayList<Comment> getCmtShow(int idSong, int first, int last) {
		ArrayList<Comment> listComment = new ArrayList<Comment>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM comments WHERE status = 1 AND id_song = ? ORDER BY id DESC LIMIT ?,?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idSong);
			pstate.setInt(2, first);
			pstate.setInt(3, last);
			result = pstate.executeQuery();
			while (result.next()) {
				Comment objComment = new Comment(result.getInt("id"), result.getInt("id_user"),
						result.getInt("id_song"), result.getString("comment"), result.getTimestamp("date_create"),
						result.getInt("status"));
				listComment.add(objComment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listComment;
	}
	
	public ArrayList<Comment> getCmtShow(int idSong) {
		ArrayList<Comment> listComment = new ArrayList<Comment>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM comments WHERE status = 1 AND id_song = ? ORDER BY id DESC LIMIT 4";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idSong);
			result = pstate.executeQuery();
			while (result.next()) {
				Comment objComment = new Comment(result.getInt("id"), result.getInt("id_user"),
						result.getInt("id_song"), result.getString("comment"), result.getTimestamp("date_create"),
						result.getInt("status"));
				listComment.add(objComment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, state, conn);
		}
		return listComment;
	}
	
	public ArrayList<Comment> getCmt(int idSong) {
		ArrayList<Comment> listComment = new ArrayList<Comment>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM comments WHERE id_song = ? ORDER BY id DESC";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, idSong);
			result = pstate.executeQuery();
			while (result.next()) {
				Comment objComment = new Comment(result.getInt("id"), result.getInt("id_user"),
						result.getInt("id_song"), result.getString("comment"), result.getTimestamp("date_create"),
						result.getInt("status"));
				listComment.add(objComment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listComment;
	}
	
	public boolean checkId(int id) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM comments WHERE id = ?";
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
	
	public int getCount() {
		int count = 0;
		conn = connectDB.getConnection();
		String sql = "SELECT COUNT(id) FROM comments";
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
	
	public int getCount(int idSong) {
		int count = 0;
		conn = connectDB.getConnection();
		String sql = "SELECT COUNT(id) FROM comments WHERE status = 1 AND id_song = ?";
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
			connectDB.close(result, state, conn);
		}
		return count;
	}
	
	public int addComment(Comment objCmt) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "INSERT INTO comments(id_user,id_song,comment) VALUES (?,?,?)";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, objCmt.getIdUser());
			pstate.setInt(2, objCmt.getIdSong());
			pstate.setString(3, objCmt.getComment());
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}
	
	public int editStatus(int status, int id) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "UPDATE comments SET status = ? WHERE id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, status);
			pstate.setInt(2, id);
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}
	
	public int deleteComment(int id) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "DELETE FROM comments WHERE id = ?";
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
	
	public boolean checkCommentUser(int idUser) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM comments WHERE id_user = ?";
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
	
	public boolean checkCommentSong(int idSong) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM comments WHERE id_song = ?";
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
	
	public int deleteCommentUser(int idUser) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "DELETE FROM comments WHERE id_user = ?";
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
	
	public int deleteCommentSong(int idSong) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "DELETE FROM comments WHERE id_song = ?";
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
