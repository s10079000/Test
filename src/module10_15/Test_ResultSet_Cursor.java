package module10_15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.Util;

public class Test_ResultSet_Cursor {
	private static final String SQL = "SELECT * FROM EMPLOYEE";

	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

//			stmt = con.createStatement(); // SQLException: Oracle - 無效的僅轉送結果集作業: beforeFirst
									     // SQLException: Microsoft SQL Server 2008 - 不支援在順向結果集上執行要求的作業。

//			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); // Microsoft SQL Server 2008 不支援此種組合
//			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			rs = stmt.executeQuery(SQL);

			System.out.println("起始位置=" + rs.getRow());
			rs.next(); // 不可遺漏 rs.next(); 因為一開始游標在第一列之上
			System.out.print(rs.getRow() + " ");
			System.out.print(" EMPNO = " + rs.getInt(1));
			System.out.print(" ENAME = " + rs.getString(2) + "\n");
			//回到第一列
			rs.beforeFirst();

			System.out.println("----------------1----------------");

			while (rs.next()) {
				System.out.print(rs.getRow() + " ");
				System.out.print(" EMPNO = " + rs.getInt(1));
				System.out.print(" ENAME = " + rs.getString(2) + "\n");
			}

			System.out.println("----------------2----------------");

			while (rs.previous()) {
				System.out.print(rs.getRow() + " ");
				System.out.print(" EMPNO = " + rs.getInt(1));
				System.out.print(" ENAME = " + rs.getString(2) + "\n");
			}

			System.out.println("----------------3----------------");

			rs.absolute(1);

			// rs.relative(0);

			// rs.first();

			System.out.print(rs.getRow() + " ");
			System.out.print(" EMPNO = " + rs.getInt(1));
			System.out.print(" ENAME = " + rs.getString(2) + "\n");

			System.out.println("----------------4----------------");
			
			//回到最後一列
			rs.last();
			int len = rs.getRow();
			System.out.println("資料共" + len + "筆" + " [使用ResultSet游標移動的方式]");
			rs.beforeFirst();

			System.out.println("----------------5----------------");

			ResultSet rs2 = stmt.executeQuery("SELECT count(*) AS count FROM EMPLOYEE");
			rs2.next();
			int len2 = rs2.getInt("count");
			System.out.println("資料共" + len2 + "筆" + " [直接下SQL指令的方式]");
			rs2.close();

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

			if (stmt != null) {
				try {
					stmt.close();
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
