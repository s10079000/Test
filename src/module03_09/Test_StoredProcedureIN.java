package module03_09;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import util.Util;

public class Test_StoredProcedureIN {

	public static void main(String[] args) {
		Connection con = null;
		CallableStatement cstmt = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			cstmt = con.prepareCall("{call INSERT_EMP(?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setInt(1, 7015);
			cstmt.setString(2, "DAVID");
			cstmt.setString(3, "MANAGER");
			
			// 利用GregorianCalendar指定日期再轉換成java.sql.Date資料
			GregorianCalendar cal = new GregorianCalendar(2016, Calendar.JANUARY, 1);
			Date date = new Date(cal.getTimeInMillis());
			cstmt.setDate(4, date);
			
			cstmt.setFloat(5, 2500);
			cstmt.setFloat(6, 0.0f);
			cstmt.setInt(7, 40);
			
			cstmt.execute();
			
		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} finally {
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
