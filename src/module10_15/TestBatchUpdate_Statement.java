package module10_15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import util.Util;

public class TestBatchUpdate_Statement {

	public static void main(String[] args) {
		Connection con = null;
		Statement stat = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			
			stat = con.createStatement();
			stat.addBatch("INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
			+ "VALUES(7015, 'DAVID', 'MANAGER', TO_DATE('2016-01-01','YYYY-MM-DD'), 2500, 0.0, 40)");
			stat.addBatch("INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
			+ "VALUES(7016, 'KEVIN', 'CLERK', TO_DATE('2016-02-02','YYYY-MM-DD'), 800, 0.0, 20)");
			stat.addBatch("INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
			+ "VALUES(7017, 'VINCENT', 'SALESMAN', TO_DATE('2016-03-03','YYYY-MM-DD'), 600, 1000.0, 30)");
			stat.addBatch("INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
			+ "VALUES(7018, 'RON', 'ANALYST', TO_DATE('2016-04-04','YYYY-MM-DD'), 3500, 0.0, 10)");
			
			stat.executeBatch();
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (stat != null) {
				try {
					stat.close();
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

}
