package module10_15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.Util;

public class TestWriteClob {
	private static final String SQL = "UPDATE CLUB SET INTRO = ? WHERE ID = ?";

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(SQL);

			// 1. setClob (JDBC 4.0)
			Reader reader = getLongStringStream("items/BM.txt");
			pstmt.setClob(1, reader);
			pstmt.setInt(2, 1);
			pstmt.executeUpdate();

			// 2. setCharacterStream
			Reader reader2 = getLongStringStream("items/Bar.txt");
			pstmt.setCharacterStream(1, reader2);
			pstmt.setInt(2, 2);
			pstmt.executeUpdate();
			
			//3. setString
			String data = getLongString("items/RM.txt");
			pstmt.setString(1, data);
			pstmt.setInt(2, 3);
			pstmt.executeUpdate();
			
			// setAsciiStream使用方式同setCharacterStream
			// 差別就在於Unicode的支援
			// 若是文字為Unicode請使用setCharacterStream
			
			System.out.println("更新成功");

			reader.close();
			reader2.close();
//			data.close();
			
		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} catch (IOException ie) {
			System.out.println(ie);
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
		}
	}

	// 使用String
	public static String getLongString(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder(); // StringBuffer is thread-safe!
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();

		return sb.toString();
	}

	// 使用資料流
	public static Reader getLongStringStream(String path) throws IOException {
		return new FileReader(path);

	}

}
