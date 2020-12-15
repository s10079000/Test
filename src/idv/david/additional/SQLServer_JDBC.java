package idv.david.additional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLServer_JDBC {

	public static void main(String[] args) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Microsoft SQLServer 2008 - JDBC驅動程式-第四類
		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}

		try {
			Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Sample", "sa", "123456");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");

			while (rs.next()) {
				String str1 = rs.getString(1);
				String str2 = rs.getString(2);

				System.out.print(" EMPNO = " + str1);
				System.out.print(" ENAME = " + str2);
				System.out.print("\n");
			}

			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	}

}
