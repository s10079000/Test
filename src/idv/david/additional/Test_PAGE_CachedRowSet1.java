package idv.david.additional;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class Test_PAGE_CachedRowSet1 {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "david";
	private static final String PASSWORD = "123456";
	private static final String SQL = "SELECT * FROM EMPLOYEE";

	public static void main(String[] args) {
		try {
			Class.forName(DRIVER);
			CachedRowSet rowset = RowSetProvider.newFactory().createCachedRowSet();
			rowset.setUrl(URL);
			rowset.setUsername(USER);
			rowset.setPassword(PASSWORD);
			rowset.setCommand(SQL);
			// 設定每頁的筆數
			rowset.setPageSize(3);
			// 執行Query，會自動連線，之後再斷線
			rowset.execute();

			ResultSetMetaData rsmd = rowset.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			for (int i = 1; i <= numberOfColumns; i++)
				System.out.print(rsmd.getColumnName(i) + " ");

			System.out.println();

			while (rowset.next()) { // 第一頁
				for (int i = 1; i <= numberOfColumns; i++)
					System.out.print(rowset.getString(i) + "       ");
				System.out.println();
			}
			System.out.println("---------------------"); // 分頁線

			while (rowset.nextPage()) { // 有下一頁嗎?
				while (rowset.next()) {
					for (int i = 1; i <= numberOfColumns; i++)
						System.out.print(rowset.getString(i) + "       ");
					System.out.println();
				}
				System.out.println("---------------------"); // 分頁線
			}

			System.out.println("****************************************************************");

			rowset.afterLast();

			while (rowset.previousPage()) { // 有上一頁嗎?
				rowset.afterLast();
				while (rowset.previous()) {
					for (int i = 1; i <= numberOfColumns; i++)
						System.out.print(rowset.getString(i) + "       ");
					System.out.println();
				}
				System.out.println("---------------------"); // 分頁線
			}

		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

}
