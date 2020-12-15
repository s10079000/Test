package module03_09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Util;

public class Test_SELECT_ResultSet {
	private static final String SQL = "SELECT * FROM EMPLOYEE ORDER BY SAL";   //降冪在SAL後面加上DESC即可
	private static final String SQL_ALIAS = "SELECT a.ENAME, a.SAL, a.JOB, b.DNAME FROM EMPLOYEE a, DEPARTMENT b WHERE a.DEPTNO = b.DEPTNO";

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			System.out.println("Connecting to database successfully! (連線成功！)");
			
			pstmt = con.prepareStatement(SQL);
//			pstmt = con.prepareStatement(SQL_ALIAS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String ename = rs.getString("ENAME");
				double sal = rs.getDouble("SAL");
				String job = rs.getString("JOB");
//				String dname = rs.getString("DNAME");
				
				System.out.println("EMP NAME = " + ename);
				System.out.println("EMP SAL = " + sal);
				System.out.println("EMP JOB = " + job);
//				System.out.println("EMP DNAME = " + dname);
				System.out.println();
			}

		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} finally {

			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
			
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
