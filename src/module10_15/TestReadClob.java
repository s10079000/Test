package module10_15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Util;

public class TestReadClob {
	private static final String SQL = "SELECT INTRO FROM CLUB WHERE ID = ?";

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(SQL);

			// 1. getClob
			pstmt.setInt(1, 1);
			ResultSet rs1 = pstmt.executeQuery();
			rs1.next();
			Clob clob = rs1.getClob(1);
			System.out.println(readString(clob));
			rs1.close();
			
//			// 2. getCharacterStream
//			pstmt.setInt(1, 2);
//			ResultSet rs2 = pstmt.executeQuery();
//			rs2.next();
//			Reader reader = rs2.getCharacterStream(1);
//			System.out.println(readString(reader));
//			rs2.close();
			
			//3. getString
			pstmt.setInt(1, 3);
			ResultSet rs3 = pstmt.executeQuery();
			rs3.next();
			System.out.println(rs3.getString(1));
			
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
	
	public static String readString(Clob clob) throws IOException, SQLException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(clob.getCharacterStream());
		String str;
		while((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();

		return sb.toString();
	}
	
	public static String readString(Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(reader);
		String str;
		while((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();

		return sb.toString();
	}

}
