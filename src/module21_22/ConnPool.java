package module21_22;

import java.sql.*;
import java.util.*;

public class ConnPool {
	private static final int DEFAULT_MAX_CONNECTIONS = 3;
	// Vector與Hashtable已實作同步化(synchronized)
	private Vector<Connection> freeConnections; // JDK1.5 Generic (就是裝著連線的池子)
	private Hashtable<Thread, Connection> boundConnections; // JDK1.5 Generic (用來記錄每位使用者跟他們所屬的連線)
	private String driverName;
	private String jdbcURL;
	private String username;
	private String password;
	private int maxConnections;

	public ConnPool() {
		this(DEFAULT_MAX_CONNECTIONS);
	}

	public ConnPool(int numConnections) {
		maxConnections = numConnections;
		boundConnections = null;
		freeConnections = null;
		driverName = "";
		jdbcURL = "";
		username = "";
		password = "";
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

	public void setMaxConnections(int numConnections) {
		maxConnections = numConnections;
	}

	public void setConnectionSwitch(String on_off) throws SQLException {
		if (on_off.equalsIgnoreCase("ON"))
			openDB(driverName, jdbcURL, username, password);
		else if (on_off.equalsIgnoreCase("OFF"))
			closeDB();
	}
	//連線池初始化,並找到資料庫建立幾個連線放在池子裏面
	public void openDB(String drvName, String url, String uname, String passwd) throws SQLException {
		try {
			boundConnections = new Hashtable<Thread, Connection>();
			freeConnections = new Vector<Connection>();
			Class.forName(drvName);
			for (int i = 0; i < maxConnections; i++)
				freeConnections.add(DriverManager.getConnection(url, uname, passwd));
		} catch (Exception ex) {
			boundConnections = null;
			freeConnections = null;
			throw new SQLException(ex.toString());
		}
	}

	public void closeDB() throws SQLException {
		if (boundConnections != null) {
			for (Connection conn : boundConnections.values()) { // JDK1.5 for-each
				conn.close();
			}
			boundConnections.clear();
			boundConnections = null;
		}

		if (freeConnections != null) {
			for (Connection conn : freeConnections) { // JDK1.5 for-each
				conn.close();
			}
			freeConnections.removeAllElements();
			freeConnections = null;
		}
	}

	public synchronized Connection getConnection() throws SQLException {
		if (freeConnections == null)
			throw new SQLException("The conection pool has not been established yet.");
		if (boundConnections.get(Thread.currentThread()) != null)
			throw new SQLException("Cannot get connections over once for this current running thread.");
		try {
			if (freeConnections.size() == 0) {
				System.out.println(Thread.currentThread().getName() + " is waiting..............................................");
				wait();
			}
		} catch (InterruptedException ex) {
			throw new SQLException(ex.toString());
		}
		Connection conn = freeConnections.firstElement();
		freeConnections.removeElement(conn);
		boundConnections.put(Thread.currentThread(), conn);

		return conn;
	}

	public synchronized void returnConnection() throws SQLException {
		Connection conn = boundConnections.remove(Thread.currentThread());
		if (conn == null)
			throw new SQLException("The connection which this current running thread got is not found.");
		freeConnections.add(conn);
		notify();
	}

}