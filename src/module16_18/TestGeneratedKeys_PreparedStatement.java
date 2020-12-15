package module16_18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Util;

public class TestGeneratedKeys_PreparedStatement {
	private static final String SQL = "INSERT INTO EMPLOYEE2(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
			+ "VALUES(EMP2_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";

	public static void main(String[] args) {
		Connection con = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			String[] cols = { "EMPNO" }; // 或 int cols[] = {1};

			PreparedStatement pstmt = con.prepareStatement(SQL, cols);
			pstmt.setString(1, "DAVID");
			pstmt.setString(2, "MANAGER");
			pstmt.setDate(3, java.sql.Date.valueOf("2016-01-01"));
			pstmt.setInt(4, 2500);
			pstmt.setDouble(5, 0.0);
			pstmt.setInt(6, 40);

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				String key = rs.getString(1);
				System.out.println("自增主鍵值 = " + key + "(剛新增成功的員工編號)");
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}

			rs.close();

			pstmt = con.prepareStatement("SELECT * FROM EMPLOYEE2");
			ResultSet rs2 = pstmt.executeQuery();
			while (rs2.next()) {
				System.out.println("EMPNO = " + rs2.getInt(1));
				System.out.println("ENAME = " + rs2.getString(2));
				System.out.println("=================");
			}

			rs2.close();
			pstmt.close();

		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} finally {
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
