package module10_15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import util.Util;

public class TestSQLWarning {
	private static final String SQL = "SELECT ENAME, SAL FROM EMPLOYEE ORDER BY ENAME";

	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);

			System.out.println("insert之前===============================================");
			while (rs.next()) {
				String ename = rs.getString("ENAME");
				double sal = rs.getDouble("SAL");  
//				int sal = rs.getInt("SAL");
				System.out.println("\t" + ename + "   " + sal);
				// 捕捉警告 (但是Oracle不支援)
				SQLWarning stmt_Warning = stmt.getWarnings();
				printWarning(stmt_Warning);
				// 捕捉警告 (但是Oracle不支援)
				SQLWarning rs_Warning = rs.getWarnings();
				printWarning(rs_Warning);
			}
			System.out.println();

			// insert資料
			stmt.executeUpdate("INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
					+ "VALUES(7015, 'DAVID', 'MANAGER', TO_DATE('2016-01-01','YYYY-MM-DD'), 2500, 0.0, 40)");

		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			SQLException gen = new SQLException("SQL 操作已經取消", "S1008", 0);
			se.setNextException(gen); // Append the old exception
			while (se != null) {
				System.err.println("-------------SQLException caught-------------");
				System.err.println("Message: " + se.getMessage()); // 回傳String
				System.err.println("SQL state: " + se.getSQLState()); // 回傳String
				System.err.println("Vendor code: " + se.getErrorCode()); // 回傳int
				se = se.getNextException();
			}
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
		}
	}

	private static boolean printWarning(SQLWarning warning) throws SQLException {
		if (warning == null)
			return false;
		while (warning != null) {
			System.err.println("\n----Warning----");
			System.err.println("Message: " + warning.getMessage());
			System.err.println("SQL state: " + warning.getSQLState());
			System.err.println("Vendor code: " + warning.getErrorCode());
			System.out.println("---------------");
			warning = warning.getNextWarning();
			System.out.println();
		}
		return true;
	}

}
