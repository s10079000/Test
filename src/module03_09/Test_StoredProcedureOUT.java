package module03_09;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import util.Util;

public class Test_StoredProcedureOUT {

	public static void main(String[] args) {
		Connection con = null;
		CallableStatement cstmt = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			System.out.println("Connecting to database successfully! (連線成功！)");

			cstmt = con.prepareCall("{call get_sal_avg(?,?)}");
			cstmt.setFloat(1, 1000);
			cstmt.registerOutParameter(2, Types.FLOAT);
			cstmt.execute();
			
			float avg = cstmt.getFloat(2);
			System.out.println("Average is " + avg);
			
		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} finally {

			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (cstmt != null) {
				try {
					cstmt.close();
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
