package module03_09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import util.Util;

public class Test_INSERT_Statement {
	private static final String SQL = "INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
			+ "VALUES(7015, 'DAVID', 'MANAGER', TO_DATE('2016-01-01','YYYY-MM-DD'), 2500, 0.0, 40)";

	public static void main(String[] args) {
		Connection con = null;
		Statement stat = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			System.out.println("Connecting to database successfully! (連線成功！)");

			stat = con.createStatement();
			int rowCount = stat.executeUpdate(SQL);
			System.out.println("新增 " + rowCount + " 筆資料");

		} catch (ClassNotFoundException ce) {
			System.err.println(ce);
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {

			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException se) {
					System.err.println(se.getMessage());
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					System.err.println(se.getMessage());
				}
			}
		}
	}
}
