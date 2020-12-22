package daos;

import java.sql.SQLException;
import java.util.ArrayList;

import models.ForbiddenWord;
import utils.JDBCConnectionUtil;

public class ForbiddenWordDao extends AbstractDao {
	public ForbiddenWordDao() {
		connectDB = new JDBCConnectionUtil();
	}
	
	public ArrayList<ForbiddenWord> getAll() {
		ArrayList<ForbiddenWord> listWord = new ArrayList<ForbiddenWord>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM forbiddenword ORDER BY id DESC";
		try {
			state = conn.createStatement();
			result = state.executeQuery(sql);
			while (result.next()) {
				ForbiddenWord objWord = new ForbiddenWord(result.getInt("id"), result.getString("word"));
				listWord.add(objWord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, state, conn);
		}
		return listWord;
	}
	
	public ForbiddenWord getWord(int id) {
		ForbiddenWord objWord = null;
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM forbiddenword WHERE id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, id);
			result = pstate.executeQuery();
			if (result.next()) {
				objWord = new ForbiddenWord(result.getInt("id"), result.getString("word"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return objWord;
	}
	
	public ArrayList<ForbiddenWord> getWord(int first, int last) {
		ArrayList<ForbiddenWord> listWord = new ArrayList<ForbiddenWord>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM forbiddenword ORDER BY id DESC LIMIT ?,?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, first);
			pstate.setInt(2, last);
			result = pstate.executeQuery();
			while (result.next()) {
				ForbiddenWord objWord = new ForbiddenWord(result.getInt("id"), result.getString("word"));
				listWord.add(objWord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listWord;
	}
	
	public ArrayList<ForbiddenWord> getSearch(String searchContent) {
		ArrayList<ForbiddenWord> listWord = new ArrayList<ForbiddenWord>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM forbiddenword WHERE word LIKE ? ORDER BY id DESC";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, "%" + searchContent + "%");
			result = pstate.executeQuery();
			while (result.next()) {
				ForbiddenWord objWord = new ForbiddenWord(result.getInt("id"), result.getString("word"));
				listWord.add(objWord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listWord;
	}
	
	public boolean checkWord(String word) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM forbiddenword WHERE word = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, word);
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
	
	public boolean checkId(int id) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM forbiddenword WHERE id = ?";
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
		String sql = "SELECT COUNT(id) FROM forbiddenword";
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
	
	public int addWord(ForbiddenWord objWord) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "INSERT INTO forbiddenword (word) VALUES (?)";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, objWord.getWord());
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}
	
	public int editWord(ForbiddenWord objWord) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "UPDATE forbiddenword SET word = ? WHERE id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, objWord.getWord());
			pstate.setInt(2, objWord.getId());
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}
	
	public int deleteWord(int id) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "DELETE FROM forbiddenword WHERE id = ?";
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
	
}
