package module10_15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import util.Util;

public class Test_SELECT_RSMetaData {
	private static final String SQL = "SELECT * FROM EMPLOYEE";
	
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			System.out.println("Connecting to database successfully! (連線成功！)");
			
			pstmt = con.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			
			int numberOfColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numberOfColumns; i++) {
				String colName = rsmd.getColumnName(i);
				//oracle查詢table名稱會得到空字串
				String tableName = rsmd.getTableName(i);
				String name = rsmd.getColumnTypeName(i);
				boolean caseSen = rsmd.isCaseSensitive(i);
				boolean writable = rsmd.isWritable(i);
				System.out.println("Information for column " + colName);
				System.out.println("    Column is in table " + tableName);
				System.out.println("    DBMS name for type is " + name);
				System.out.println("    Is case sensitive:  " + caseSen);
				System.out.println("    Is possibly writable:  " + writable);
				System.out.println();
			}
			
			while (rs.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					//錯誤示範!!,不能每個都用getString,如果之後數值資料需要計算,會需要再轉型
					String s = rs.getString(i);
					System.out.print(s + "  ");
				}
				System.out.println();
			}
			
		} catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch(SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		
	}

}
