package module16_18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Util;

public class TestTranscationWithOrder {
	private static final String INSERT_ORDER = "INSERT INTO ORDERMASTER(ID, BUYER, PRICE, ORDTIME) VALUES(ORD_SEQ.NEXTVAL, ?, ?, CURRENT_TIMESTAMP)";
	private static final String INSERT_ORDERLIST = "INSERT INTO ORDERLIST(ORDERID, PRODUCTID, QUANTITY) VALUES (?, ?, ?)";

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String next_orderid = null;
		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

			// 1.設定於pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			// 先新增訂單
			String cols[] = { "ID" };
			pstmt = con.prepareStatement(INSERT_ORDER, cols);
			pstmt.setString(1, "David");
			pstmt.setDouble(2, 1313.0);
			pstmt.executeUpdate();

			// 取得對應的自增主鍵值
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_orderid = rs.getString(1);
				System.out.println("自增主鍵值 = " + next_orderid + "(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增訂單明細內容
			addWithOrderId(con, next_orderid);

			// 2.設定於pstmt.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("新增訂單編號 " + next_orderid + " 時，明細同時被新增完畢");

			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			try {
				// 發生例外即進行rollback動作
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	private static void addWithOrderId(Connection con, String next_orderid) {
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_ORDERLIST);

			pstmt.setInt(1, Integer.parseInt(next_orderid));
			pstmt.setInt(2, 2);
			pstmt.setInt(3, 1);
			
			pstmt.executeUpdate();
			
			pstmt.setInt(1, Integer.parseInt(next_orderid));
			pstmt.setInt(2, 3);
			pstmt.setInt(3, 1);
			
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				// 發生例外即進行rollback動作
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

}
