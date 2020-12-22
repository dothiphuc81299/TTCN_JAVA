package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import utils.JDBCConnectionUtil;

public abstract class AbstractDao {
	protected JDBCConnectionUtil connectDB;
	protected Statement state;
	protected PreparedStatement pstate;
	protected Connection conn;
	protected ResultSet result;
}
