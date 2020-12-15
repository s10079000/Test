package module03_09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import util.Util;

public class TestConnection1 {

	public static void main(String[] args) {
		Connection con = null;

		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			System.out.println("Connecting to database successfully! (連線成功！)");
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} finally {
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
