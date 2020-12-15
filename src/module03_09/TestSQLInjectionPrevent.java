package module03_09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import util.Util;

public class TestSQLInjectionPrevent {
	private static final String SQL = "SELECT * FROM MEMBER WHERE NAME = ? AND PASSWORD = ?";

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String userName = "", userPassword = "";

		Scanner sc = new Scanner(System.in);
		System.out.println("請輸入名稱: ");
		userName = sc.nextLine();
		System.out.println("請輸入密碼: ");
		userPassword = sc.nextLine();

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			System.out.println("Connecting to database successfully! (連線成功！)");

			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, userName);
			pstmt.setString(2, userPassword);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("User ID: " + rs.getInt("ID"));
				System.out.println("User name: " + rs.getString("NAME"));
				System.out.println("User password: " + rs.getString("PASSWORD"));
				System.out.println();
			}

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
			sc.close();
		}
	}

}
