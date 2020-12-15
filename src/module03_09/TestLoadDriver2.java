package module03_09;

import java.sql.DriverManager;
import java.sql.SQLException;

public class TestLoadDriver2 {

	public static void main(String[] args) {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("Loading driver successfully! (載入成功！)");
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
}
