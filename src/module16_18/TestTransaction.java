package module16_18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import util.Util;

public class TestTransaction {

	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			System.out.println("Connecting to database successfully! (連線成功！)");

			con.setAutoCommit(false); //改為手動提交,告訴資料庫進入交易

			stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
					+ "VALUES(7015, 'DAVID', 'MANAGER', TO_DATE('2016-01-01','YYYY-MM-DD'), 2500, 0.0, 40)");
			stmt.executeUpdate("INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
					+ "VALUES(7016, 'JAMES', 'CLERK', TO_DATE('2016-02-02','YYYY-MM-DD'), 800, 0.0, 20)");
			stmt.executeUpdate("INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
					+ "VALUES(7017, 'VINCENT', 'SALESMAN', TO_DATE('2016-03-03','YYYY-MM-DD'), 600, 1000.0, 30)");
			stmt.executeUpdate("INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
					+ "VALUES(7018, 'RON', 'ANALYST', TO_DATE('2016-04-04','YYYY-MM-DD'), 3500, 0.0, 10)");
			
			System.out.println("Operation success!");
			con.commit();
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			try {
				// 發生例外即進行rollback動作
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
		} finally {
			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true); //確保資料庫交易結束,避免發生死結問題
					con.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
		}
	}

}
