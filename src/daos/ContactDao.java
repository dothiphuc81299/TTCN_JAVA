package daos;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Contact;
import utils.JDBCConnectionUtil;

public class ContactDao extends AbstractDao {
	public ContactDao() {
		connectDB = new JDBCConnectionUtil();
	}

	public ArrayList<Contact> getAll() {
		ArrayList<Contact> listContact = new ArrayList<Contact>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM contacts ORDER BY id DESC";
		try {
			state = conn.createStatement();
			result = state.executeQuery(sql);
			while (result.next()) {
				Contact objContact = new Contact(result.getInt("id"), result.getString("name"),
						result.getString("email"), result.getString("website"), result.getString("message"));
				listContact.add(objContact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, state, conn);
		}
		return listContact;
	}
	
	// Phân trang
	public ArrayList<Contact> getContact(int first, int last) {
		ArrayList<Contact> listContact = new ArrayList<Contact>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM contacts ORDER BY id DESC LIMIT ?,?";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setInt(1, first);
			pstate.setInt(2, last);
			result = pstate.executeQuery();
			while (result.next()) {
				Contact objContact = new Contact(result.getInt("id"), result.getString("name"),
						result.getString("email"), result.getString("website"), result.getString("message"));
				listContact.add(objContact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listContact;
	}
	
	// Tìm kiếm liên hệ
	public ArrayList<Contact> getSearchContact(String searchContent) {
		ArrayList<Contact> listContact = new ArrayList<Contact>();
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM contacts WHERE name LIKE ? ORDER BY id DESC";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, "%" + searchContent + "%");
			result = pstate.executeQuery();
			while (result.next()) {
				Contact objContact = new Contact(result.getInt("id"), result.getString("name"),
						result.getString("email"), result.getString("website"), result.getString("message"));
				listContact.add(objContact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(result, pstate, conn);
		}
		return listContact;
	}
	
	public boolean checkId(int id) {
		conn = connectDB.getConnection();
		String sql = "SELECT * FROM contacts WHERE id = ?";
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
	
	// đếm số lượng liên hệ
	public int getCount() {
		int count = 0;
		conn = connectDB.getConnection();
		String sql = "SELECT COUNT(id) FROM contacts";
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
	
	public int addContact(Contact objContact) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "INSERT INTO contacts (name,email,website,message) VALUES (?,?,?,?)";
		try {
			pstate = conn.prepareStatement(sql);
			pstate.setString(1, objContact.getName());
			pstate.setString(2, objContact.getEmail());
			pstate.setString(3, objContact.getWebsite());
			pstate.setString(4, objContact.getContent());
			rs = pstate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectDB.close(pstate, conn);
		}
		return rs;
	}
	
	public int deleteContact(int id) {
		int rs = 0;
		conn = connectDB.getConnection();
		String sql = "DELETE FROM contacts WHERE id = ?";
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
