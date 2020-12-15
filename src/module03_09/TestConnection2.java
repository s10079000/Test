package module03_09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import util.Util;

public class TestConnection2 {

	public static void main(String[] args) {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
		
		// JDK 7(JDBC 4.1)以後
		try(Connection con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD)) {
			System.out.println("Connecting to database successfully! (連線成功！)");
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} 
	}
}
