package module21_22;

import java.sql.*;

public class SQLAgent {
	private ConnPool connPool;
	private Connection conn;
	private String driverName;
	private String jdbcURL;
	private String username;
	private String password;

	public void setConnPool(ConnPool pool) {
		connPool = pool;
	}

	public void setDriverName(String drvName) {
		driverName = drvName;
	}

	public void setJdbcURL(String url) {
		jdbcURL = url;
	}

	public void setUserName(String uname) {
		username = uname;
	}

	public void setPassword(String passwd) {
		password = passwd;
	}

	public Connection getConnection() {
		return conn;
	}

	public void setConnectionSwitch(String on_off) throws SQLException {
		if (on_off.equalsIgnoreCase("ON")) {
			if (connPool != null)
				openDB(connPool);
			else
				openDB(driverName, jdbcURL, username, password);
		} else if (on_off.equalsIgnoreCase("OFF"))
			closeDB();
	}

	public void openDB(ConnPool pool) throws SQLException {
		if (conn != null && !conn.isClosed())
			throw new SQLException("The connection has been established already.");
		conn = connPool.getConnection();
	}

	public void openDB(String drvName, String url, String uname, String passwd) throws SQLException {
		if (conn != null && !conn.isClosed())
			throw new SQLException("The connection has been established already.");
		try {
			Class.forName(drvName);
		} catch (ClassNotFoundException ex) {
			throw new SQLException(ex.toString());
		}
		conn = DriverManager.getConnection(url, uname, passwd);
	}

	public void closeDB() throws SQLException {
		if (connPool != null) {
			connPool.returnConnection();
			connPool = null;
		} else {
			if (conn == null)
				throw new SQLException("This connection is null.");
			if (conn.isClosed())
				throw new SQLException("This connection has been close already..");
			conn.close();
		}
		conn = null;
	}

}