package module16_18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.Util;

public class TestGeneratedKeys_Statement {
	private static final String SQL = "INSERT INTO EMPLOYEE2(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
			+ "VALUES(EMP2_SEQ.NEXTVAL, 'DAVID', 'MANAGER', TO_DATE('2016-01-01','YYYY-MM-DD'), 2500, 0.0, 40)";

	public static void main(String[] args) {
		Connection con = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			String[] cols = { "EMPNO" }; // 或是 int[] cols = {1};
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL, cols);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				String key = rs.getString(1); // Oracle Driver只支援欄位索引值取得自增主鍵值
				System.out.println("自增主鍵值 = " + key + "(剛新增成功的員工編號)");
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}

			rs.close();
			stmt.close();

			Statement stmt2 = con.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM EMPLOYEE2");
			while (rs2.next()) {
				System.out.println("EMPNO = " + rs2.getInt(1));
				System.out.println("ENAME = " + rs2.getString(2));
				System.out.println("=================");
			}

			rs2.close();
			stmt2.close();

		} catch (ClassNotFoundException ce) {
			System.err.println(ce.getMessage());
		} catch (SQLException se) {
			se.printStackTrace();
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
