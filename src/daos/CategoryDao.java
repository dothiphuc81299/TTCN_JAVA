package daos;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Category;
import utils.JDBCConnectionUtil;

public class CategoryDao extends AbstractDao {
	public CategoryDao() {
		connectDB = new JDBCConnectionUtil();
	}

	public ArrayList<Category> getAll() {
		ArrayList<Category> listCat = new ArrayList<Category>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM categories ORDER BY id DESC";
		try {
			state = conn.createStatement();
			result = state.executeQuery(sql);
			while (result.next()) {
				Category objCat = new Category(result.getInt("id"), result.getString("name"));
				listCat.add(objCat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, state, conn);
		}
		return listCat;
	}
	
	// Lấy (last) danh mục bắt đầu từ (first) => Phân trang
	public ArrayList<Category> getCat(int first, int last) {
		ArrayList<Category> listCat = new ArrayList<Category>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM categories ORDER BY id DESC LIMIT ?,?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, first);
			pstate.setInt(2, last);
			result = pstate.executeQuery();
			while (result.next()) {
				Category objCat = new Category(result.getInt("id"), result.getString("name"));
				listCat.add(objCat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listCat;
	}

	// Tìm kiếm danh mục bài hát
	public ArrayList<Category> getSearchCat(String searchContent) {
		ArrayList<Category> listCat = new ArrayList<Category>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM categories WHERE name LIKE ? ORDER BY id DESC";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, "%" + searchContent + "%");
			result = pstate.executeQuery();
			while (result.next()) {
				Category objCat = new Category(result.getInt("id"), result.getString("name"));
				listCat.add(objCat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listCat;
	}

	public Category getCat(int id) {
		Category objCat = null;
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM categories WHERE id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, id);
			result = pstate.executeQuery();
			if (result.next()) {
				objCat = new Category(result.getInt("id"), result.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return objCat;
	}
	
	public boolean checkId(int id) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM categories WHERE id = ?";
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
	
	// Kiểm tra id có tồn tại hay không
	public int getCount(int id) {
		int count = 0;
		conn = connectDB.getConnection();
		String sql = "SELECT COUNT(id) FROM categories WHERE id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, id);
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
	
	// đếm số lượng danh mục bài hát
	public int getCount() {
		int count = 0;
		conn = connectDB.getConnection();
		String sql = "SELECT COUNT(id) FROM categories";
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

	public int addCat(Category objCat) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "INSERT INTO categories (name) VALUES (?)";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, objCat.getName());
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}

	public int editCat(Category objCat) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "UPDATE categories SET name = ? WHERE id = ?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, objCat.getName());
			pstate.setInt(2, objCat.getId());
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}

	public int deleteCat(int id) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "DELETE FROM categories WHERE id = ?";
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
