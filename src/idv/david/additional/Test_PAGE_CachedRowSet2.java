package idv.david.additional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class Test_PAGE_CachedRowSet2 {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "david";
	private static final String PASSWORD = "123456";
	private static final String SQL = "SELECT * FROM EMPLOYEE";

	public static void main(String[] args) {
		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(SQL);
			rs.last();
			int len = rs.getRow();
			System.out.println("全部資料總共有 = " + len + "筆");

			Scanner sc = new Scanner(System.in);
			System.out.println("請輸入每頁要瀏覽的資料筆數？ ");
			int pageSize = sc.nextInt();
			System.out.println("請輸入第幾筆資料？ ");
			int startRow = sc.nextInt();
			sc.close();

			CachedRowSet rowset = RowSetProvider.newFactory().createCachedRowSet();
			rowset.setPageSize(pageSize);
			// 將查詢出來的ResultSet資料封裝在CachedRowSet實體裡
			// 第二個參數決定了第幾筆資料開始查看
			rowset.populate(rs, startRow);
			System.out.println("每一頁有 " + pageSize + " 筆");

			rs.close();
			stmt.close();
			con.close(); // 【注意: 資料庫連線已在此關閉】

			System.out.println("取出從第 " + startRow + " 筆開始的那一頁");
			while (rowset.next()) { // 取出要的那一頁而已，潮爽DER!!!
				int empno = rowset.getInt(1);
				String ename = rowset.getString(2);

				System.out.print(" EMPNO = " + empno);
				System.out.print(" ENAME = " + ename);
				System.out.print("\n");
			}

		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}

	}

}
