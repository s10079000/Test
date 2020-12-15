package module10_15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.Util;

public class TestBatchUpdate_PreparedStatement {
	private static final String SQL = "INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
			+ "VALUES(?, ?, ?, ?, ?, ?, ?)";

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, 7015);
			pstmt.setString(2, "DAVID");
			pstmt.setString(3, "MANAGER");
			pstmt.setDate(4, java.sql.Date.valueOf("2016-01-01"));
			pstmt.setDouble(5, 2500);
			pstmt.setDouble(6, 0.0);
			pstmt.setInt(7, 40);
			pstmt.addBatch();
			
			pstmt.setInt(1, 7016);
			pstmt.setString(2, "KEVIN");
			pstmt.setString(3, "CLERK");
			pstmt.setDate(4, java.sql.Date.valueOf("2016-02-02"));
			pstmt.setDouble(5, 800);
			pstmt.setDouble(6, 0.0);
			pstmt.setInt(7, 20);
			pstmt.addBatch();
			
			pstmt.executeBatch();

		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} finally {

			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (pstmt != null) {
				try {
					pstmt.close();
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
